
package Misc.AudioFeatures;

import Misc.sampled.AudioSamples;
import java.io.File;
import javax.sound.sampled.*;

public class FromFileToAudio
{
    private AudioSamples audio_data  ;

    public FromFileToAudio(File recording_file)
        throws Exception
    {
        AudioInputStream original_stream = AudioSystem.getAudioInputStream(recording_file);
        AudioFormat original_format = original_stream.getFormat();
        int bit_depth = original_format.getSampleSizeInBits();
        if(bit_depth != 8 && bit_depth != 16) {
            bit_depth = 16;
        }
        AudioInputStream second_stream = original_stream;
        if(original_format.getEncoding() != javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED || !original_format.isBigEndian())
        {
            AudioFormat new_format = new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, original_format.getSampleRate(), bit_depth, original_format.getChannels(), original_format.getChannels() * (bit_depth / 8), original_format.getSampleRate(), true);
            second_stream = AudioSystem.getAudioInputStream(new_format, original_stream);
        }
        AudioInputStream new_stream = second_stream;
        if(bit_depth != original_format.getSampleSizeInBits())
        {
            AudioFormat new_format = new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED, original_format.getSampleRate(), bit_depth, original_format.getChannels(), original_format.getChannels() * (bit_depth / 8), original_format.getSampleRate(), true);
            new_stream = AudioSystem.getAudioInputStream(new_format, second_stream);
        }
        audio_data = new AudioSamples(new_stream, recording_file.getPath(), false);
    }
    
    public AudioSamples getAudioSamples() {
        return audio_data ;
    }
}
