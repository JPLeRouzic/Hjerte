package Misc.Gui.Main;

import Misc.AudioFeatures.RecordingInfo;
import ML.featureDetection.NormalizeBeat;
import Misc.Gui.Controller.Control;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class GenObs {

    GenObs() {
    }

    ArrayList generateObsFromFiles(Control controller) {
        RecordingInfo recordings[] = controller.exfeat.recordingInfo;
        if (recordings == null) {
            String message = "No recordings available to extract features from.";
            JOptionPane.showConfirmDialog(null, message, "WARNING", 0);
        }

        ArrayList obs = null;
        try {
            obs = controller.exfeat.extractAllFiles(
                    controller.exfeat.recordingInfo,
                    controller.exfeat);
        } catch (Throwable ex) {
            Logger.getLogger(GenObs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }
}
