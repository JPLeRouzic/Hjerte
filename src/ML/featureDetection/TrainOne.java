/**
 * This is the general strategy that was followed by winners in Physionet 2016:
 * 
 * We first need to get comparable sounds, so sounds must be downsample
 *     (in order to get the same rate for different sounds)
 *
 * Then we need a clean sound, so we remove spikes
 *
 * Then we use this information to time normalize the sound according to the beat rate
 * in order to minimize the HMM work
 *
 * Then we will segment it to recognize the significant times like the locations for
 * S1, systole, s2 and diastole 
 * Actually there are dozens of features that recognised in Physionet 2016, but they 
 * are common to the full sound file and not specific to some area. On contrary with HMMs
 * we need to segment precisely all the areas in the sound file.
 * 
 * We find the FFT of the whole signal and then process it to identify the main 10 signals in it
 *
 * Those times (which should include the signal creating S1, sys, S2, dia) will be our HMM states,
 * and we must give an observation matrix as input to the training,
 *
 * The result of training should fill in the state transition matrix
 *
 * * The observation matrix has states in rows and observations in columns
 *
 * * The state transition matrix gives the probability of transitions from
 * one state to the next
 * It is a square matrix where each row and column corresponds to a state
 * and where the intersection of a row and a colum gives the transition
 * probability
 * We take the convention that rows represent the current state and columns
 * represent the next state
 *
 * Last modified by: Jean-Pierre Le Rouzic 1/18/2017 jeanpierre.lerouzic@yahoo.no
 *
 */
package ML.featureDetection;

import ML.Classify.Observation;
import ML.Classify.PDefFeats;
import ML.Train.Segmentation;
import java.util.ArrayList;

public class TrainOne {

    /**
     * The times (S1, sys, S2, dia) will be our HMM states, and we must give an
     * observation matrix as input to the training, The result of training
     * should fill in the state transition matrix
     *
     * * The observation matrix has states in rows and observations in columns
     */
    static float[][] observation_matrix;

    /*
 * * The state transition matrix gives the probability of transitions from
 * one state to the next
 * It is a square matrix where each row and column corresponds to a state
 * and where the intersection of a row and a colum gives the transition
 * probability
 * We take the convention that rows represent the current state and columns
 * represent the next state
     */
    static float[][] state_transition_matrix;

    /*
    * Used to find most important frequencies in the sound file
    */
    final int ten = 10 ;
    int freqIdx = 0 ;
    float[] freqsStack = new float[ten] ;     
    float[] binNbStack = new float[ten] ; 
    
    /**
     * Basic constructor that sets the definition and dependencies (and their
     * offsets) of this feature.
     */
    public TrainOne() {
        String name = "Train classifier n°1";
        String description = "Train the classifier on given set of files.";
    }

    /**
     * Extracts this feature from the given samples at the given sampling rate
     * and given the other feature values.
     *
     * @param samples
     * @param sampling_rate
     * @param heart_rate  // Average heart rate given optionally by the user through the GUI
     * @param norm
     * @return
     * @throws java.lang.Exception
     */
    public ArrayList extractFeature(
            float[] samples,
            float sampling_rate,
            PDefFeats predefFeatures,
            NormalizeBeat norm
    )
            throws Exception {

        int heart_rate = 60 ; 
        // find features
        int smplingRate = (int) sampling_rate;

        /**
         * Normalize dynamics of signal
         *
         */
        float[] data_norm = norm.normalizeAmplitude(samples);

        FindBeats cb = new FindBeats();
        
        // calculate beat rate
        if(predefFeatures.nbBeats.intValue() != 0)  {
            heart_rate = (predefFeatures.nbBeats.intValue() * 60) / predefFeatures.duration.intValue();
        }
        
        cb.calcBeat1(data_norm, smplingRate, heart_rate);        

        /**
         *
         * Now that we have a clean sound, we will segment it to recognize the
         * significant times like the locations for S1, systole, s2 and diastole
         *
         * This is the labelled observations private ArrayList<String>
         * moreBeatsType = new ArrayList<String>(); private ArrayList<Integer>
         * moreBeatsIndx = new ArrayList<Integer>();
         *
         * Those times (S1, sys, S2, dia) will be our HMM states, and we must
         * have an observation matrix as input to the training, The result of
         * training should fill in the state transition matrix.
         */
        Segmentation segmt = new Segmentation(cb);

        segmt.segmentation(cb, smplingRate);
        
        // Add suffix to Observations names
        enrichObs(segmt.segmentedBeats, predefFeatures) ;
        
        return segmt.segmentedBeats;
    }

    /*
    It uses the adaptive Huffman compression algorithm as its pattern detection engine. 
    Let us say that we are comparing file A and file B. 
    We compress file A to determine how small it can get. 
    We then compress file B to see the amount it will shrink. 
    Finally, we compress file A+B. 
    If gzip(A+B) is significantly less than gzip(A) + gzip(B), then that means files A and B share patterns.    		
    
    Encoder encoder = factory.createEncoder();
    String encodedMessage = encoder.encode(message);
				
    Decoder decoder = factory.createDecoder();
    String decodedMessage = decoder.decode(encodedMessage);    
     */
    
    
    /**
     * Create an identical copy of this feature. This permits FeatureExtractor
     * to use the prototype pattern to create new composite features using
     * metafeatures.
     *
     * @return
     */
    public Object clone() {
        return new TrainOne();
    }

    private void enrichObs(ArrayList segmentedBeats, PDefFeats predefFeatures) {
                // For the HMM to separate the observations in more cases than S1-S4, we need to
        // add a "minor" numbering to the "Sx" string.
        // However it is added later than primary name, to have a reasonable amount of Observations
        // (not having hundreds that are destination, only once)
        // This because otherwise having only four kind of "Observation" is not very helpful
        for (int u = 0; u < segmentedBeats.size(); u++) {
            Observation obs = (Observation) segmentedBeats.get(u);
            int size = obs.getSign().size();
            // Lets have no more than 20 Observations
//            float fact = (float) (size / 20.0) ;
            int deux = (int) (size % 20);
            String suffix = String.valueOf(deux) ;
            obs.setNameSufx(suffix);
            
            // add predefined features
            obs.addPreDef(predefFeatures) ;
        }
        

    }
}
