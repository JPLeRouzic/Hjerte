/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ML.Train;

import ML.Classify.Observation;
import ML.featureDetection.FindBeats;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jplr
 */
public class Segmentation {
    
    public ArrayList segmentedBeats = new ArrayList();
    private final FindBeats cb;
    
    public Segmentation(FindBeats cibi) {
        cb = cibi;
    }

    /**
     * we have ProbableBeats which contains the probable S1 events and moreBeats
     * which contains the probable S1, S2, S3, S4 We will use ProbableBeats to
     * find the probable locations of other Sx events in moreBeats So we use
     * moreBeats to detect events at proximity of the found time for S1 events.
     * Once a S1 S1 is detected, the next S1 before the after this S1 and before
     * the next is supposed to be S2, and so on for S3 and S4.
     * @param norm
     * @param rate
     * @return 
     */
    public ArrayList segmentation(FindBeats norm, int rate) {
        int S1 = 0;
        int S2 = 0;
        int S3 = 0;
        int S4 = 0;
        Iterator moreBeatsPTR = null;
        Iterator itrPB = norm.getProbableBeats().iterator();
        label0:
        do {
            if (!itrPB.hasNext()) {
                break;
            }
            Integer S1base = ((Integer) itrPB.next());
            Integer S1next;
            if (itrPB.hasNext()) {
                S1next = ((Integer) itrPB.next());
            } else {
                return segmentedBeats;
            }
            float low = (float) (S1base.floatValue() * 0.9);
            float high = (float) (S1base.floatValue() * 1.1);
            if (moreBeatsPTR == null) {
                moreBeatsPTR = norm.getMoreBeats().iterator();
            }
            label1:
            do {
                do {
                    if (!moreBeatsPTR.hasNext()) {
                        continue label0;
                    }
                    S1 = ((Integer) moreBeatsPTR.next()).intValue();
                    if ((float) S1 <= low || (float) S1 >= high) {
                        continue label1;
                    }
                } while ((S1 - S1base.intValue()) + 1 < 0);
                Observation eventHMM = makeHMMObs("S1", S1, S1base.intValue(), S1next.intValue(), norm, rate);
                if (eventHMM == null) {
                    moreBeatsPTR.remove();
                    continue label0;
                }
                addEvents(eventHMM);
                if (moreBeatsPTR.hasNext()) {
                    S2 = ((Integer) moreBeatsPTR.next()).intValue();
                    if (S2 < S1next.intValue()) {
                        eventHMM = makeHMMObs("S2", S2, S1base.intValue(), S1next.intValue(), norm, rate);
                        if (eventHMM == null) {
                            moreBeatsPTR.remove();
                            continue label0;
                        }
                        addEvents(eventHMM);
                    } else {
                        moreBeatsPTR.remove();
                        continue label0;
                    }
                }
                if (moreBeatsPTR.hasNext()) {
                    S3 = ((Integer) moreBeatsPTR.next()).intValue();
                    if (S3 < S1next.intValue()) {
                        eventHMM = makeHMMObs("S3", S3, S1base.intValue(), S1next.intValue(), norm, rate);
                        if (eventHMM == null) {
                            moreBeatsPTR.remove();
                            continue label0;
                        }
                        addEvents(eventHMM);
                    } else {
                        moreBeatsPTR.remove();
                        continue label0;
                    }
                }
                if (!moreBeatsPTR.hasNext()) {
                    continue;
                }
                S4 = ((Integer) moreBeatsPTR.next()).intValue();
                if (S4 < S1next.intValue()) {
                    eventHMM = makeHMMObs("S4", S4, S1base.intValue(), S1next.intValue(), norm, rate);
                    if (eventHMM == null) {
                        moreBeatsPTR.remove();
                    } else {
                        addEvents(eventHMM);
                        continue;
                    }
                } else {
                    moreBeatsPTR.remove();
                }
                continue label0;
            } while (S1 <= S1base.intValue());
            moreBeatsPTR.remove();
        } while (true);

        return segmentedBeats;
    }
    
    public void addEvents(Observation eventHMM) {
        segmentedBeats.add(eventHMM);
    }
    
    private Observation makeHMMObs(String pref, int Sx, int S1base, int S1next, FindBeats norm, int rate) {
        // make a string for the relative position of the event in the beat
        // Obtain a FFT of the time between S1base and Sx and make a string of it
        // First get the sample between S1base and Sx
        float[] data = norm.getNormalizedData();
        float[] sample = new float[(Sx - S1base) + 1];
        if (sample.length < 3) {
            // not enough values in sample
            return null;
        }

        // arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
        System.arraycopy(data, S1base, sample, 0, Sx - S1base);
        
        float offsetAbs = (Sx - S1base);
        float offsetRel = (float) (Sx - S1base) / (float) (S1next - S1base);

        // calculate a beat signature
        ArrayList efft = cb.beatSign(sample);

        // For the HMM to separate the observations in more cases than S1-S4, we need to
        // add a "minor" numbering to the "Sx" string.
        // However it will be added later, to have a reasonnable amount of Observations
        Observation obs = new Observation(pref, offsetRel, offsetAbs, efft);
        
        return obs;
    }
    
    ArrayList getSegmentedBeats() {
        return segmentedBeats;
    }
    
}
