
package Misc.Gui.Actions;

import Misc.Gui.Controller.Control;
import Misc.Gui.OuterFrame;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class EditRecordingsAction extends AbstractAction
{

    public EditRecordingsAction(Control c)
    {
        super("Edit Recordings...");
        controller = c;
    }

    public void setTable(JTable jt, OuterFrame of)
    {
        recordings_table = jt;
        of_ = of;
    }

    public void actionPerformed(ActionEvent e)
    {
        int selected_row = recordings_table.getSelectedRow();
        if(selected_row < 0)
            JOptionPane.showMessageDialog(null, "No recording selected for editing.", "ERROR", 0);
    }

    static final long serialVersionUID = 1L;
    private Control controller;
    private JTable recordings_table;
    private OuterFrame of_;
}
