/*
 * First, the algorithm applies a low-pass filter. 
 * Filtered audio data are divided into windows of samples.
 * A peak detection algorithm is applied to each window and identifies as peaks 
 * all values above a certain threshold. 
 *
 * For each peak the distance in number of samples between it and its neighbouring peaks is stored.
 * For each distance between peaks the algorithm counts how many times it has been detected. 
 * Once all windows are processed the algorithm has built a map distanceHistogram where for each 
 * possible distance its number of occurrences is stored.
 */
package ML.featureDetection;

import java.util.ArrayList;
import java.util.BitSet;

/**
 *
 * @author jplr
 */
public class FindBeats {

    // bag of events probably including S1, S2, S3, S4 sounds
    private ArrayList moreBeatEvents = new ArrayList();

    // S1 events as found in data flow, maybe wrong because of spikes
    private ArrayList probableS1Beats = new ArrayList();

    // Normalized data that is correlated to moreBeatEvents and probableS1Beats
    private float[] normalizedData;

    // average value of sound 
    float aver;

    // max value of sound
    float maxi;

    // treshhold to obtain the beat rate, useful to qualify the quality of the file sound
    // and helping in segmenting the heart sounds
    float treshFind;

    // number of time a resample was needed
    public int resampleCount;

    // number of time a signal normalisation was needed
    public int normalizeCount;
    
    // Used to do a rough evaluation of beat rate and treshold, before aiming at a more precise evaluation
    int RBF_Beats = 0;
    float RBF_treshhold = 0 ;
    

    /**
     */
    public FindBeats() {
        treshFind = 0;
        resampleCount = 0;
        normalizeCount = 0;

        moreBeatEvents = new ArrayList();
        probableS1Beats = new ArrayList();
    }

    private void averMax(float[] data) {
        float absData;
        int idx = 0;
        float sum = 0;

        // find this file average
        while (idx < data.length) {
            absData = data[idx];
            if (absData < 0) {
                absData = -absData;
            }
            // in order to find average value
            sum += absData;
            // in order to find maximum value
            if (absData > maxi) {
                maxi = absData;
            }

            idx++;
        }

        // find this file average
        aver = sum / data.length;
    }

