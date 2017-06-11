
package Misc.sampled;


public class AudioMethodsDSP
{

    public AudioMethodsDSP()
    {
    }

    public static float[][] applyGain(float samples[][], float gain)
    {
        float altered_samples[][] = new float[samples.length][];
        for(int i = 0; i < altered_samples.length; i++)
        {
            altered_samples[i] = new float[samples[i].length];
            for(int j = 0; j < altered_samples[i].length; j++)
                altered_samples[i][j] = samples[i][j] * gain;

        }

        return altered_samples;
    }

    public static void applyGainAndPanning(float samples_to_modify[][], float gain, float panning)
        throws Exception
    {
        if((double)gain < 0.0D || (double)gain > 1.0D)
            throw new Exception((new StringBuilder()).append("Gain of ").append(gain).append(" specified.\nThis value must be between 0.0 and 1.0.").toString());
        if((double)panning < -1D || (double)panning > 1.0D)
            throw new Exception((new StringBuilder()).append("Panning of ").append(panning).append(" specified.\nThis value must be between -1.0 and 1.0.").toString());
        if(samples_to_modify == null)
            throw new Exception("Empty set of samples provided.");
        for(int chan = 0; chan < samples_to_modify.length; chan++)
            if(samples_to_modify[chan] == null)
                throw new Exception((new StringBuilder()).append("Channel ").append(chan).append(" is empty.").toString());

        for(int chan = 0; chan < samples_to_modify.length; chan++)
        {
            for(int samp = 0; samp < samples_to_modify[chan].length; samp++)
            {
                float array[] = samples_to_modify[chan];
                int n = samp;
                array[n] *= gain;
            }

        }

        if(samples_to_modify.length == 2 && (double)panning != 0.0D)
        {
            if((double)panning > 0.0D)
            {
                float left_multiplier = (float)(1.0D - (double)panning);
                for(int samp2 = 0; samp2 < samples_to_modify[0].length; samp2++)
                {
                    float array2[] = samples_to_modify[0];
                    int n2 = samp2;
                    array2[n2] *= left_multiplier;
                }

            }
            if((double)panning < 0.0D)
            {
                float right_multiplier = (float)((double)panning + 1.0D);
                for(int samp2 = 0; samp2 < samples_to_modify[1].length; samp2++)
                {
                    float array3[] = samples_to_modify[1];
                    int n3 = samp2;
                    array3[n3] *= right_multiplier;
                }

            }
        }
    }

    public static float[] getSamplesMixedDownIntoOneChannel(float audio_samples[][])
    {
        if(audio_samples.length == 1)
            return audio_samples[0];
        float number_channels = audio_samples.length;
        int number_samples = audio_samples[0].length;
        float samples_mixed_down[] = new float[number_samples];
        for(int samp = 0; samp < number_samples; samp++)
        {
            float total_so_far = 0.0F;
            for(int chan = 0; (float)chan < number_channels; chan++)
                total_so_far += audio_samples[chan][samp];

            samples_mixed_down[samp] = total_so_far / number_channels;
        }

        return samples_mixed_down;
    }

    public static float[][] clipSamples(float original_samples[][])
        throws Exception
    {
        if(original_samples == null)
            throw new Exception("Empty set of samples to provided.");
        float clipped_samples[][] = new float[original_samples.length][];
        for(int chan = 0; chan < clipped_samples.length; chan++)
        {
            clipped_samples[chan] = new float[original_samples[chan].length];
            for(int samp = 0; samp < clipped_samples[chan].length; samp++)
            {
                if((double)original_samples[chan][samp] < -1D)
                {
                    clipped_samples[chan][samp] = -1F;
                    continue;
                }
                if((double)original_samples[chan][samp] > 1.0D)
                    clipped_samples[chan][samp] = 1.0F;
                else
                    clipped_samples[chan][samp] = original_samples[chan][samp];
            }

        }

        return clipped_samples;
    }

    public static float[] normalizeSamples(float samples_to_normalize[])
    {
        float normalized_samples[] = new float[samples_to_normalize.length];
        for(int samp = 0; samp < normalized_samples.length; samp++)
            normalized_samples[samp] = samples_to_normalize[samp];

        float max_sample_value = 0.0F;
        for(int samp2 = 0; samp2 < normalized_samples.length; samp2++)
            if(Math.abs(normalized_samples[samp2]) > max_sample_value)
                max_sample_value = Math.abs(normalized_samples[samp2]);

        if((double)max_sample_value != 0.0D)
        {
            for(int samp2 = 0; samp2 < normalized_samples.length; samp2++)
            {
                float array[] = normalized_samples;
                int n = samp2;
                array[n] /= max_sample_value;
            }

        }
        return normalized_samples;
    }

