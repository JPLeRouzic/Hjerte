
package Misc.Gui.Main;

import ML.Train.HMM;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// Referenced classes of package Misc.Gui:
//            EntryPoint

class ExtrFeatrsTrain
{

    ExtrFeatrsTrain()
    {
    }

    static void trainOnFeatures(ArrayList obs)
    {
        try
        {
            EntryPoint.hmmTrain = new HMM(obs);
            HMM hp = EntryPoint.hmmTrain;
            hp.train(); // marche pas sur ensemble des fichiers! des Observations, mais pas de stats!
        }
        catch(Throwable t)
        {
            if(t.toString().equals("java.lang.OutOfMemoryError"))
                JOptionPane.showMessageDialog(null, "The Java Runtime ran out of memory. Please rerun this program\nwith a higher amount of memory assigned to the Java Runtime heap.", "ERROR", 0);
            else
            if(t instanceof Exception)
            {
                Exception e = (Exception)t;
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", 0);
            }
        }
    }
}
