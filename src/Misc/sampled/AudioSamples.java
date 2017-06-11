
package Misc.sampled;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

// Referenced classes of package Misc.sampled:
//            AudioMethodsGeneral, AudioMethodsDSP

public class AudioSamples
{

    public AudioSamples(File audio_file, String unique_identifier, boolean normalize_if_clipped)
        throws Exception
    {
        if(!audio_file.exists())
            throw new Exception((new StringBuilder()).append("File ").append(audio_file.getName()).append(" does not exist.").toString());
        if(audio_file.isDirectory())
            throw new Exception((new StringBuilder()).append("File ").append(audio_file.getName()).append(" is a directory.").toString());
        AudioInputStream audio_input_stream = null;
        try
        {
            audio_input_stream = AudioSystem.getAudioInputStream(audio_file);
        }
        catch(UnsupportedAudioFileException ex)
        {
            throw new Exception((new StringBuilder()).append("File ").append(audio_file.getName()).append(" has an unsupported audio format.").toString());
        }
        catch(IOException ex2)
        {
            throw new Exception((new StringBuilder()).append("File ").append(audio_file.getName()).append(" is not readable.").toString());
        }
        AudioInputStream converted_audio = AudioMethodsGeneral.getConvertedAudioStream(audio_input_stream);
        channel_samples = AudioMethodsGeneral.extractSampleValues(converted_audio);
        samples = AudioMethodsDSP.getSamplesMixedDownIntoOneChannel(channel_samples);
        if(channel_samples.length == 1)
            channel_samples = (float[][])null;
        audio_format = converted_audio.getFormat();
        unique_ID = unique_identifier;
        if(normalize_if_clipped)
            normalizeIfClipped();
        converted_audio.close();
    }

    public AudioSamples(AudioInputStream audio_input_stream, String unique_identifier, boolean normalize_if_clipped)
        throws Exception
    {
        if(audio_input_stream == null)
            throw new Exception("Given AudioInputStream is empty.");
        unique_ID = unique_identifier;
        AudioInputStream converted_audio = AudioMethodsGeneral.getConvertedAudioStream(audio_input_stream);
        channel_samples = AudioMethodsGeneral.extractSampleValues(converted_audio);
        samples = AudioMethodsDSP.getSamplesMixedDownIntoOneChannel(channel_samples);
        if(channel_samples.length == 1)
            channel_samples = (float[][])null;
        audio_format = converted_audio.getFormat();
        if(normalize_if_clipped)
            normalizeIfClipped();
        converted_audio.close();
    }

    public AudioSamples(float audio_samples[][], AudioFormat audio_format, String unique_identifier, boolean normalize_if_clipped)
        throws Exception
    {
        if(audio_samples == null)
            throw new Exception("Given audio samples array is empty.");
        for(int chan = 0; chan < audio_samples.length; chan++)
            if(audio_samples[chan] == null)
                throw new Exception("One or more channels of given audio samples array is empty.");

        int number_samples = audio_samples[0].length;
        for(int chan2 = 0; chan2 < audio_samples.length; chan2++)
            if(audio_samples[chan2].length != number_samples)
                throw new Exception("Different channels of given audio samples array have a\ndifferent number of samples.");

        if(audio_format == null)
            throw new Exception("Null audio format specified for samples.");
        if(audio_format.getChannels() != audio_samples.length)
            throw new Exception((new StringBuilder()).append("The specified samples have ").append(audio_samples.length).append(" channels but\nthe specified audio format has ").append(audio_format.getChannels()).append(" channels.\nThese must be the same.").toString());
        unique_ID = unique_identifier;
        samples = AudioMethodsDSP.getSamplesMixedDownIntoOneChannel(audio_samples);
        if(audio_samples.length == 1)
            channel_samples = (float[][])null;
        else
            channel_samples = AudioMethodsDSP.getCopyOfSamples(audio_samples);
        this.audio_format = AudioMethodsGeneral.getConvertedAudioFormat(audio_format);
        if(normalize_if_clipped)
            normalizeIfClipped();
    }

    public AudioSamples(float audio_samples[][], float sampling_rate, String unique_identifier, boolean normalize_if_clipped)
        throws Exception
    {
        if(audio_samples == null)
            throw new Exception("Given audio samples array is empty.");
        for(int chan = 0; chan < audio_samples.length; chan++)
            if(audio_samples[chan] == null)
                throw new Exception("One or more channels of given audio samples array is empty.");

        int number_samples = audio_samples[0].length;
        for(int chan2 = 0; chan2 < audio_samples.length; chan2++)
            if(audio_samples[chan2].length != number_samples)
                throw new Exception("Different channels of given audio samples array have a\ndifferent number of samples.");

        unique_ID = unique_identifier;
        samples = AudioMethodsDSP.getSamplesMixedDownIntoOneChannel(audio_samples);
        if(audio_samples.length == 1)
            channel_samples = (float[][])null;
        else
            channel_samples = AudioMethodsDSP.getCopyOfSamples(audio_samples);
        audio_format = getDefaultAudioFormat(sampling_rate);
        if(normalize_if_clipped)
            normalizeIfClipped();
    }

