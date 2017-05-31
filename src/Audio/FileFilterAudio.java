
package Audio;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilterAudio extends FileFilter
{

    public FileFilterAudio()
    {
    }

    public boolean accept(File f)
    {
        boolean acceptable_extension = false;
        String lowercase_file_name = f.getName().toLowerCase();
        if(f.isDirectory() || lowercase_file_name.endsWith(".wav") || lowercase_file_name.endsWith(".wave") || lowercase_file_name.endsWith(".aif") || lowercase_file_name.endsWith(".aiff") || lowercase_file_name.endsWith(".aifc") || lowercase_file_name.endsWith(".au") || lowercase_file_name.endsWith(".snd") || lowercase_file_name.endsWith(".ogg") || lowercase_file_name.endsWith(".oga") || lowercase_file_name.endsWith(".mp3"))
            acceptable_extension = true;
        return acceptable_extension;
    }

    public String getDescription()
    {
        return "Audio (wav, wave, aif, aiff, aifc, au, snd, ogg, oga and mp3) files";
    }
}
