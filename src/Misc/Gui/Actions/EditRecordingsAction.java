package Misc.Gui.Actions;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import Misc.Gui.Main.OuterFrame;
import javax.swing.JTable;
import Misc.Gui.Controller.Control;
import javax.swing.AbstractAction;

public class EditRecordingsAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    private Control controller;
    private JTable recordings_table;
    private OuterFrame of_;
    
    public EditRecordingsAction(final Control c) {
        super("Edit Recordings...");
        this.controller = c;
    }
    
    public void setTable(final JTable jt, final OuterFrame of) {
        this.recordings_table = jt;
        this.of_ = of;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        final int selected_row = this.recordings_table.getSelectedRow();
        if (selected_row < 0) {
            JOptionPane.showMessageDialog(null, "No recording selected for editing.", "ERROR", 0);
        }
    }
}
