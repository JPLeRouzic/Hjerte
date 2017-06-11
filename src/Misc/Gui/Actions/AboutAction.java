
package Misc.Gui.Actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class AboutAction extends AbstractAction
{

    public AboutAction()
    {
        super("About...");
    }

    public void actionPerformed(ActionEvent e)
    {
        String data = "Based on jAudio, modified by Jean-Pierre Le Rouzic";
        JOptionPane.showMessageDialog(null, data, "Early heart failure detector", 1);
    }

    static final long serialVersionUID = 1L;
}