    public AudioSamples getCopyOfAudioSamples()
        throws Exception
    {
        String new_unique_ID = new String(unique_ID);
        float new_channel_samples[][] = (float[][])null;
        if(channel_samples != null)
        {
            new_channel_samples = new float[channel_samples.length][];
            for(int i = 0; i < new_channel_samples.length; i++)
            {
                new_channel_samples[i] = new float[channel_samples[i].length];
                for(int j = 0; j < new_channel_samples[i].length; j++)
                    new_channel_samples[i][j] = channel_samples[i][j];

            }

        } else
        {
            new_channel_samples = new float[1][samples.length];
            for(int i = 0; i < samples.length; i++)
                new_channel_samples[0][i] = samples[i];

        }
        AudioFormat new_audio_format = new AudioFormat(audio_format.getEncoding(), audio_format.getSampleRate(), audio_format.getSampleSizeInBits(), audio_format.getChannels(), audio_format.getFrameSize(), audio_format.getFrameRate(), audio_format.isBigEndian());
        if(audio_format != null)
            new_audio_format = new AudioFormat(audio_format.getEncoding(), audio_format.getSampleRate(), audio_format.getSampleSizeInBits(), audio_format.getChannels(), audio_format.getFrameSize(), audio_format.getFrameRate(), audio_format.isBigEndian());
        return new AudioSamples(new_channel_samples, new_audio_format, new_unique_ID, false);
    }

    public String getRecordingInfo()
    {
        String return_string = AudioMethodsGeneral.getAudioFormatData(audio_format);
        String number_samples = (new StringBuilder()).append(getNumberSamplesPerChannel()).append(" samples\n").toString();
        String duration = (new StringBuilder()).append(getDuration()).append(" seconds\n").toString();
        String max_sample_value = (new StringBuilder()).append(getMaximumAmplitude()).append("\n").toString();
        return_string = (new StringBuilder()).append(return_string).append(new String((new StringBuilder()).append("SAMPLES PER CHANNEL: ").append(number_samples).toString())).toString();
        return_string = (new StringBuilder()).append(return_string).append(new String((new StringBuilder()).append("DURATION: ").append(duration).toString())).toString();
        return_string = (new StringBuilder()).append(return_string).append(new String((new StringBuilder()).append("MAX SIGNAL AMPLITUDE: ").append(max_sample_value).toString())).toString();
        return return_string;
    }

    public String getUniqueIdentifier()
    {
        return unique_ID;
    }

    public AudioFormat getAudioFormat()
    {
        return audio_format;
    }

    public float getSamplingRate()
    {
        return audio_format.getSampleRate();
    }

    public float getSamplingRateAsDouble()
    {
        return audio_format.getSampleRate();
    }

    public int getNumberSamplesPerChannel()
    {
        return samples.length;
    }

    public float getDuration()
    {
        return convertSampleIndexToTime(samples.length - 1);
    }

    public int getNumberChannels()
    {
        if(channel_samples == null)
            return 1;
        else
            return channel_samples.length;
    }

    public float[] getSamplesMixedDown()
    {
        return samples;
    }

    public float[] getSamplesMixedDown(int start_sample, int end_sample)
        throws Exception
    {
        if(start_sample < 0)
            throw new Exception((new StringBuilder()).append("Requested audio starting at sample ").append(start_sample).append("\nStart sample indice must be 0 or greater.").toString());
        if(end_sample >= samples.length)
            throw new Exception((new StringBuilder()).append("Requested audio ending at sample ").append(end_sample).append("\nA total of ").append(samples.length).append("samples are present.\nRequested ending sample indice must be less than this.").toString());
        if(start_sample >= end_sample)
            throw new Exception((new StringBuilder()).append("Requested audio starting at sample ").append(start_sample).append(" and ending at sample ").append(end_sample).append(".\nRequested start sample indice must be less than requested\nend sample indice.").toString());
        int number_samples = end_sample - start_sample;
        float sample_segment[] = new float[number_samples];
        for(int samp = start_sample; samp <= end_sample; samp++)
            sample_segment[samp - start_sample] = samples[samp];

        return sample_segment;
    }

