package Misc.Gui.Main;

import ML.Train.HMM;
import Misc.Gui.Controller.Control;

public class EntryPoint
{
    public static Control controller;
    public static OuterFrame outer_frame;
    public static HMM hmmTrain;
    public static HMM hmmTest;
    
    public static void main(final String[] args) throws Exception {
        EntryPoint.controller = new Control();
        (EntryPoint.outer_frame = new OuterFrame(EntryPoint.controller)).repaint();
        EntryPoint.outer_frame.featureSelectorPanel();
    }
}
