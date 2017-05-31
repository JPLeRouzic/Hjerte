package Audio;

import Misc.sampled.AudioEventLineListener;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import javax.sound.sampled.*;

public class AudioMethods {

    public AudioMethods() {
    }

    public static AudioFormat getCopyOfAudioFormat(AudioFormat old_audio_format) {
        return new AudioFormat(old_audio_format.getEncoding(), old_audio_format.getSampleRate(), old_audio_format.getSampleSizeInBits(), old_audio_format.getChannels(), old_audio_format.getFrameSize(), old_audio_format.getFrameRate(), old_audio_format.isBigEndian());
    }

    public static String getAudioFormatData(AudioFormat audio_format) {
        String encoding = (new StringBuilder()).append(audio_format.getEncoding().toString()).append("\n").toString();
        String endian;
        if (audio_format.isBigEndian()) {
            endian = "big-endian\n";
        } else {
            endian = "little-endian\n";
        }
        String sampling_rate = (new StringBuilder()).append(audio_format.getSampleRate() / 1000F).append(" kHz\n").toString();
        String bit_depth = (new StringBuilder()).append(audio_format.getSampleSizeInBits()).append(" bits\n").toString();
        String channels;
        if (audio_format.getChannels() == 1) {
            channels = "mono\n";
        } else {
            if (audio_format.getChannels() == 2) {
                channels = "stereo\n";
            } else {
                channels = (new StringBuilder()).append(audio_format.getChannels()).append(" channels\n").toString();
            }
        }
        String frame_size = (new StringBuilder()).append(8 * audio_format.getFrameSize()).append(" bits\n").toString();
        String frame_rate = (new StringBuilder()).append(audio_format.getFrameRate()).append(" frames per second\n").toString();
        String additional_properties = (new StringBuilder()).append(audio_format.properties()).append("\n").toString();
        String data = new String();
        data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("SAMPLING RATE: ").append(sampling_rate).toString())).toString();
        data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("BIT DEPTH: ").append(bit_depth).toString())).toString();
        data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("CHANNELS: ").append(channels).toString())).toString();
        data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("FRAME SIZE: ").append(frame_size).toString())).toString();
        data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("FRAME RATE: ").append(frame_rate).toString())).toString();
        data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("ENCODING: ").append(encoding).toString())).toString();
        data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("BYTE ORDER: ").append(endian).toString())).toString();
        data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("PROPERTIES: ").append(additional_properties).toString())).toString();
        return data;
    }

    public static String getAudioFileFormatData(File file)
            throws Exception {
        try {
            String data;
            AudioFileFormat audio_file_format = AudioSystem.getAudioFileFormat(file);
            String file_name = (new StringBuilder()).append(file.getName()).append("\n").toString();
            String file_type = (new StringBuilder()).append(audio_file_format.getType().toString()).append("\n").toString();
            String file_size = (new StringBuilder()).append(audio_file_format.getByteLength() / 1024).append(" kilobytes\n").toString();
            String length_of_audio_data = (new StringBuilder()).append(audio_file_format.getFrameLength()).append(" sample frames\n").toString();
            String time_duration = (new StringBuilder()).append((float) audio_file_format.getFrameLength() / audio_file_format.getFormat().getFrameRate()).append(" seconds\n").toString();
            String additional_properties = (new StringBuilder()).append(audio_file_format.properties()).append("\n").toString();
            data = new String();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("FILE NAME: ").append(file_name).toString())).toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("FILE TYPE: ").append(file_type).toString())).toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("FILE SIZE: ").append(file_size).toString())).toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("FRAMES OF AUDIO DATA: ").append(length_of_audio_data).toString())).toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("TIME DURATION: ").append(time_duration).toString())).toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("PROPERTIES: ").append(additional_properties).toString())).toString();
            data = (new StringBuilder()).append(data).append("\n").append(getAudioFormatData(audio_file_format.getFormat())).toString();
            return data;
        } catch (UnsupportedAudioFileException ex) {
            throw new Exception("File " + file.getName() + " has an unsupported audio format.");
        } catch (IOException ex) {
            throw new Exception("File " + file.getName() + " is not readable.");
        }
    }

    public static String[] getAvailableFileFormatTypes() {
        javax.sound.sampled.AudioFileFormat.Type file_types[] = AudioSystem.getAudioFileTypes();
        String file_type_labels[] = new String[file_types.length];
        for (int i = 0; i < file_types.length; i++) {
            file_type_labels[i] = file_types[file_types.length - 1 - i].toString();
        }

        return file_type_labels;
    }

    public static javax.sound.sampled.AudioFileFormat.Type getAudioFileFormatType(String file_type_name) {
        if (file_type_name.equals("WAVE")) {
            return javax.sound.sampled.AudioFileFormat.Type.WAVE;
        }
        if (file_type_name.equals("AIFF")) {
            return javax.sound.sampled.AudioFileFormat.Type.AIFF;
        }
        if (file_type_name.equals("AIFC")) {
            return javax.sound.sampled.AudioFileFormat.Type.AIFC;
        }
        if (file_type_name.equals("AU")) {
            return javax.sound.sampled.AudioFileFormat.Type.AU;
        }
        if (file_type_name.equals("SND")) {
            return javax.sound.sampled.AudioFileFormat.Type.SND;
        } else {
            return null;
        }
    }

    public static String getAvailableMixerData() {
        javax.sound.sampled.Mixer.Info mixer_info[] = AudioSystem.getMixerInfo();
        String data = new String();
        for (int i = 0; i < mixer_info.length; i++) {
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("INDEX: ").append(i).append("\n").toString())).toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("NAME: ").append(mixer_info[i].getName()).toString())).append("\n").toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("VERSION: ").append(mixer_info[i].getVersion()).toString())).append("\n").toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("VENDOR: ").append(mixer_info[i].getVendor()).toString())).append("\n").toString();
            data = (new StringBuilder()).append(data).append(new String((new StringBuilder()).append("DESCRIPTION: ").append(mixer_info[i].getDescription()).toString())).append("\n").toString();
            data = (new StringBuilder()).append(data).append(new String("\n")).toString();
        }

        return data;
    }

    public static Mixer getMixer(int mixer_index, AudioEventLineListener listener) {
        javax.sound.sampled.Mixer.Info mixer_info[] = AudioSystem.getMixerInfo();
        Mixer mixer = AudioSystem.getMixer(mixer_info[mixer_index]);
        if (listener != null) {
            mixer.addLineListener(listener);
        }
        return mixer;
    }

    public static TargetDataLine getTargetDataLine(AudioFormat audio_format, AudioEventLineListener listener)
            throws Exception {
        DataLine.Info data_line_info = new DataLine.Info(TargetDataLine.class, audio_format);
        TargetDataLine target_data_line = null;
        target_data_line = (TargetDataLine) AudioSystem.getLine(data_line_info);
        if (listener != null) {
            target_data_line.addLineListener(listener);
        }
        target_data_line.open(audio_format);
        target_data_line.start();
        return target_data_line;
    }

    public static TargetDataLine getTargetDataLine(AudioFormat audio_format, Mixer mixer, AudioEventLineListener listener)
            throws Exception {
        DataLine.Info data_line_info = new DataLine.Info(TargetDataLine.class, audio_format);
        TargetDataLine target_data_line = null;
        target_data_line = (TargetDataLine) mixer.getLine(data_line_info);
        if (listener != null) {
            target_data_line.addLineListener(listener);
        }
        target_data_line.open(audio_format);
        target_data_line.start();
        return target_data_line;
    }

    public static SourceDataLine getSourceDataLine(AudioFormat audio_format, AudioEventLineListener listener) {
        SourceDataLine source_data_line = null;
        DataLine.Info data_line_info = new DataLine.Info(SourceDataLine.class, audio_format);
        try {
            source_data_line = (SourceDataLine) AudioSystem.getLine(data_line_info);
            if (listener != null) {
                source_data_line.addLineListener(listener);
            }
            source_data_line.open(audio_format);
        } catch (LineUnavailableException e) {
            System.out.println(e);
            System.exit(0);
        }
        source_data_line.start();
        return source_data_line;
    }

    public static AudioInputStream getInputStream(ByteArrayOutputStream byte_stream, AudioFormat audio_format) {
        byte audio_bytes[] = byte_stream.toByteArray();
        java.io.InputStream input_byte_stream = new ByteArrayInputStream(audio_bytes);
        long number_of_sample_frames = audio_bytes.length / audio_format.getFrameSize();
        AudioInputStream audio_input_stream = new AudioInputStream(input_byte_stream, audio_format, number_of_sample_frames);
        return audio_input_stream;
    }

    public static AudioInputStream getInputStream(byte audio_bytes[], AudioFormat audio_format) {
        java.io.InputStream input_byte_stream = new ByteArrayInputStream(audio_bytes);
        long number_of_sample_frames = audio_bytes.length / audio_format.getFrameSize();
        AudioInputStream audio_input_stream = new AudioInputStream(input_byte_stream, audio_format, number_of_sample_frames);
        return audio_input_stream;
    }

    public static AudioInputStream getInputStream(File audio_file)
            throws Exception {
        AudioInputStream audio_input_stream = null;
        try {
            audio_input_stream = AudioSystem.getAudioInputStream(audio_file);
        } catch (UnsupportedAudioFileException ex) {
            throw new Exception((new StringBuilder()).append("File ").append(audio_file.getName()).append(" has an unsupported audio format.").toString());
        } catch (IOException ex) {
            throw new Exception((new StringBuilder()).append("File ").append(audio_file.getName()).append(" is not readable.").toString());
        }
        return audio_input_stream;
    }

    public static AudioFormat getConvertedAudioFormat(AudioFormat original_format) {
        int bit_depth = original_format.getSampleSizeInBits();
        if (bit_depth != 8 && bit_depth != 16) {
            bit_depth = 16;
        }
        return new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, original_format.getSampleRate(), bit_depth, original_format.getChannels(), original_format.getChannels() * (bit_depth / 8), original_format.getSampleRate(), true);
    }

    public static AudioInputStream getConvertedAudioStream(AudioInputStream audio_input_stream) {
        audio_input_stream = convertUnsupportedFormat(audio_input_stream);
        AudioFormat original_audio_format = audio_input_stream.getFormat();
        AudioFormat new_audio_format = getConvertedAudioFormat(original_audio_format);
        if (!new_audio_format.matches(original_audio_format)) {
            audio_input_stream = AudioSystem.getAudioInputStream(new_audio_format, audio_input_stream);
        }
        return audio_input_stream;
    }

    public static AudioInputStream convertUnsupportedFormat(AudioInputStream audio_input_stream) {
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audio_input_stream.getFormat());
        if (!AudioSystem.isLineSupported(info)) {
            AudioFormat original_format = audio_input_stream.getFormat();
            int bit_depth = 16;
            AudioFormat new_audio_format = new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, original_format.getSampleRate(), bit_depth, original_format.getChannels(), original_format.getChannels() * (bit_depth / 8), original_format.getSampleRate(), true);
            audio_input_stream = AudioSystem.getAudioInputStream(new_audio_format, audio_input_stream);
        }
        return audio_input_stream;
    }

    public static float[][] extractSampleValues(AudioInputStream audio_input_stream)
            throws Exception {
        byte audio_bytes[] = getBytesFromAudioInputStream(audio_input_stream);
        int number_bytes = audio_bytes.length;
        AudioFormat this_audio_format = audio_input_stream.getFormat();
        int number_of_channels = this_audio_format.getChannels();
        int bit_depth = this_audio_format.getSampleSizeInBits();
        if (bit_depth != 16 && bit_depth != 8 || !this_audio_format.isBigEndian() || this_audio_format.getEncoding() != javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED) {
            throw new Exception("Only 8 or 16 bit signed PCM samples with a big-endian\nbyte order can be analyzed currently.");
        }
        int number_of_bytes = audio_bytes.length;
        int bytes_per_sample = bit_depth / 8;
        int number_samples = number_of_bytes / bytes_per_sample / number_of_channels;
        if ((number_samples == 2 || bytes_per_sample == 2) && number_of_bytes % 2 != 0 || number_samples == 2 && bytes_per_sample == 2 && number_of_bytes % 4 != 0) {
            throw new Exception("Uneven number of bytes for given bit depth and number of channels.");
        }
        float max_sample_value = (float) ((double) findMaximumSampleValue(bit_depth) + 2D);
        float sample_values[][] = new float[number_of_channels][number_samples];
        ByteBuffer byte_buffer = ByteBuffer.wrap(audio_bytes);
        if (bit_depth == 8) {
            for (int samp = 0; samp < number_samples; samp++) {
                for (int chan = 0; chan < number_of_channels; chan++) {
                    sample_values[chan][samp] = (float) byte_buffer.get() / max_sample_value;
                }

            }

        } else {
            if (bit_depth == 16) {
                ShortBuffer short_buffer = byte_buffer.asShortBuffer();
                for (int samp = 0; samp < number_samples; samp++) {
                    for (int chan = 0; chan < number_of_channels; chan++) {
                        sample_values[chan][samp] = (float) short_buffer.get() / max_sample_value;
                    }

                }

            }
        }
        return sample_values;
    }

    public static byte[] getBytesFromAudioInputStream(AudioInputStream audio_input_stream)
            throws Exception {
        float buffer_duration_in_seconds = 0.25F;
        int buffer_size = getNumberBytesNeeded(buffer_duration_in_seconds, audio_input_stream.getFormat());
        byte rw_buffer[] = new byte[buffer_size + 2];
        ByteArrayOutputStream byte_array_output_stream = new ByteArrayOutputStream();
        for (int position = audio_input_stream.read(rw_buffer, 0, rw_buffer.length); position > 0; position = audio_input_stream.read(rw_buffer, 0, rw_buffer.length)) {
            byte_array_output_stream.write(rw_buffer, 0, position);
        }

        byte results[] = byte_array_output_stream.toByteArray();
        try {
            byte_array_output_stream.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        return results;
    }

    public static int getNumberBytesNeeded(float duration_in_seconds, AudioFormat audio_format) {
        int frame_size_in_bytes = audio_format.getFrameSize();
        float frame_rate = audio_format.getFrameRate();
        return (int) ((float) frame_size_in_bytes * frame_rate * duration_in_seconds);
    }

    public static int getNumberBytesNeeded(int number_samples, AudioFormat audio_format) {
        int number_bytes_per_sample = audio_format.getSampleSizeInBits() / 8;
        int number_channels = audio_format.getChannels();
        return number_samples * number_bytes_per_sample * number_channels;
    }

    public static AudioInputStream convertToAudioInputStream(float samples[][], AudioFormat audio_format)
            throws Exception {
        int number_bytes_needed = getNumberBytesNeeded(samples[0].length, audio_format);
        byte audio_bytes[] = new byte[number_bytes_needed];
        writeSamplesToBuffer(samples, audio_format.getSampleSizeInBits(), audio_bytes);
        return getInputStream(audio_bytes, audio_format);
    }

    public static void writeSamplesToBuffer(float sample_values[][], int bit_depth, byte buffer[])
            throws Exception {
        if (sample_values == null) {
            throw new Exception("Empty set of samples to write provided.");
        }
        if (bit_depth != 8 && bit_depth != 16) {
            throw new Exception((new StringBuilder()).append("Bit depth of ").append(bit_depth).append(" specified.Only bit depths of 8 or 16 currently accepted.").toString());
        }
        if (buffer == null) {
            throw new Exception("Null buffer for storing samples provided.");
        }
        sample_values = clipSamples(sample_values);
        float max_sample_value = findMaximumSampleValue(bit_depth);
        ByteBuffer byte_buffer = ByteBuffer.wrap(buffer);
        if (bit_depth == 8) {
            for (int samp = 0; samp < sample_values[0].length; samp++) {
                for (int chan = 0; chan < sample_values.length; chan++) {
                    float sample_value = sample_values[chan][samp] * max_sample_value;
                    byte_buffer.put((byte) (int) sample_value);
                }

            }

        } else {
            if (bit_depth == 16) {
                ShortBuffer short_buffer = byte_buffer.asShortBuffer();
                for (int samp = 0; samp < sample_values[0].length; samp++) {
                    for (int chan = 0; chan < sample_values.length; chan++) {
                        float sample_value = sample_values[chan][samp] * max_sample_value;
                        short_buffer.put((short) (int) sample_value);
                    }

                }

            }
        }
    }

    public static float[][] clipSamples(float original_samples[][])
            throws Exception {
        if (original_samples == null) {
            throw new Exception("Empty set of samples to provided.");
        }
        float clipped_samples[][] = new float[original_samples.length][];
        for (int chan = 0; chan < clipped_samples.length; chan++) {
            clipped_samples[chan] = new float[original_samples[chan].length];
            for (int samp = 0; samp < clipped_samples[chan].length; samp++) {
                if ((double) original_samples[chan][samp] < -1D) {
                    clipped_samples[chan][samp] = -1F;
                    continue;
                }
                if ((double) original_samples[chan][samp] > 1.0D) {
                    clipped_samples[chan][samp] = 1.0F;
                } else {
                    clipped_samples[chan][samp] = original_samples[chan][samp];
                }
            }

        }

        return clipped_samples;
    }

    public static float findMaximumSampleValue(int bit_depth) {
        int max_sample_value_int = 1;
        for (int i = 0; i < bit_depth - 1; i++) {
            max_sample_value_int *= 2;
        }

        float max_sample_value = (float) ((double) (float) (--max_sample_value_int) - 1.0D);
        return max_sample_value;
    }

    public static void saveByteArrayOutputStream(ByteArrayOutputStream audio, AudioFormat audio_format, File save_file, javax.sound.sampled.AudioFileFormat.Type file_type)
            throws Exception {
        if (audio == null) {
            throw new Exception("No audio data provided to save.");
        }
        if (audio_format == null) {
            throw new Exception("No audio format provided for saving.");
        }
        if (save_file == null) {
            throw new Exception("No file provided for saving.");
        }
        if (file_type == null) {
            throw new Exception("No audio file format provided for saving.");
        } else {
            int number_bytes = audio.size();
            int bytes_per_frame = audio_format.getFrameSize();
            long number_frames = number_bytes / bytes_per_frame;
            ByteArrayInputStream bais = new ByteArrayInputStream(audio.toByteArray());
            AudioInputStream audio_input_stream = new AudioInputStream(bais, audio_format, number_frames);
            saveToFile(audio_input_stream, save_file, file_type);
            return;
        }
    }

    public static void saveToFile(AudioInputStream audio_input_stream, File file_to_save_to, javax.sound.sampled.AudioFileFormat.Type file_type)
            throws Exception {
        if (audio_input_stream == null) {
            throw new Exception("No audio provided to save.");
        }
        if (file_to_save_to == null) {
            throw new Exception("No file provided to save to.");
        }
        if (file_type == null) {
            throw new Exception("No file type to save to specified.");
        } else {
            AudioSystem.write(audio_input_stream, file_type, file_to_save_to);
            return;
        }
    }
}
