
package ML.featureDetection;

import Misc.Tools.FFT;

// Referenced classes of package ML.featureDetection:
//            NormalizeBeat

public class PowerSpectrum
{

    public PowerSpectrum()
    {
        String name = "Power Spectrum";
        String description = "A measure of the power of different frequency components.";
    }

    public float[] extractFeature(float samples[], float sampling_rate, NormalizeBeat norm)
        throws Exception
    {
        FFT fft = new FFT(samples);
        return fft.getPowerSpectrum();
    }
}
