package Misc.Gui.Actions;

import Audio.AudioMethods;
import java.io.File;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import Misc.Gui.Controller.Control;
import javax.swing.JTable;
import javax.swing.AbstractAction;

public class ViewFileInfoAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    private JTable recordings_table;
    private final Control controller;
    
    public ViewFileInfoAction(final Control c, final JTable jt) {
        super("View File Info...");
        this.controller = c;
        this.recordings_table = jt;
    }
    
    public void setTable(final JTable jt) {
        this.recordings_table = jt;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.recordings_table == null) {
            final String message = "There is no recorded file!";
            JOptionPane.showMessageDialog(null, message, "ERROR", 0);
        }
        final int[] selected_rows = this.recordings_table.getSelectedRows();
        for (int i = 0; i < selected_rows.length; ++i) {
            try {
                final File file = new File(this.controller.exfeat.recordingInfo[selected_rows[i]].file_path);
                final String data = AudioMethods.getAudioFileFormatData(file);
                JOptionPane.showMessageDialog(null, data, "FILE INFORMATION", 1);
            }
            catch (Exception e2) {
                final String message2 = "Could not display file information for file " + this.controller.exfeat.recordingInfo[selected_rows[i]].file_path + "\n" + e2.getMessage();
                JOptionPane.showMessageDialog(null, message2, "ERROR", 0);
            }
        }
    }
}
