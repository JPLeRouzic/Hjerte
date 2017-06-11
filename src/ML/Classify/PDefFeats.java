/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ML.Classify;

/**
 *
 * @author jplr
 */
public class PDefFeats {


    public PDefFeats() {
        plop = speech = noise = respiration = variant = hum = saturation = false;
        nbBeats = new Integer(0) ;
        duration =  new Integer(0) ;
    }

    public boolean plop;              // A sudden, short duration noise
    public boolean speech;              // human speech
    public boolean noise;              // sound that are continuous but whose origin is difficult to identify
    public boolean respiration;              // human respiration
    public boolean variant;              // probably non standard heart sounds
    public boolean hum;              // some constant (amplitude/frequency) noise like 50hZ or high frequency
    public boolean saturation;              // sound deformation

    public Integer nbBeats;
    public Integer duration;
}