    public float[] getSamplesMixedDown(float start_time, float end_time)
        throws Exception
    {
        int start_sample = convertTimeToSampleIndex(start_time);
        int end_sample = convertTimeToSampleIndex(end_time);
        return getSamplesMixedDown(start_sample, end_sample);
    }

    public float[][] getSampleWindowsMixedDown(int window_size)
        throws Exception
    {
        if(window_size < 1)
            throw new Exception((new StringBuilder()).append("Window size of ").append(window_size).append(" specified.\nThis value must be above 0.").toString());
        int number_windows = samples.length / window_size;
        if(samples.length % window_size != 0)
            number_windows++;
        float windowed_samples[][] = new float[number_windows][window_size];
        for(int win = 0; win < number_windows; win++)
        {
            if(win != number_windows - 1)
            {
                for(int samp = 0; samp < window_size; samp++)
                    windowed_samples[win][samp] = samples[win * window_size + samp];

                continue;
            }
            for(int samp = 0; samp < window_size; samp++)
                if(win * window_size + samp < samples.length)
                    windowed_samples[win][samp] = samples[win * window_size + samp];
                else
                    windowed_samples[win][samp] = 0.0F;

        }

        return windowed_samples;
    }

    public float[][] getSampleWindowsMixedDown(float window_duration)
        throws Exception
    {
        int window_size = convertTimeToSampleIndex(window_duration);
        return getSampleWindowsMixedDown(window_size);
    }

    public AudioInputStream getAudioInputStreamMixedDown()
        throws Exception
    {
        AudioFormat mixed_down_audio_format = new AudioFormat(audio_format.getSampleRate(), audio_format.getSampleSizeInBits(), 1, true, audio_format.isBigEndian());
        float samples_to_convert[][] = {
            samples
        };
        AudioInputStream audio_input_stream = AudioMethodsGeneral.convertToAudioInputStream(samples_to_convert, mixed_down_audio_format);
        return audio_input_stream;
    }

    public AudioInputStream getAudioInputStreamMixedDown(int start_sample, int end_sample)
        throws Exception
    {
        AudioFormat mixed_down_audio_format = new AudioFormat(audio_format.getSampleRate(), audio_format.getSampleSizeInBits(), 1, true, audio_format.isBigEndian());
        float sample_portion[] = getSamplesMixedDown(start_sample, end_sample);
        float samples_to_convert[][] = {
            sample_portion
        };
        AudioInputStream audio_input_stream = AudioMethodsGeneral.convertToAudioInputStream(samples_to_convert, mixed_down_audio_format);
        return audio_input_stream;
    }

    public AudioInputStream getAudioInputStreamMixedDown(float start_time, float end_time)
        throws Exception
    {
        AudioFormat mixed_down_audio_format = new AudioFormat(audio_format.getSampleRate(), audio_format.getSampleSizeInBits(), 1, true, audio_format.isBigEndian());
        float sample_portion[] = getSamplesMixedDown(start_time, end_time);
        float samples_to_convert[][] = {
            sample_portion
        };
        AudioInputStream audio_input_stream = AudioMethodsGeneral.convertToAudioInputStream(samples_to_convert, mixed_down_audio_format);
        return audio_input_stream;
    }

    public float[][] getSamplesChannelSegregated()
    {
        if(channel_samples == null)
        {
            float formatted_samples[][] = {
                samples
            };
            return formatted_samples;
        } else
        {
            return channel_samples;
        }
    }

    public float[][] getSamplesChannelSegregated(int start_sample, int end_sample)
        throws Exception
    {
        if(start_sample < 0)
            throw new Exception((new StringBuilder()).append("Requested audio starting at sample ").append(start_sample).append("\nStart sample indice must be 0 or greater.").toString());
        if(end_sample >= samples.length)
            throw new Exception((new StringBuilder()).append("Requested audio ending at sample ").append(end_sample).append("\nA total of ").append(samples.length).append("samples are present.\nRequested ending sample indice must be less than this.").toString());
        if(start_sample >= end_sample)
            throw new Exception((new StringBuilder()).append("Requested audio starting at sample ").append(start_sample).append(" and ending at sample ").append(end_sample).append(".\nRequested start sample indice must be less than requested\nend sample indice.").toString());
        int number_samples = (end_sample - start_sample) + 1;
        float sample_segment[][];
        if(channel_samples == null)
        {
            sample_segment = new float[1][number_samples];
            for(int samp = start_sample; samp <= end_sample; samp++)
                sample_segment[0][samp - start_sample] = samples[samp];

            return sample_segment;
        }
        sample_segment = new float[channel_samples.length][number_samples];
        for(int chan = 0; chan < channel_samples.length; chan++)
        {
            for(int samp2 = start_sample; samp2 <= end_sample; samp2++)
                sample_segment[chan][samp2 - start_sample] = channel_samples[chan][samp2];

        }

        return sample_segment;
    }

