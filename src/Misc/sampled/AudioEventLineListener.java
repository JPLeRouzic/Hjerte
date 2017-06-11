
package Misc.sampled;

import java.io.PrintStream;
import javax.sound.sampled.*;

public class AudioEventLineListener
    implements LineListener
{

    public AudioEventLineListener()
    {
    }

    public void update(LineEvent event)
    {
        Line line_firing_event = event.getLine();
        String event_type = event.getType().toString();
        String line_info = line_firing_event.getLineInfo().toString();
        String event_position = Long.toString(event.getFramePosition());
        String event_overview = event.toString();
        String line_type = line_info.substring(10);
        int space_location = line_type.indexOf(" ");
        if(space_location != -1)
            line_type = line_type.substring(0, space_location);
        String line_instance = "";
        int dollar_location = event_overview.indexOf("$");
        if(dollar_location == -1)
        {
            dollar_location = event_overview.indexOf("@");
            if(dollar_location != -1)
                line_instance = event_overview.substring(dollar_location);
        } else
        {
            line_instance = event_overview.substring(dollar_location);
        }
        System.out.print("---------------------------------------------\n");
        System.out.print("LINE EVENT REPORT:\n");
        System.out.print("---------------------------------------------\n");
        System.out.print((new StringBuilder()).append("EVENT TYPE: ").append(event_type).append("\n").toString());
        System.out.print((new StringBuilder()).append("LINE TYPE: ").append(line_type).append("\n").toString());
        System.out.print((new StringBuilder()).append("LINE INSTANCE: ").append(line_instance).append("\n").toString());
        System.out.print((new StringBuilder()).append("EVENT POSITION (in sample frames): ").append(event_position).append("\n").toString());
        System.out.print("---------------------------------------------\n\n");
    }
}
