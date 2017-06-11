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
    float shiftrelToS1 ;         // Shift of this heart sound with respect to S1 as a fraction of the average beat
    float shiftAbsToS1 ;         // Shift of this heart sound with respect to S1 
    PDefFeats feats ;
    String namePrefix ;      //name of the heart sound (S1, S2, etc..)
    String nameSuffix ;      //suffix of the heart sound (4 in S1.4, 8 in S2.8, etc..)
    int rawIndex ;
    
    /**
     * It describes one HMM observation
     * 
     * @param heartPref         // name of the heart sound (S1, S2, etc..)
     * @param disp              // displacement of the event from the beginning of the sound file
     * @param soundShiftrel     // Shift of this heart sound with respect to S1 as a fraction of the average beat
     * @param soundShiftabs     // Shift of this heart sound with respect to S1 
     * @param soundFFT          // A convenient digital signature of the heart beat (not a FFT or Hash)
     */
    public Observation(String heartPref, int disp, float soundShiftrel, float soundShiftabs, ArrayList soundFFT) {
        namePrefix = heartPref;
        rawIndex = disp ; 
        shiftrelToS1 = soundShiftrel ;
        shiftAbsToS1 = soundShiftabs ;
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

    /**
     * The displacement since the beginning of the sound file in number of samples
     * @return 
     */
    public int getRawIndex() {
        return rawIndex ;
    }
}