    public float[][] getSamplesChannelSegregated(float start_time, float end_time)
        throws Exception
    {
        int start_sample = convertTimeToSampleIndex(start_time);
        int end_sample = convertTimeToSampleIndex(end_time);
        return getSamplesChannelSegregated(start_sample, end_sample);
    }

    public float[][][] getSampleWindowsChannelSegregated(int window_size)
        throws Exception
    {
        if(channel_samples == null)
        {
            float windowed_samples[][][] = {
                getSampleWindowsMixedDown(window_size)
            };
            return windowed_samples;
        }
        if(window_size < 1)
            throw new Exception((new StringBuilder()).append("Window size of ").append(window_size).append(" specified.\nThis value must be above 0.").toString());
        int number_windows = samples.length / window_size;
        if(samples.length % window_size != 0)
            number_windows++;
        float windowed_samples2[][][] = new float[channel_samples.length][number_windows][window_size];
        for(int chan = 0; chan < channel_samples.length; chan++)
        {
            for(int win = 0; win < number_windows; win++)
            {
                if(win != number_windows - 1)
                {
                    for(int samp = 0; samp < window_size; samp++)
                        windowed_samples2[chan][win][samp] = channel_samples[chan][win * window_size + samp];

                    continue;
                }
                for(int samp = 0; samp < window_size; samp++)
                    if(win * window_size + samp < samples.length)
                        windowed_samples2[chan][win][samp] = channel_samples[chan][win * window_size + samp];
                    else
                        windowed_samples2[chan][win][samp] = 0.0F;

            }

        }

        return windowed_samples2;
    }

    public float[][][] getSampleWindowsChannelSegregated(float window_duration)
        throws Exception
    {
        int window_size = convertTimeToSampleIndex(window_duration);
        return getSampleWindowsChannelSegregated(window_size);
    }

    public AudioInputStream getAudioInputStreamChannelSegregated()
        throws Exception
    {
        float samples_to_convert[][] = getSamplesChannelSegregated();
        AudioInputStream audio_input_stream = AudioMethodsGeneral.convertToAudioInputStream(samples_to_convert, audio_format);
        return audio_input_stream;
    }

    public AudioInputStream getAudioInputStreamChannelSegregated(int start_sample, int end_sample)
        throws Exception
    {
        float samples_to_convert[][] = getSamplesChannelSegregated(start_sample, end_sample);
        AudioInputStream audio_input_stream = AudioMethodsGeneral.convertToAudioInputStream(samples_to_convert, audio_format);
        return audio_input_stream;
    }

    public AudioInputStream getAudioInputStreamChannelSegregated(float start_time, float end_time)
        throws Exception
    {
        float samples_to_convert[][] = getSamplesChannelSegregated(start_time, end_time);
        AudioInputStream audio_input_stream = AudioMethodsGeneral.convertToAudioInputStream(samples_to_convert, audio_format);
        return audio_input_stream;
    }

    public void saveAudio(File save_file, boolean multi_channel, javax.sound.sampled.AudioFileFormat.Type save_file_type, boolean normalize_if_clipped)
        throws Exception
    {
        if(save_file == null)
            throw new Exception("No file provided to save to.");
        if(normalize_if_clipped)
            normalizeIfClipped();
        AudioInputStream audio_input_stream = null;
        if(multi_channel)
            audio_input_stream = getAudioInputStreamChannelSegregated();
        else
            audio_input_stream = getAudioInputStreamMixedDown();
        if(save_file_type == null)
            save_file_type = javax.sound.sampled.AudioFileFormat.Type.WAVE;
        if(save_file.exists())
            save_file.delete();
        AudioSystem.write(audio_input_stream, save_file_type, save_file);
    }

    public void normalizeIfClipped()
    {
        if((double)checkMixedDownSamplesForClipping() > 0.0D)
            normalizeMixedDownSamples();
        if((double)checkChannelSegregatedSamplesForClipping() > 0.0D)
            normalizeChannelSegretatedSamples();
    }

