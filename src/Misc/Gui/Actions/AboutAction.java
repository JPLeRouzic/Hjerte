package Misc.Gui.Actions;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class AboutAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    
    public AboutAction() {
        super("About...");
    }
    
    public void actionPerformed(final ActionEvent e) {
        final String data = "Based on jAudio, modified by Jean-Pierre Le Rouzic";
        JOptionPane.showMessageDialog(null, data, "Early heart failure detector", 1);
    }
}