    public static float[][] normalizeSamples(float samples_to_normalize[][])
    {
        float normalized_samples[][] = new float[samples_to_normalize.length][samples_to_normalize[0].length];
        for(int chan = 0; chan < normalized_samples.length; chan++)
        {
            for(int samp = 0; samp < normalized_samples[chan].length; samp++)
                normalized_samples[chan][samp] = samples_to_normalize[chan][samp];

        }

        float max_sample_value = 0.0F;
        for(int chan2 = 0; chan2 < normalized_samples.length; chan2++)
        {
            for(int samp2 = 0; samp2 < normalized_samples[chan2].length; samp2++)
                if(Math.abs(normalized_samples[chan2][samp2]) > max_sample_value)
                    max_sample_value = Math.abs(normalized_samples[chan2][samp2]);

        }

        if((double)max_sample_value != 0.0D)
        {
            for(int chan2 = 0; chan2 < normalized_samples.length; chan2++)
            {
                for(int samp2 = 0; samp2 < normalized_samples[chan2].length; samp2++)
                {
                    float array[] = normalized_samples[chan2];
                    int n = samp2;
                    array[n] /= max_sample_value;
                }

            }

        }
        return normalized_samples;
    }

    public static float[][] getCopyOfSamples(float original_samples[][])
    {
        float new_samples[][] = new float[original_samples.length][];
        for(int chan = 0; chan < new_samples.length; chan++)
        {
            new_samples[chan] = new float[original_samples[chan].length];
            for(int samp = 0; samp < new_samples[chan].length; samp++)
                new_samples[chan][samp] = original_samples[chan][samp];

        }

        return new_samples;
    }

    public static int convertTimeToSample(float time, float sampling_rate)
    {
        return Math.round(time * sampling_rate);
    }

    public static float convertSampleToTime(int sample, float sampling_rate)
    {
        return (float)sample / sampling_rate;
    }

    public static float findMaximumSampleValue(int bit_depth)
    {
        int max_sample_value_int = 1;
        for(int i = 0; i < bit_depth - 1; i++)
            max_sample_value_int *= 2;

        float max_sample_value = (float)(--max_sample_value_int) - 1.0F;
        return max_sample_value;
    }

    public static float[] getAutoCorrelation(float signal[], int min_lag, int max_lag)
    {
        float autocorrelation[] = new float[(max_lag - min_lag) + 1];
        for(int lag = min_lag; lag <= max_lag; lag++)
        {
            int auto_indice = lag - min_lag;
            autocorrelation[auto_indice] = 0.0F;
            for(int samp = 0; samp < signal.length - lag; samp++)
            {
                float array[] = autocorrelation;
                int n = auto_indice;
                array[n] += signal[samp] * signal[samp + lag];
            }

        }

        return autocorrelation;
    }

    public static float[] getAutoCorrelationLabels(float sampling_rate, int min_lag, int max_lag)
    {
        float labels[] = new float[(max_lag - min_lag) + 1];
        for(int i = 0; i < labels.length; i++)
            labels[i] = sampling_rate / (float)(i + min_lag);

        return labels;
    }

    public static void applyClickAvoidanceAttenuationEnvelope(float sample_values[][], float click_avoid_env_length, float sample_rate)
        throws Exception
    {
        if(sample_values == null)
            throw new Exception("Empty set of samples provided.");
        if(sample_rate <= 0.0F)
            throw new Exception((new StringBuilder()).append("Given sample rate is ").append(sample_rate).append(" Hz.\nThis value should be greater than zero.").toString());
        if((double)click_avoid_env_length < 0.0D)
            throw new Exception((new StringBuilder()).append("Click avoidance envelope length is ").append(click_avoid_env_length).append(" seconds.\nThis value should be 0.0 seconds or higher.").toString());
        float duration_of_audio = (float)sample_values[0].length / sample_rate;
        if(2D * (double)click_avoid_env_length >= (double)duration_of_audio)
            throw new Exception((new StringBuilder()).append("Click avoidance envelope length is ").append(click_avoid_env_length).append(" seconds.\nThis would lead to combined envelope lengths longer than the provided audio.").toString());
        int sample_duration = (int)(click_avoid_env_length * sample_rate);
        int start_sample_1 = 0;
        int end_sample_1 = sample_duration - 1;
        int start_sample_2 = sample_values[0].length - 1 - sample_duration;
        int end_sample_2 = sample_values[0].length - 1;
        for(int samp = 0; samp <= end_sample_1; samp++)
        {
            float amplitude_multipler = (float)samp / (float)end_sample_1;
            for(int chan = 0; chan < sample_values.length; chan++)
            {
                float array[] = sample_values[chan];
                int n = samp;
                array[n] *= amplitude_multipler;
            }

        }

        for(int samp = start_sample_2; samp <= end_sample_2; samp++)
        {
            float amplitude_multipler = (float)(1.0D - (double)((samp - start_sample_2) / (end_sample_2 - start_sample_2)));
            for(int chan = 0; chan < sample_values.length; chan++)
            {
                float array2[] = sample_values[chan];
                int n2 = samp;
                array2[n2] *= amplitude_multipler;
            }

        }

    }
}
