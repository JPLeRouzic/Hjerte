
package Misc.Gui.Actions;

import Misc.AudioFeatures.RecordingInfo;
import Misc.Gui.Controller.Control;
import Misc.Tools.GeneralMethods;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTable;

public class RemoveRecordingAction 
{

    static final long serialVersionUID = 1L;
    Control controller;
    JTable recordingTable;

    public RemoveRecordingAction(JTable recordTable)
    {
        recordingTable = recordTable ;
    }

	/**
	 * Sets references to both the controller and the recordings table.
	 *
	 * @param c
	 *            near global controller.
	 * @param recordingTable
	 *            table holding references to audio files.
	 */
    public void setModel(Control c, JTable recordingTable)
    {
        controller = c;
        this.recordingTable = recordingTable;
    }

    public void removeFiles()
    {
        int selected_rows = recordingTable.getRowCount();
        for(int i = 0; i < selected_rows; i++) {
            controller.exfeat.recordingInfo[i] = null;
        }

        controller.exfeat.recordingInfo = null;
        
        controller.filesList.fillTable(controller.exfeat.recordingInfo);
        controller.filesList.fireTableDataChanged();
    }
}
