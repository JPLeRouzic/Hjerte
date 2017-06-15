package Misc.Gui.Main;

import java.awt.Component;
import javax.swing.JOptionPane;
import ML.Train.HMM;
import java.util.ArrayList;

class ExtrFeatrsTrain
{
    static void trainOnFeatures(final ArrayList obs) {
        try {
            EntryPoint.hmmTrain = new HMM(obs);
            final HMM hp = EntryPoint.hmmTrain;
            hp.train();
        }
        catch (Throwable t) {
            if (t.toString().equals("java.lang.OutOfMemoryError")) {
                JOptionPane.showMessageDialog(null, "The Java Runtime ran out of memory. Please rerun this program\nwith a higher amount of memory assigned to the Java Runtime heap.", "ERROR", 0);
            }
            else if (t instanceof Exception) {
                final Exception e = (Exception)t;
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", 0);
            }
        }
    }
}
