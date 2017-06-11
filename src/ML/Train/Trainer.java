
package ML.Train;

import ML.Classify.Observation;
import java.util.HashMap;

// Referenced classes of package ML.Train:
//            HMM

public class Trainer
{

    public Trainer()
    {
    }

    public String parseTrainer(HMM p, String prevState, String currentState, Observation currentObservation)
    {
        addStateToMap_Hid(p.hidnStatesCounts, currentState);
        addBothKeysToMap_obsrvCnts_state_obsrv(p.observationCounts, currentState, currentObservation);
        addBothKeysToMap_bigramCnts_perState_currentState(p.transitionsProbs, prevState, currentState);
        addBothKeysToMapstateObsCnt_currObs_currStat(p.stateForObservationCounts, currentObservation, currentState);
        if(((Integer)p.hidnStatesCounts.get(currentState)).intValue() >= p.mostFreqStateCount.intValue())
        {
            p.mostFreqStateCount = ((Integer)p.hidnStatesCounts.get(currentState));
            p.mostFreqState = currentState;
        }
        p.numTrainingBigrams++;
        return currentState;
    }

    void addStateToMap_Hid(HashMap map, String key1)
    {
        if(map.containsKey(key1))
            map.put(key1, Integer.valueOf(((Integer)map.get(key1)).intValue() + 1));
        else
            map.put(key1, Integer.valueOf(1));
    }

    void addStateToMap_Obs(HashMap map, Observation key1)
    {
        if(map.containsKey(key1))
            map.put(key1, Integer.valueOf(((Integer)map.get(key1)).intValue() + 1));
        else
            map.put(key1, Integer.valueOf(1));
    }

    private void addBothKeysToMap_obsrvCnts_state_obsrv(HashMap observationCounts, String currentState, Observation currentObservation)
    {
        if(observationCounts.containsKey(currentState))
        {
            addStateToMap_Obs((HashMap)observationCounts.get(currentState), currentObservation);
        } else
        {
            HashMap subMap = new HashMap();
            subMap.put(currentObservation, Integer.valueOf(1));
            observationCounts.put(currentState, subMap);
        }
    }

    private void addBothKeysToMap_bigramCnts_perState_currentState(HashMap stateBigramCounts, String prevState, String currentState)
    {
        if(stateBigramCounts.containsKey(prevState))
        {
            addStateToMap_Hid((HashMap)stateBigramCounts.get(prevState), currentState);
        } else
        {
            HashMap subMap = new HashMap();
            subMap.put(currentState, Integer.valueOf(1));
            stateBigramCounts.put(prevState, subMap);
        }
    }

    private void addBothKeysToMapstateObsCnt_currObs_currStat(HashMap stateForObservationCounts, Observation currentObservation, String currentState)
    {
        if(stateForObservationCounts.containsKey(currentObservation))
        {
            addStateToMap_Hid((HashMap)stateForObservationCounts.get(currentObservation), currentState);
        } else
        {
            HashMap subMap = new HashMap();
            subMap.put(currentState, Integer.valueOf(1));
            stateForObservationCounts.put(currentObservation, subMap);
        }
    }
}
