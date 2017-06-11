
package Misc.AudioFeatures;

import Misc.Tools.StringMethods;
import Misc.sampled.AudioSamples;
import java.io.Serializable;

public class RecordingInfo
    implements Serializable
{

    public String identifier;
    public String file_path;
    public transient AudioSamples samples;

    public RecordingInfo(String identifier, String file_path, AudioSamples samples)
    {
        this.identifier = identifier;
        this.file_path = file_path;
        this.samples = samples;
    }

    public RecordingInfo(String file_path)
    {
        identifier = StringMethods.convertFilePathToFileName(file_path);
        this.file_path = file_path;
        samples = null;
    }
}
