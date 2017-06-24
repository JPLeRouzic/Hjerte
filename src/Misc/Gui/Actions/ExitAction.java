package Misc.Gui.Actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class ExitAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    
    public ExitAction() {
        super("Exit");
    }
    
    public void actionPerformed(final ActionEvent e) {
        System.exit(0);
    }
}
