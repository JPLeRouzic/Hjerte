package ML.Classify;

import Misc.Gui.Main.EntryPoint;
import java.util.Collection;
import java.util.Set;

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
     * We look at each hidden state in hmmTest to find if it also belongs to
     * hmmTrain - If it does not, go to next hidden state (except if it is the
     * last one) - It it belongs to hmmTrain, then compare the next observations
     * in both HMMs report a hidden state similarity (a percentage) based on the
     * number of correlations, starting from the root hidden state When all
     * hidden states have been processed, add all hidden state similarity scores
     * and divide by the number of root hidden states
     *
     * @param hmmTest
     * @param hmmTrain
     */
    float viterbi() {
        float globalScore = 0, similarity = 0;

        // get the keys and values of the training HMM
        Collection valuesTrain = EntryPoint.hmmTrain.transitionsProbs.values();
        Set keysTrain = EntryPoint.hmmTrain.transitionsProbs.keySet();
        Object[] valuesTrainArray = valuesTrain.toArray();
        Object[] valuesTrainKeys = keysTrain.toArray();

        // get the keys and values of the test HMM
        Collection valuesTest = EntryPoint.hmmTest.transitionsProbs.values();
        Set keysTest = EntryPoint.hmmTest.transitionsProbs.keySet();
        Object[] valuesTestArray = valuesTest.toArray();
        Object[] valuesTestKeys = keysTest.toArray();

        // Browse each hidden state in hmmTest to find if it also belongs to hmmTrain 
        for (int hiddenStateTestIdx = 0; hiddenStateTestIdx < valuesTestArray.length; hiddenStateTestIdx++) {

            String TestKey = valuesTestKeys[hiddenStateTestIdx].toString();
            String TestValues = valuesTestArray[hiddenStateTestIdx].toString();

            for (int hiddenStateTrainIdx = 0; hiddenStateTrainIdx < valuesTrainArray.length; hiddenStateTrainIdx++) {
                String trainKey = valuesTrainKeys[hiddenStateTrainIdx].toString();
                String trainValues = valuesTrainArray[hiddenStateTrainIdx].toString();
                if (TestKey.contentEquals(trainKey)) {
                    // The two hidden states are the same
                    float branchScore = exploreBranch(TestValues, trainValues);
                    globalScore += branchScore;
                    break;
                }
            }

        }
        similarity = globalScore / valuesTestArray.length;
        return similarity ;
    }

    /**
     * compare the next observations in both HMMs report a hidden state
     * similarity (a percentage) based on the number of correlations, starting
     * from the root hidden state When all hidden states have been processed,
     * add all hidden state similarity scores and divide by the number of root
     * hidden states
     *
     * @param testValues
     * @param trainValues
     * @return
     */
    private float exploreBranch(String testValues, String trainValues) {
        float localScore;
        float similarity = 0;
        String delims = "[{}=,]";
        int divider = 0;

        String[] tokensTest = testValues.split(delims);
        String[] tokensTrain = trainValues.split(delims);

        for (int idxtst = 0; idxtst < tokensTest.length; idxtst++) {
//            System.out.println("1, tokensTest: " + tokensTest[idxtst]);
            char[] uno = tokensTest[idxtst].toCharArray();
            if ((uno != null) && (uno.length > 1)) {

                int udx = 0;
                while ((udx < uno.length) && (uno[udx] != ('S'))) {
                    udx++;
                }
                if(udx >= uno.length) {
                    continue ;
                }
                if (uno[udx] == ('S')) {
//                System.out.println("2: It starts with S");
                    divider++;
                    for (int idxtrn = 0; idxtrn < tokensTrain.length; idxtrn++) {
//                    System.out.println("3, tokensTest: " + tokensTest[idxtst] + "  tokensTrain: " + tokensTrain[idxtrn]);
                        if (tokensTest[idxtst].trim().contentEquals(tokensTrain[idxtrn].trim())) {
//                        System.out.println("4: tokensTest (" + tokensTest[idxtst].trim() + ") is equal to tokensTrain (" + tokensTrain[idxtrn].trim() + ")");
                            similarity++;
                            // Now find next tokensTest
                            break ;
                        } else {
//                        System.out.println("tokensTest is not equal to tokensTrain") ;
                        }
                    }
                } else {
//               System.out.println("Not 'S'; " + tokensTest[idxtst]) ;
                }
            }
        }
//        System.out.println("similarity: " + similarity + "  divider: " + divider);
        localScore = similarity / divider;
        return localScore;
    }
}
