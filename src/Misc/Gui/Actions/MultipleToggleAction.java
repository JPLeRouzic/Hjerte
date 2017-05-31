
package Misc.Gui.Actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTable;

public class MultipleToggleAction extends AbstractAction
{

    public MultipleToggleAction(JTable f)
    {
        features = f;
    }

    public void actionPerformed(ActionEvent e)
    {
        int selectedRows[] = features.getSelectedRows();
        if(selectedRows.length > 0)
        {
            boolean valueToBeSet = ((Boolean)features.getValueAt(selectedRows[0], 0)).booleanValue();
            for(int i = 0; i < selectedRows.length; i++)
                features.setValueAt(new Boolean(!valueToBeSet), selectedRows[i], 0);

        }
    }

    static final long serialVersionUID = 1L;
    private JTable features;
}
