/*
 * This resample to 4000 Hz with interpolation
 */
package ML.featureDetection;

import Audio.Filters;

/**
 *
 * @author jplr
 */
public class Resample {

    private static float[] passLowWindow;
    private static float[] dataOut1Pass;

    /**
     * This is only for down sampling, i.e. newSamplingRate < samplingRate
     * 
     * @param dataIn
     * @param samplingRate
     * @param newSamplingRate
     * @return 
     */
    public float[] downSample(float[] dataIn, int samplingRate, int newSamplingRate) {
        
        Filters filt = new Filters() ;
        // it is rounded to the floor value, as it is integer arithmetics
        int sizeWindows = 8 * (samplingRate / newSamplingRate) ;

        // passLowWindow is a FIFO with dataIn for the current downsized window
        // that come from the incoming dataIn flow
        passLowWindow = new float[sizeWindows];

        // dataOut1Pass is much shorter as the sampling rate is lower
        dataOut1Pass = new float[dataIn.length];

        // Average of PipeHole
        float ave;

        // sampledIndx is a pointer on the sampled dataIn
        int sampledIndx = 0;

        // dataindx is a pointer on the current dataIn in incoming dataIn flow
        int dataindx = 0;

        while (dataindx < dataIn.length) {
            // Attempt to fill PipeHole with dataIn starting at dataindx, for an amount of sizeWindows
            filt.fillPipeHole(dataIn, passLowWindow, dataindx, sizeWindows);

            // Obtain average of PipeHole
            ave = filt.averageWindow(passLowWindow);

            // Send average to output
            dataOut1Pass[sampledIndx] = ave;

            // increment pointers
            sampledIndx++;
            if (sampledIndx >= dataOut1Pass.length) {
                // necessary because float to integer conversion
                break;
            }
            dataindx++;
        }
        return dataOut1Pass;
    }
}
