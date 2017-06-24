package Misc.Gui.Actions;

import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.AbstractAction;

public class MultipleToggleAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    private JTable features;
    
    public MultipleToggleAction(final JTable f) {
        this.features = f;
    }
    
    public void actionPerformed(final ActionEvent e) {
        final int[] selectedRows = this.features.getSelectedRows();
        if (selectedRows.length > 0) {
            boolean valueToBeSet = ((Boolean)features.getValueAt(selectedRows[0], 0)).booleanValue();
            for (int i = 0; i < selectedRows.length; ++i) {
                this.features.setValueAt(new Boolean(!valueToBeSet), selectedRows[i], 0);
            }
        }
    }
}
