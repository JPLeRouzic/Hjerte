package Misc.Gui.Main;

import Misc.AudioFeatures.RecordingInfo;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import Misc.Gui.Controller.Control;

class GenObs
{
    ArrayList generateObsFromFiles(final Control controller) {
        final RecordingInfo[] recordings = controller.exfeat.recordingInfo;
        if (recordings == null) {
            final String message = "No recordings available to extract features from.";
            JOptionPane.showConfirmDialog(null, message, "WARNING", 0);
        }
        ArrayList obs = null;
        try {
            obs = controller.exfeat.extractAllFiles(controller.exfeat.recordingInfo, controller.exfeat);
        }
        catch (Throwable ex) {
            Logger.getLogger(GenObs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }
}
