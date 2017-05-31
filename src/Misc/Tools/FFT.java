
package Misc.Tools;


// Referenced classes of package Misc.Tools:
//            StatsMaths

public class FFT
{

    public FFT(float signal[])
        throws Exception
    {
        real_output = new float[signal.length];
        imaginary_output = new float[signal.length];
        real_output = signal;
        for(int i = 0; i < imaginary_output.length; i++)
            imaginary_output[i] = 0.0F;

        FFT(real_output, imaginary_output, false, false);
    }

    public void FFT(float real_input[], float imaginary_input[], boolean inverse_transform, boolean use_hanning_window)
        throws Exception
    {
        if(imaginary_input != null && real_input.length != imaginary_input.length)
            throw new Exception("Imaginary and real inputs are of different sizes.");
        if(real_input.length < 3)
            throw new Exception((new StringBuilder()).append("Only ").append(real_input.length).append(" samples provided.\nAt least three are needed.").toString());
        int valid_size = StatsMaths.ensureIsPowerOfN(real_input.length, 2);
        if(valid_size != real_input.length)
        {
            float temp[] = new float[valid_size];
            System.arraycopy(real_input, 0, temp, 0, real_input.length);
            for(int i = real_input.length; i < valid_size; i++)
                temp[i] = 0.0F;

            real_input = temp;
            if(imaginary_input == null)
            {
                imaginary_input = new float[valid_size];
                for(int i = 0; i < imaginary_input.length; i++)
                    imaginary_input[i] = 0.0F;

            } else
            {
                temp = new float[valid_size];
                for(int i = 0; i < imaginary_input.length; i++)
                    temp[i] = imaginary_input[i];

                for(int i = imaginary_input.length; i < valid_size; i++)
                    temp[i] = 0.0F;

                imaginary_input = temp;
            }
        } else
        if(imaginary_input == null)
        {
            imaginary_input = new float[valid_size];
            for(int i = 0; i < imaginary_input.length; i++)
                imaginary_input[i] = 0.0F;

        }
        real_output = new float[valid_size];
        System.arraycopy(real_input, 0, real_output, 0, valid_size);
        imaginary_output = new float[valid_size];
        System.arraycopy(imaginary_input, 0, imaginary_output, 0, valid_size);
        if(use_hanning_window)
        {
            for(int i = 0; i < real_output.length; i++)
            {
                float hanning = (float)(0.5D - 0.5D * Math.cos((6.2831853071795862D * (double)i) / (double)valid_size));
                real_output[i] *= hanning;
            }

        }
        int forward_transform = 1;
        if(inverse_transform)
            forward_transform = -1;
        float scale = 1.0F;
        int j = 0;
        for(int i = 0; i < valid_size; i++)
        {
            if(j >= i)
            {
                float tempr = real_output[j] * scale;
                float tempi = imaginary_output[j] * scale;
                real_output[j] = real_output[i] * scale;
                imaginary_output[j] = imaginary_output[i] * scale;
                real_output[i] = tempr;
                imaginary_output[i] = tempi;
            }
            int m;
            for(m = valid_size / 2; m >= 1 && j >= m; m /= 2)
                j -= m;

            j += m;
        }

        int stage = 0;
        int max_spectra_for_stage = 1;
        for(int step_size = 2 * max_spectra_for_stage; max_spectra_for_stage < valid_size; step_size = 2 * max_spectra_for_stage)
        {
            float delta_angle = (float)(((double)forward_transform * 3.1415926535897931D) / (double)max_spectra_for_stage);
            for(int spectra_count = 0; spectra_count < max_spectra_for_stage; spectra_count++)
            {
                float angle = (float)spectra_count * delta_angle;
                float real_correction = (float)Math.cos(angle);
                float imag_correction = (float)Math.sin(angle);
                int right = 0;
                for(int left = spectra_count; left < valid_size; left += step_size)
                {
                    right = left + max_spectra_for_stage;
                    float temp_real = real_correction * real_output[right] - imag_correction * imaginary_output[right];
                    float temp_imag = real_correction * imaginary_output[right] + imag_correction * real_output[right];
                    real_output[right] = real_output[left] - temp_real;
                    imaginary_output[right] = imaginary_output[left] - temp_imag;
                    real_output[left] += temp_real;
                    imaginary_output[left] += temp_imag;
                }

            }

            max_spectra_for_stage = step_size;
            max_spectra_for_stage = step_size;
        }

        output_angle = null;
        output_power = null;
        output_magnitude = null;
    }

    public float[] getMagnitudeSpectrum()
    {
        if(output_magnitude == null)
        {
            int number_unfolded_bins = imaginary_output.length / 2;
            output_magnitude = new float[number_unfolded_bins];
            for(int i = 0; i < output_magnitude.length; i++)
                output_magnitude[i] = (float)(Math.sqrt(real_output[i] * real_output[i] + imaginary_output[i] * imaginary_output[i]) / (double)real_output.length);

        }
        return output_magnitude;
    }

    public float[] getPowerSpectrum()
    {
        if(output_power == null)
        {
            int number_unfolded_bins = imaginary_output.length / 2;
            output_power = new float[number_unfolded_bins];
            for(int i = 0; i < output_power.length; i++)
                output_power[i] = (real_output[i] * real_output[i] + imaginary_output[i] * imaginary_output[i]) / (float)real_output.length;

        }
        return output_power;
    }

    public float[] getPhaseAngles()
    {
        if(output_angle == null)
        {
            int number_unfolded_bins = imaginary_output.length / 2;
            output_angle = new float[number_unfolded_bins];
            for(int i = 0; i < output_angle.length; i++)
            {
                if((double)imaginary_output[i] == 0.0D && (double)real_output[i] == 0.0D)
                    output_angle[i] = 0.0F;
                else
                    output_angle[i] = (float)((Math.atan(imaginary_output[i] / real_output[i]) * 180D) / 3.1415926535897931D);
                if((double)real_output[i] < 0.0D && (double)imaginary_output[i] == 0.0D)
                {
                    output_angle[i] = 180F;
                    continue;
                }
                if((double)real_output[i] < 0.0D && (double)imaginary_output[i] == -0D)
                {
                    output_angle[i] = -180F;
                    continue;
                }
                if((double)real_output[i] < 0.0D && (double)imaginary_output[i] > 0.0D)
                {
                    output_angle[i] += 180D;
                    continue;
                }
                if((double)real_output[i] < 0.0D && (double)imaginary_output[i] < 0.0D)
                    output_angle[i] += -180D;
            }

        }
        return output_angle;
    }

    public float[] getBinLabels(float sampling_rate)
    {
        int number_bins = real_output.length;
        float bin_width = sampling_rate / (float)number_bins;
        int number_unfolded_bins = imaginary_output.length / 2;
        float labels[] = new float[number_unfolded_bins];
        labels[0] = 0.0F;
        for(int bin = 1; bin < labels.length; bin++)
            labels[bin] = (float)bin * bin_width;

        return labels;
    }

    public float[] getRealValues()
    {
        return real_output;
    }

    public float[] getImaginaryValues()
    {
        return imaginary_output;
    }

    private float real_output[];
    private float imaginary_output[];
    private float output_angle[];
    private float output_magnitude[];
    private float output_power[];
}
