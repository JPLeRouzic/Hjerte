/*
 *  // This fills in the most important item in this class
    // It gives the probability of transition from one state to the next
    // for each next step
    // "Current state" => {
    //         ("next state 1", probability = 0.1),
    //         ("next state 2", probability = 0.6),
    //         ("next state 3", probability = 0.4)
 */
package Misc.Gui.HMM;

import ML.Train.HMM;
import Misc.Gui.Main.EntryPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jplr
 */
public class LoadHMM {

    static void loadHMM(FileReader fr) {
        try {
            String key, value;
//            List<String> keys = new ArrayList<String>();
//            List<String> values = new ArrayList<String>();
            List keys = new ArrayList();
            List values = new ArrayList();
            BufferedReader bf = new BufferedReader(fr);

            EntryPoint.hmmTrain = new HMM(new ArrayList()) ;
            EntryPoint.hmmTrain.transitionsProbs = new HashMap();
            while ((key = bf.readLine()) != null) {
                value = bf.readLine();
                EntryPoint.hmmTrain.transitionsProbs.put(key, value);
            }

            // close the BufferedReader when we're done
            bf.close();

            /**
             * **********************
             * for (int yuu = 0; yuu < iterMax; yuu++) { String key =
             * valuesKeys[yuu].toString(); String value =
             * valuesArray[yuu].toString(); fw.write((new
             * StringBuilder()).append(key).append(sep).toString());
             * fw.write((new StringBuilder()).append(" " +
             * value).append(sep).toString()); }
             *
             */
        } catch (IOException ex) {
            Logger.getLogger(LoadHMM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
