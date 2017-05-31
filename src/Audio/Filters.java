
package Audio;

import java.io.IOException;

public class Filters
{

    public Filters()
    {
    }

    public float[] passBandFilter(float dataIn[], float floor, float ceiling, int samplingRate)
        throws IOException
    {
        float highSamplingRate = ceiling / 2.0F;
        int sizeWindows = (int)((float)samplingRate / highSamplingRate);
        passLowWindow = new float[sizeWindows];
        dataOut1Pass = new float[dataIn.length];
        float avergOld = 0.0F;
        int sampledIndx = 0;
        int dataindx = 0;
        do
        {
            if(dataindx >= dataIn.length)
                break;
            fillPipeHole(dataIn, passLowWindow, dataindx, sizeWindows);
            float avergNew = averageWindow(passLowWindow);
            dataOut1Pass[sampledIndx] = avergNew;
            if(++sampledIndx >= dataOut1Pass.length)
                break;
            avergOld = avergNew;
            dataindx++;
        } while(true);
        return dataOut1Pass;
    }

    public float averageWindow(float pipeholeWindow[])
    {
        float sumPlus = 0.0F;
        float sumMinus = 0.0F;
        for(int idx = 0; idx < pipeholeWindow.length; idx++)
        {
            if(pipeholeWindow[idx] > 0.0F)
                sumPlus += pipeholeWindow[idx];
            if(pipeholeWindow[idx] < 0.0F)
                sumMinus += pipeholeWindow[idx];
        }

        if(sumPlus > -sumMinus)
            return sumPlus / (float)pipeholeWindow.length;
        else
            return sumMinus / (float)pipeholeWindow.length;
    }

    public int fillPipeHole(float dataIn[], float window[], int dataindx, int length)
    {
        int inIdx = dataindx;
        int outIdx;
        for(outIdx = 0; outIdx < length && inIdx < dataIn.length && outIdx < window.length; outIdx++)
        {
            window[outIdx] = dataIn[inIdx];
            inIdx++;
        }

        return outIdx;
    }

    private float passLowWindow[];
    private float dataOut1Pass[];
}
