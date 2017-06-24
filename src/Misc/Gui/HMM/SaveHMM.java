/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc.Gui.HMM;

import Misc.Gui.Main.EntryPoint;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jplr
 */
public class SaveHMM {

    public static void saveHMM(String hmmName, FileWriter fw) {
        HMMmanager mng = EntryPoint.controller.hmmManager;
        // hmmName field is empty
        if (hmmName.trim().length() == 0) {
            mng.getTextArea().setText("HMM Field must be not empty.");
            return;
        }
        HMMrecord list = new HMMrecord("author", hmmName, "portFolio", "length");
        mng.getTextArea().setText(list.toString());
        //  Save this HMM
        try {
            String sep = System.getProperty("line.separator");
            Collection values = EntryPoint.hmmTrain.transitionsProbs.values();
            Set keys = EntryPoint.hmmTrain.transitionsProbs.keySet();
            Object[] valuesArray = values.toArray();
            Object[] valuesKeys = keys.toArray();
            for (int yuu = 0; yuu < values.size(); yuu++) {
                String key = valuesKeys[yuu].toString();
                String value = valuesArray[yuu].toString();
                fw.write((new StringBuilder()).append(key).append(sep).toString());
                fw.write((new StringBuilder()).append(" " + value).append(sep).toString());
            }
            //
        } catch (IOException ex) {
            Logger.getLogger(HMMmanager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(HMMmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
