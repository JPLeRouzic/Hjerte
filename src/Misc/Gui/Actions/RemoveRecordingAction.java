
package Misc.Gui.Actions;

import Misc.AudioFeatures.RecordingInfo;
import Misc.Gui.Controller.Control;
import Misc.Tools.GeneralMethods;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTable;

public class RemoveRecordingAction extends AbstractAction
{

    static final long serialVersionUID = 1L;
    Control controller;
    JTable recordingTable;

    public RemoveRecordingAction(JTable recordTable)
    {
        super("Delete Recording");
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

    public void actionPerformed(ActionEvent e)
    {
        int selected_rows[] = recordingTable.getSelectedRows();
        for(int i = 0; i < selected_rows.length; i++) {
            controller.exfeat.recordingInfo[selected_rows[i]] = null;
        }

        Object results[] = GeneralMethods.removeNullEntriesFromArray(controller.exfeat.recordingInfo);
        if(results != null)
        {
            controller.exfeat.recordingInfo = new RecordingInfo[results.length];
            for(int i = 0; i < results.length; i++) {
                controller.exfeat.recordingInfo[i] = (RecordingInfo)results[i];
            }

        } else
        {
            controller.exfeat.recordingInfo = null;
        }
        controller.filesList.fillTable(controller.exfeat.recordingInfo);
        controller.filesList.fireTableDataChanged();
    }
}
