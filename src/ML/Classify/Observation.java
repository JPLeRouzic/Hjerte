package ML.Classify;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * This class is a type for a HMM observation
 * In our case, this is a small FFT (1/20 of a heartbeat) that  
 * capture the identity of a heart beat sound like S1
 *
 * @author jplr
 */
public class Observation {
    ArrayList signature ;    // small FFT of the time between S1 and this sound
    float shiftrel ;         // Shift of this heart sound with respect to S1 as a fraction of the average beat
    float shiftAbs ;         // Shift of this heart sound with respect to S1 
    PDefFeats feats ;
    String namePrefix ;      //name of the heart sound (S1, S2, etc..)
    String nameSuffix ;      //suffix of the heart sound (4 in S1.4, 8 in S2.8, etc..)
    
    public Observation(String heartPref, float soundShiftrel, float soundShiftabs, ArrayList soundFFT) {
        namePrefix = heartPref;
        shiftrel = soundShiftrel ;
        shiftAbs = soundShiftabs ;
        signature = soundFFT ;
    }
    
    public ArrayList getSign() {
        return signature ;
    }
    
    public String getNamePref() {
        return namePrefix ;
    }
    
    public String getFullName() {
        return namePrefix + "." + nameSuffix ;
    }
    
    public void setNamePref(String s) {
        namePrefix = s;
    }

    public void addPreDef(PDefFeats predefFeatures) {
        feats = predefFeatures ;
    }

    public void setNameSufx(String suffix) {
       nameSuffix = suffix;
    }
}