    /**
     * It calculates the beat in dataIn, this is not the same as heart beat rate
     * because the heart has several sounds inside one beat We will count
     * downward changes that occur at intensities between max value and average
     * value in order to stay away from noise A good reference is a0004.wav that
     * has 36 events and lasts 36 seconds.
     *
     * @param dataIn
     * @param sampling_rate
     *
     */
    public void calcBeat1(float[] data, int sampling_rate, int beatSec) {
        float[] dataIn = data;
        ArrayList events = null;
        ArrayList candidateBeats = new ArrayList();
        float sum = 0, max = 0, ave;
        int idx;
        ArrayList preBeats = new ArrayList();
        ArrayList prepreBeats = new ArrayList();
        float s2Shift = (float) 0.15;
        float earlyS1, lateS1;

        // average beat rate may be one per second.
        int nbSecInFile = dataIn.length / sampling_rate;

        averMax(dataIn);
        ave = aver;
        max = maxi;

        // evaluating the tresholdS1        
        treshFind = 6;

        earlyS1 = sampling_rate * (float) 0.5;
        lateS1 = sampling_rate * (float) 1.5;

        // Rough evaluation of treshold, if beat rate not provided in advance
        if (beatSec == 0) {
            roughBeatFind(dataIn, earlyS1, lateS1, s2Shift, ave, max, nbSecInFile);
            beatSec = RBF_Beats ;
            treshFind = RBF_treshhold ;
        }

        int floor_low, floor_high, ceiling_low, ceiling_high;
        floor_low = (int) (beatSec * 0.41675);       // 25 events per minute 
        floor_high = (int) (beatSec * 0.83335);      // 50 events per minute 
        ceiling_low = (int) (beatSec * 1.33335);     // 80 events per minute 
        ceiling_high = (int) (beatSec * 2.6667);   // 160 events per minute 

        while (treshFind > 0.05) {
            events = calcBeatFind(dataIn, earlyS1, lateS1, s2Shift, treshFind);

            // Number of events per minute
            int nbEvents = (events.size() * 60) / nbSecInFile;

            if ((nbEvents > (floor_low)) && (nbEvents < (ceiling_high))) {
                if ((nbEvents > (floor_high)) && (nbEvents < (ceiling_low))) {
                    normalizedData = dataIn;
                    probableS1Beats = events;
                    float tresholdS1 = (ave + (treshFind * max)) / (treshFind + 1);
                    moreBeatEvents = findNextBeatEvents(dataIn, tresholdS1);
                    return;
                }
                // Normally we should converge, if not then return last heart rate
                int un = preBeats.size() - prepreBeats.size();
                int deux = events.size() - preBeats.size();
                if ((un < deux) && (un > 0) && (deux > 0) && (preBeats.size() > 0)) {
                    // Find next sounds in this beat
                    normalizedData = dataIn;
                    probableS1Beats = events;
                    float tresholdS1 = (ave + (treshFind * max)) / (treshFind + 1);
                    moreBeatEvents = findNextBeatEvents(dataIn, tresholdS1);
                    return;
                } else {
                    // Maybe the real heart wrong is wrong
                    if ((events.size() == preBeats.size()) && (events.size() == prepreBeats.size())) {
                        candidateBeats = events;
                        break;
                    }
                    prepreBeats = preBeats;
                    preBeats = events;
                }
                if ((nbEvents < (floor_high)) && (candidateBeats.size() < events.size())) {
                    candidateBeats = events;
                } else if ((nbEvents > (ceiling_low)) && (candidateBeats.size() > events.size())) {
                    candidateBeats = events;
                } // troisième cas ?
            } else if (events.size() > (nbSecInFile * 4)) {
                // Something wrong with dataIn, way too much events, so we filter it heavily
                Resample resp = new Resample();
                dataIn = resp.downSample(dataIn, sampling_rate, 1024);
                resampleCount++;
                continue;
            } else if (events.size() < (nbSecInFile / 3)) {
                // Something wrong with dataIn, not enough events, so we normalize dataIn
                NormalizeBeat nb = new NormalizeBeat();
                dataIn = nb.normalizeAmplitude(dataIn);
                normalizeCount++;
                continue;
            }
            // Finer grain as tresholdS1 becomes only slightly higher than average
            if (treshFind > 1) {
                treshFind -= 0.5;
            } else {
                treshFind -= 0.125;
            }
        }
        // Find next sounds in this beat
        normalizedData = dataIn;
        probableS1Beats = events;
        float tresholdS1 = (ave + (2 * max)) / (2 + 1);
//        moreBeatEvents = findNextBeatEvents(dataIn, earlyS1, lateS1, s2Shift, tresholdS1);

        moreBeatEvents = betBeatSlice(dataIn, tresholdS1, candidateBeats.size() * 4);

        probableS1Beats = candidateBeats;
        return;
    }

