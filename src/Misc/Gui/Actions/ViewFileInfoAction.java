
package Misc.Gui.Actions;

import Audio.AudioMethods;
import Misc.Gui.Controller.Control;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;

public class ViewFileInfoAction extends AbstractAction
{

    public ViewFileInfoAction(Control c, JTable jt)
    {
        super("View File Info...");
        controller = c;
        recordings_table = jt;
    }

    public void setTable(JTable jt)
    {
        recordings_table = jt;
    }

    /**
     * Show the file info of the selected file.
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e)
    {
        if(recordings_table == null)
        {
            String message = "There is no recorded file!";
            JOptionPane.showMessageDialog(null, message, "ERROR", 0);
        }
        int selected_rows[] = recordings_table.getSelectedRows();
        for(int i = 0; i < selected_rows.length; i++)
        {
            String message;
            try
            {
                File file = new File(controller.exfeat.recordingInfo[selected_rows[i]].file_path);
                String data = AudioMethods.getAudioFileFormatData(file);
                JOptionPane.showMessageDialog(null, data, "FILE INFORMATION", 1);
                continue;
            }
            catch(Exception e1)
            {
                message = (new StringBuilder()).append("Could not display file information for file ").append(controller.exfeat.recordingInfo[selected_rows[i]].file_path).append("\n").append(e1.getMessage()).toString();
            }
            JOptionPane.showMessageDialog(null, message, "ERROR", 0);
        }

    }

    static final long serialVersionUID = 1L;
    private JTable recordings_table;
    private final Control controller;
}
