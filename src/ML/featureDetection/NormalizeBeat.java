/*
 * First, the algorithm applies a low-pass filter. 
 * Filtered audio data are divided into windows of samples.
 * A peak detection algorithm is applied to each window and identifies as peaks 
 * all values above a certain threshold. 

 * For each peak the distance in number of samples between it and its neighbouring peaks is stored.
 * For each distance between peaks the algorithm counts how many times it has been detected. 
 * Once all windows are processed the algorithm has built a map distanceHistogram where for each 
 * possible distance its number of occurrences is stored.
 */
package ML.featureDetection;

import java.util.ArrayList;

/**
 *
 * @author jplr
 */
public class NormalizeBeat {

    // the various beats frequencies
    static float[] auto_correlation;

    // the label for each beat in "auto_correlation"
    static float[] labels;

    // bag of beats probably including S1, S2, S3, S4 sounds
    private ArrayList moreBeatEvents = new ArrayList();

    // S1 beats as found in data flow, maybe wrong because of spikes
    private ArrayList probableS1Beats = new ArrayList();

    public NormalizeBeat() {
    }

    /**
     * Make amplitude "average" to enable comparison between sound files from
     * different origins. 
     * Also shorten the record to the real end, not the one
     * in the GUI button. 
     * Also correct bias, average = 0
     */
    public float[] normalizeAmplitude(float[] dataIn) {

        // sum all data slots and find max value
        float maxValue = 0, averPlus = 0, averMinus = 0, shift = 0;
        float absData ;
        boolean endFlag = true;
        int endIdx = 0;

        /*
	*  shorten the record to the real end, not the one in
	* the GUI button
         */
        int idx = dataIn.length - 1;
        while (idx >= 0) {
            if ((dataIn[idx] == 0) && (endFlag == true)) {
                // no need to process the end of the loop
                idx--;
                continue;
            }
            if ((dataIn[idx] != 0) && (endFlag == true)) {
                // then the array has no more ends with "0"
                // record where it ends, in "endIdx".
                endIdx = idx;
                endFlag = false;
            }
            
            if(dataIn[idx] > 0) {
                averPlus += dataIn[idx] ;
            }
            else {
                averMinus += dataIn[idx] ;                
            }

            // Get absolute value
            absData = dataIn[idx] ;
            if(absData < 0) {
                absData = - absData ;
            }
            // It is not a good idea to let maxValue to go higher than 0.3
            // as it is certainly some spike that propelled it so high
            // and spikes are quite rare
            if ((absData > maxValue) && (maxValue < 0.3)) {
                if ((absData > (maxValue * 1.01)) && (maxValue > 0)) {
                    // we do not want to react to a random spike, but increase maxValue just in case
                    maxValue = (float) (maxValue * 1.01);
                } else {
                    maxValue = absData;
                }

                // debug
                if (maxValue > 0.8) {
                    int y = 7;
                }
            }
            idx--;
        }

        float[] dataOut = new float[endIdx + 1];

        /*
	* Normalize amplitude
         */
        shift = (averPlus + averMinus) / dataIn.length ;
        float multipl = (float) 1 / maxValue;

        // adjust each slot in the "dataIn" array
        idx = 0;
        while (idx < endIdx) {
            dataOut[idx] = dataIn[idx] - shift;
            idx++;
        }

        idx = 0;
        while (idx < endIdx) {
            dataOut[idx] = dataOut[idx] * multipl;
            idx++;
        }
        
        /*
	* Normalize sampling rate
         */
        return dataOut;
    }
}
