package ML.Classify;

import ML.Train.HMM;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*
 * The Viterbi algorithm is a dynamic programming algorithm for finding the most likely 
 * sequence of hidden states – called the Viterbi path – that results in a sequence of 
 * observed events, especially in the context of Markov information sources and hidden Markov models.
 */
/**
 *
 * @author jplr
 */
public class Viterbi {

    /**
     *
     * @param mm
     * @param trainedHMM
     * @param observations
     */
    public void viterbi(HMM trainedHMM, ArrayList observations) {
        //two-dimensional Viterbi Matrix
        boolean sentenceStart = true;
        HashMap prevMap = null;
        for (int i = 0; i < observations.size(); i++) {
            Observation word = (Observation) observations.get(i);
            HashMap subMap = new HashMap();

            if (sentenceStart) {
                Node n = new Node(word, "S1", (float) 1.0, null);
                subMap.put(word, n);
                sentenceStart = false;
            } else {
                //add all possible states (given the current observation) to the Viterbi matrix                
                if (trainedHMM.stateForObservationCounts.containsKey(word)) {
                    // Only Training Set tags
                    HashMap tagcounts = (HashMap) trainedHMM.stateForObservationCounts.get(word);
                    for (Iterator it = tagcounts.keySet().iterator(); it.hasNext();) {
                        String tag = (String) it.next();
                        subMap.put(tag, calcNode(trainedHMM, word, tag, prevMap));
                    }

                } else {
                    //never-before seen observations we can't guess for morphologically Every-Tag Guessing
                    // trainedHMM.stateForObservationCounts does not contain this word
                    for (Iterator it = trainedHMM.stateForObservationCounts.keySet().iterator(); it.hasNext();) {
                        String tag = (String) it.next();
                        subMap.put(tag, calcNode(trainedHMM, word, tag, prevMap));
                    }/*
                    // Every-Tag Guessing
                    for (String tag : tagCounts.keySet()) {
                    subMap.put(tag, calcNode(word, tag, prevMap));
                    }
                     */
                }

                if ((i == observations.size() - 1) || observations.get(i + 1).equals("S1")) {
                    // prints out the linked list of the correctly tagged observations
//                    trainedHMM.printStateInfo(subMap);
                    sentenceStart = true;
                }
            }
            prevMap = subMap;
        }
    }

    /**
     * This method computes the probability that String "state" is the
     * appropriate "state" for "String" observation, given the probabilities
     * before it (found in prevMap)
     *
     * probability = max ( previous Viterbi path probability * transition
     * probability * state observation likelihood )
     *
     */
    private Node calcNode(HMM mm, Observation word, String tag, HashMap prevMap) {
        Node n = new Node(word, tag);
        float maxProb = (float) 0.0;

        for (Iterator it = prevMap.keySet().iterator(); it.hasNext();) {
            String prevTag = (String) it.next();

            Node prevNode = (Node) prevMap.get(prevTag);

            //this is the previous Viterbi path probability
            float prevProb = prevNode.getProbability();

            //this is the transition probability
            prevProb = prevProb * mm.calcPriorProbState(prevTag, tag);
            if (prevProb >= maxProb) {
                maxProb = prevProb;
                n.parent = prevNode;
            }
        }
        //this is the state observation likelihood
        float probl = maxProb * mm.calcLikelihood(tag, word);
        n.setProbability(probl);
        return n;
    }

}
