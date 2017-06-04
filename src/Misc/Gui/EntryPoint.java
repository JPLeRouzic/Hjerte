
package Misc.Gui;

import ML.Train.HMM;
import Misc.Gui.Controller.Control;

// Referenced classes of package Misc.Gui:
//            OuterFrame

public class EntryPoint
{
    public static Control controller;
    public static OuterFrame outer_frame;
    public static HMM hmmTrain;
    public static HMM hmmTest;

    public EntryPoint()
    {
    }

    public static void main(String args[])
        throws Exception
    {
        controller = new Control();
        outer_frame = new OuterFrame(controller);
        outer_frame.repaint();
        outer_frame.featureSelectorPanel();
    }

}
