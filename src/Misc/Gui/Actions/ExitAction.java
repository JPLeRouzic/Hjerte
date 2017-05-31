
package Misc.Gui.Actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class ExitAction extends AbstractAction
{

    public ExitAction()
    {
        super("Exit");
    }

    public void actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }

    static final long serialVersionUID = 1L;
}