    /**
     * This is for finding S1 events, it is independant of sampling rate and
     * contains no prior knowledge about beat rate
     *
     * @param dataIn
     * @param sampling_rate
     * @param treshFnd
     * @param ave
     * @param max
     *
     * @return
     */
    ArrayList calcBeatFind(
            float[] dataIn,
            float earlyS1, float lateS1,
            float s2Shift,
            float treshFnd
    ) {
        float ave, max;
        int idxGlobal;
        ArrayList BeatS1Rate = new ArrayList();
        boolean S1Flag = false;
        float tresholdS1;
        float diff = 0;
        float lastAverage;

        // Calculate average and maximum positive value in the sound sample
        // Results in fields aver and maxi
        averMax(dataIn);
        ave = aver;
        max = maxi;

        if (max > 1) {
            max = (float) 0.99;
        }

        if (ave <= 0) {
            ave = max / 3;
        }

        /**/
        tresholdS1 = (ave + (treshFnd * max)) / (treshFnd + 1);

        idxGlobal = 1;
        while (idxGlobal < dataIn.length) {
            lastAverage = lastWinAve(dataIn, idxGlobal, 30);
            if ((dataIn[idxGlobal] > tresholdS1) && (S1Flag == true)) {
                if (lastAverage < tresholdS1) {
                    // We went through the tresholdS1 upward, while counting was forbidden
                    // We need to reautorize it at the next downward tresholdS1
                    S1Flag = false;
                }
            }
            if ((dataIn[idxGlobal] < tresholdS1) && (S1Flag == false) && (lastAverage > tresholdS1)) {
                // So we are in a situation when we went through the tresholdS1 when we went 
                // from [idxGlobal-1] to [idxGlobal]
                // Let's count a beat, and rise a flag to count only once downward
                int un = BeatS1Rate.size();
                if (un > 0) {
                    diff = idxGlobal - ((Integer) BeatS1Rate.get(un - 1)).intValue();
                } else {
                    diff = earlyS1 + 1;
                }

                // Is it possibly too early?
                if (diff < earlyS1) {
                    idxGlobal++;
                    continue;
                }
                // Is it possibly too late?
                if (diff > lateS1) {
                    tresholdS1 = (float) ((double) tresholdS1 * 0.85D);
                    if (un > 1) {
                        idxGlobal = ((Integer) BeatS1Rate.get(un - 2)).intValue();
                        continue;
                    }
                }
                BeatS1Rate.add(Integer.valueOf(idxGlobal));
                S1Flag = true;

                // try to move idxGlobal a bit, in a effort to thwart spikes
                idxGlobal += s2Shift;
            }
            idxGlobal++;
        }
        return BeatS1Rate;
    }

    public ArrayList getMoreBeats() {
        return moreBeatEvents;
    }

    public ArrayList getProbableBeats() {
        return probableS1Beats;
    }

    public float[] getNormalizedData() {
        return normalizedData;
    }

    /**
     * Average the past "windowLength" slots in "dataIn" before "idxGlbal"
     * This is because the number must be an average of past data
     * 
     */
    private float lastWinAve(float[] dataIn, int idxGlbal, int windowLength) {
        int cnt = 0, idxWindow;
        int idxGlobal = idxGlbal;
        float prevWindowAve = 0;

        if (idxGlobal < windowLength) {
            idxGlobal = windowLength;
        }

        cnt = 0;
        prevWindowAve = 0;
        idxWindow = idxGlobal - windowLength;
        while (idxWindow < idxGlobal) {
            if (idxWindow < dataIn.length) {
                if (dataIn[idxWindow] > 0) {
                    prevWindowAve += dataIn[idxWindow];
                    cnt++;
                }
                idxWindow++;
            } else {
                //             int y = 0 ;
                //            break ;
            }
        }
        if (cnt > 0) {
            return prevWindowAve / cnt;
        } else {
            return dataIn[dataIn.length - 1];
        }
    }

    /**
     * The purpose of this method is to calculate a "signature" of the beat. It
     * is a bit string, long as a beat lasts, and having only "0" or "1" values
     * It is created by saturing the signal and compressing it with RLL.
     *
     * It is supposed to be faster than FFT
     *
     */
    public ArrayList beatSign(float[] data) {
        // the signature is found
        BitSet binary = new BitSet();

        int jdx = 0;
        float ave = 0, max = 0;
        float treshold = 0;
        // find this file average
        averMax(data);
        ave = aver;
        max = maxi;

        treshold = ((2 * ave) + max) / 3;

        binary = betBitSlice(data, treshold);

        // Now do the Run Limited Length algorithm
        int j;
        ArrayList reslt = new ArrayList();

        // We start always with a count of "1" at index == "0"
        int k = 0;
        boolean a = binary.get(0);
        while ((k < binary.size()) && (a != true)) {
            a = binary.get(k);
            k++;
        }

        for (int i = 0; i < binary.length(); i++) {
            int runLength = 1;
            while (i + 1 < binary.length() && binary.get(i) == binary.get(i + 1)) {
                runLength++;
                i++;
            }

            reslt.add(new Integer(runLength));
        }
        return reslt;
    }

