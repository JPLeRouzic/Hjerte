
package ML.Train;

import ML.Classify.*;
import java.util.*;

// Referenced classes of package ML.Train:
//            HMMutilities, Trainer

public class HMM
{
    Observations obs;
    
    public String mostFreqState;
    
    public HashMap observationCounts;           // HashMap<String, HashMap<Observation, Integer>>
    
    // This is the most important item in this class
    // It gives the probability of transition from one state to the next
    // for each next step
    // "Current state" => {
    //         ("next state 1", probability = 0.1),
    //         ("next state 2", probability = 0.6),
    //         ("next state 3", probability = 0.4)
    public HashMap transitionsProbs;           // HashMap<"CurrentState", HashMap<"NextState", probability>>

    public HashMap hidnStatesCounts;            // HashMap<String, Integer>
    public HashMap stateForObservationCounts;   // HashMap<Observation, HashMap<String state, Integer>>
    
    Integer mostFreqStateCount;
    int numTrainingBigrams;
    
    HMMutilities hmmUtil;
    private float similarity;

    public HMM(ArrayList obb)
    {
        obs = null;
        mostFreqState = null;
        observationCounts = new HashMap();
        numTrainingBigrams = 0;
        transitionsProbs = new HashMap();
        hidnStatesCounts = new HashMap();
        stateForObservationCounts = new HashMap();
        mostFreqStateCount = new Integer(0) ;
        obs = new Observations(obb);
    }

    public void train()
    {
        String prevState = null;
        Observation currentObservation = null;
        Trainer trn = new Trainer();
        prevState = obs.currentStateTag();
        obs.gotoNextObservation();
        while(obs.hasNext()) 
        {
            String currentState = obs.currentStateTag();
            currentObservation = obs.currentObservation();
            obs.incPtr();
            prevState = trn.parseTrainer(this, prevState, currentState, currentObservation);
        }
    }

    /**
     * The class initialization has read observation 
     * This method sends them in a list
     *
     * @return
     */
    public ArrayList getObsrvSeq()
    {
        ArrayList list = new ArrayList();
        for(; obs.hasNext(); list.add(obs.currentObservation())) {
            obs.gotoNextObservation();
        }

        return list;
    }

    /*
     * Calculates probability of (State|Observation), that this state corresponds to that Observation
     */
    public float calcLikelihood(String state, Observation word)
    {
        int vocabSize = stateForObservationCounts.keySet().size();
        int deux;
        float trois;
        deux = (HMMutilities.countsObsrv(observationCounts, state, word) + 1) ;
        trois = (float)(HMMutilities.countStates(hidnStatesCounts, state) + vocabSize);
        float un = deux / trois ;
        return (float) un;
    }

    /*
     * Calculates probability of (State1|State2), of transition from state1 to state2
     */
    public float calcPriorProbState(String state1, String state2)
    {
        int vocabSize = hidnStatesCounts.keySet().size();
        float deux = (float)(HMMutilities.countsStates(transitionsProbs, state1, state2) + 1);
        float trois = (float)(HMMutilities.countStates(hidnStatesCounts, state1) + vocabSize);
        return deux / trois ;
    }

    /* 
     * This method computes the probability that State is the appropriate state for this Observation,
     * given the probabilities before it (found in prevMap) 
     * 
     * probability = max ( previous Viterbi path probability *
     *                     transition probability *
     *                     state observation likelihood )
     *                     
     */
    public Node calcNode(Observation word, String state, HashMap prevMap)
    {
        Node n = new Node(word, state);
        float maxProb = 0.0F;
        Iterator iterator = prevMap.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String prevTag = (String)iterator.next();
            Node prevNode = (Node)prevMap.get(prevTag);
            float prevProb = prevNode.probability;
            prevProb *= calcPriorProbState(prevTag, state);
            if(prevProb >= maxProb)
            {
                maxProb = prevProb;
                n.parent = prevNode;
            }
        } while(true);
        n.probability = maxProb * calcLikelihood(state, word);
        return n;
    }

    public void setSimilarity(float simil) {
        similarity = simil ;
    }

    public String getSimilarity() {
        return String.valueOf(similarity) ;
    }
}
