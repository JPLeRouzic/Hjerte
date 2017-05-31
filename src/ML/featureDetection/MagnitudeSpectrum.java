
package ML.featureDetection;

import Misc.Tools.FFT;

// Referenced classes of package ML.featureDetection:
//            NormalizeBeat

public class MagnitudeSpectrum
{

    public MagnitudeSpectrum()
    {
        String name = "Magnitude Spectrum";
        String description = "A measure of the strength of different frequency components.";
    }

    public float[] extractFeature(float samples[], float sampling_rate, NormalizeBeat norm)
        throws Exception
    {
        FFT fft = new FFT(samples);
        float un[] = fft.getMagnitudeSpectrum();
        return un;
    }
}