    private ArrayList findNextBeatEvents(
            float[] dataIn,
            float treshFnd) {

        float trshFnd = treshFnd;
        /*        
        // Find next sounds in this beat, we try to find four times the probableS1Beats
        // for having candidates for S1, S2, S3, S4 events
//            System.out.println("trshFnd= " + trshFnd) ;
        }
         */

        moreBeatEvents = betBeatSlice(dataIn, trshFnd, probableS1Beats.size() * 4);
        return moreBeatEvents;
    }

    private BitSet betBitSlice(float[] data, float treshold) {
        // treshold is a value between the average value and the max value
        BitSet binary = new BitSet();
        // 
        int idx = 0;
        // find this file average
        while (idx < data.length) {
            if (data[idx] > treshold) {
                binary.set(idx);
            } else {
                binary.clear(idx);
            }
            idx++;
        }
        return binary;
    }

    private ArrayList betBeatSlice(float[] data, float treshold, int nbBeats) {
        // treshold is a value between the average value and the max value
        ArrayList binary = new ArrayList();
        float ave;
        int winWidth = (data.length / (nbBeats * 32));
        int idx = 0;

        // find this file average
        while (binary.size() < nbBeats) {
            binary.clear();
            idx = 0;
            while (idx < data.length) {
                ave = lastWinAve(data, idx, winWidth);
                if (ave > treshold) {
                    binary.add(idx);
//                    System.out.println("betBeatSlice: " + binary.size()) ;
                }
                idx += 30;
            }
            treshold = (float) (treshold * 0.8);
            if (treshold < 0.05) {
                break;
            }
        }
        return binary;
    }

    /**
     * This aims at finding a reasonable treshold
     *
     * @param dataIn
     * @param earlyS1
     * @param lateS1
     * @param s2Shift
     * @param f
     * @return
     */
    void roughBeatFind(
            float[] dataIn,
            float earlyS1, float lateS1,
            float s2Shift,
            float average,
            float max,
            int nbSecInFile
    ) {
        int beats ;
        RBF_Beats = 0;
        RBF_treshhold = max ;
        ArrayList events;

        do {
            RBF_treshhold = (float) (RBF_treshhold * 0.8) ;
            events = calcBeatRough(dataIn, earlyS1, lateS1, s2Shift, RBF_treshhold, average, max);
            beats = events.size();
            RBF_Beats = (beats * 60) / nbSecInFile ;
        } while (((RBF_Beats < 50) || (RBF_Beats > 150)) && (RBF_treshhold > average));
    }

    ArrayList calcBeatRough(
            float[] dataIn,
            float earlyS1, float lateS1,
            float s2Shift,
            float treshold,
            float ave, 
            float max
    ) {
        ;
        int idxGlobal;
        ArrayList BeatS1Rate = new ArrayList();
        float lastAverage;

        idxGlobal = 1;
        while (idxGlobal < dataIn.length) {
            lastAverage = lastWinAve(dataIn, idxGlobal, 200);
            
            if ((dataIn[idxGlobal] < treshold) && (lastAverage < treshold)) {
                    // Both dataIn and lastAverage are below treshold
                    // So no notable event
                
            }
            
            if ((dataIn[idxGlobal] < treshold) && (lastAverage > treshold)) {
                // We are going down
                             
                BeatS1Rate.add(Integer.valueOf(idxGlobal));

                // try to move idxGlobal a bit, in a effort to thwart spikes
                idxGlobal += s2Shift;
            }

            if ((dataIn[idxGlobal] > treshold) && (lastAverage < treshold)) {
                // We are going up
            }
            
            if ((dataIn[idxGlobal] > treshold) && (lastAverage > treshold)) {
                    // Both dataIn and lastAverage are above treshold
                    // So no notable event
                
            }
            
            idxGlobal++;
        }
        return BeatS1Rate;
    }
}