    public float getMaximumAmplitude()
    {
        float max_amplitude = 0.0F;
        if(channel_samples != null)
        {
            for(int chan = 0; chan < channel_samples.length; chan++)
            {
                for(int samp = 0; samp < channel_samples[chan].length; samp++)
                    if(Math.abs(channel_samples[chan][samp]) > max_amplitude)
                        max_amplitude = Math.abs(channel_samples[chan][samp]);

            }

        } else
        {
            for(int samp2 = 0; samp2 < samples.length; samp2++)
                if(Math.abs(samples[samp2]) > max_amplitude)
                    max_amplitude = Math.abs(samples[samp2]);

        }
        return max_amplitude;
    }

    public float checkMixedDownSamplesForClipping()
    {
        float max_difference = -1F;
        for(int samp = 0; samp < samples.length; samp++)
        {
            if((double)Math.abs(samples[samp]) <= 1.0D)
                continue;
            float difference = (float)((double)Math.abs(samples[samp]) - 1.0D);
            if(difference > max_difference)
                max_difference = difference;
        }

        return max_difference;
    }

    public float checkChannelSegregatedSamplesForClipping()
    {
        float max_difference = -1F;
        if(channel_samples != null)
        {
            for(int chan = 0; chan < channel_samples.length; chan++)
            {
                for(int samp = 0; samp < channel_samples[chan].length; samp++)
                {
                    if((double)Math.abs(channel_samples[chan][samp]) <= 1.0D)
                        continue;
                    float difference = (float)((double)Math.abs(channel_samples[chan][samp]) - 1.0D);
                    if(difference > max_difference)
                        max_difference = difference;
                }

            }

        } else
        {
            max_difference = checkMixedDownSamplesForClipping();
        }
        return max_difference;
    }

    public void normalizeMixedDownSamples()
    {
        samples = AudioMethodsDSP.normalizeSamples(samples);
    }

    public void normalizeChannelSegretatedSamples()
    {
        if(channel_samples != null)
            channel_samples = AudioMethodsDSP.normalizeSamples(channel_samples);
        else
            normalizeMixedDownSamples();
    }

    public void normalize()
    {
        normalizeChannelSegretatedSamples();
        if(channel_samples != null)
            normalizeMixedDownSamples();
    }

    public void setSamples(float new_samples[][])
        throws Exception
    {
        if(new_samples == null)
            throw new Exception("An empty set of samples provided.");
        int number_samples = -1;
        for(int chan = 0; chan < new_samples.length; chan++)
        {
            if(new_samples[chan] == null)
                throw new Exception((new StringBuilder()).append("Channel ").append(chan).append(" of the given samples is empty.").toString());
            if(number_samples != -1 && number_samples != new_samples[chan].length)
                throw new Exception("Different channels have different numbers of samples.");
            number_samples = new_samples[chan].length;
        }

        if(channel_samples == null)
        {
            if(new_samples.length != 1)
                throw new Exception((new StringBuilder()).append("Given samples have ").append(new_samples.length).append(" channels.\nOnly one channel should be present.").toString());
            samples = new float[number_samples];
            for(int samp = 0; samp < samples.length; samp++)
                samples[samp] = new_samples[0][samp];

        } else
        {
            if(new_samples.length != channel_samples.length)
                throw new Exception((new StringBuilder()).append("Given samples have ").append(new_samples.length).append(" channels.\n").append(channel_samples.length).append(" channel should be present.").toString());
            channel_samples = new float[new_samples.length][number_samples];
            for(int chan = 0; chan < channel_samples.length; chan++)
            {
                for(int samp2 = 0; samp2 < channel_samples[chan].length; samp2++)
                    channel_samples[chan][samp2] = new_samples[chan][samp2];

            }

            samples = AudioMethodsDSP.getSamplesMixedDownIntoOneChannel(channel_samples);
        }
    }

    private AudioFormat getDefaultAudioFormat(float sampling_rate)
    {
        int bit_depth = 16;
        boolean signed = true;
        boolean big_endian = true;
        int channels = 1;
        if(channel_samples != null)
            channels = channel_samples.length;
        return new AudioFormat(sampling_rate, 16, channels, true, true);
    }

    private float convertSampleIndexToTime(int sample_index)
    {
        if(sample_index < 0)
            sample_index = 0;
        else
        if(sample_index >= samples.length)
            sample_index = samples.length - 1;
        float time = (float)sample_index / audio_format.getSampleRate();
        return time;
    }

    private int convertTimeToSampleIndex(float time)
    {
        int sample_index = (int)(time * audio_format.getSampleRate());
        if(sample_index < 0)
            return 0;
        if(sample_index >= samples.length)
            return samples.length - 1;
        else
            return sample_index;
    }

    protected String unique_ID;
    protected float samples[];
    protected float channel_samples[][];
    protected AudioFormat audio_format;
}
