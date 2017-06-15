package Misc.Gui.Actions;

import javax.swing.JTable;
import Misc.Gui.Controller.Control;

public class RemoveRecordingAction
{
    static final long serialVersionUID = 1L;
    Control controller;
    JTable recordingTable;
    
    public RemoveRecordingAction(final JTable recordTable) {
        this.recordingTable = recordTable;
    }
    
    public void setModel(final Control c, final JTable recordingTable) {
        this.controller = c;
        this.recordingTable = recordingTable;
    }
    
    public void removeFiles() {
        for (int selected_rows = this.recordingTable.getRowCount(), i = 0; i < selected_rows; ++i) {
            this.controller.exfeat.recordingInfo[i] = null;
        }
        this.controller.exfeat.recordingInfo = null;
        this.controller.filesList.fillTable(this.controller.exfeat.recordingInfo);
        this.controller.filesList.fireTableDataChanged();
    }
}
