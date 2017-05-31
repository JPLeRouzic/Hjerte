
package ML.Classify;

import Misc.AudioFeatures.RecordingInfo;
import ML.Train.HMM;
import ML.featureDetection.FindBeats;
import ML.featureDetection.NormalizeBeat;
import Misc.AudioFeatures.FromFileToAudio;
import Misc.Gui.Controller.Control;
import Misc.Gui.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Classify
{

    public Classify(Control controller, OuterFrame outer_frame, RecordingInfo info[])
    {
        int heart_rate = 60 ;
        
        try {
            ArrayList beats = null;
            RecordingInfo recordings[] = info;
            if (recordings == null) {
                String message = "No recordings available to extract features from.";
                JOptionPane.showConfirmDialog(null, message, "WARNING", 0);
            }
            if (recordings.length > 1) {
                String message = "No more than one recording for classifying.";
                JOptionPane.showConfirmDialog(null, message, "WARNING", 0);
            }

            File load_file = new File(recordings[0].file_path);
            FromFileToAudio e = new FromFileToAudio(
                    load_file);
            float samples[] = e.getAudioSamples().getSamplesMixedDown();
            int smplingRate = (int) e.getAudioSamples().getSamplingRate();

            /**
             * Normalize dynamics of signal
             *
             */
            NormalizeBeat norm = new NormalizeBeat();

            float[] data_norm = norm.normalizeAmplitude(samples);

            FindBeats cb = new FindBeats();

            // calculate beat rate
        
            // calculate beat rate
            cb.calcBeat2(data_norm, smplingRate, heart_rate);
            
            // calculate beat rate
            beats = cb.getProbableBeats();
        

            // Get a list of Observations on the tested file, intended for the HMM
            // This time we do not attempt at assigning hidden events to them
            // That will be the role of the viterbi function
            // What we need is just a list of Observation events without name
            EntryPoint.hmm = new HMM(beats);
            HMM hp = EntryPoint.hmm; // just a short pointer
            hp.train();
            
            // Assign hidden states to that list of Observations
            // The hidden states are assigned, based on knowledge stored in the trained HMM
//            vt.viterbi(samples, beats, hp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
