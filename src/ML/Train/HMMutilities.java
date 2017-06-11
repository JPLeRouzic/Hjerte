
package ML.Train;

import ML.Classify.Node;
import ML.Classify.Observation;
import java.io.PrintStream;
import java.util.*;

// Referenced classes of package ML.Train:
//            HMM

public class HMMutilities extends HMM
{

    public HMMutilities(ArrayList obb)
    {
        super(obb);
    }

    static int countStates(HashMap map, String key)
    {
        return map.containsKey(key) ? ((Integer)map.get(key)).intValue() : 0;
    }

    private static int countObsrv(HashMap map, Observation key)
    {
        return map.containsKey(key) ? ((Integer)map.get(key)).intValue() : 0;
    }

    static int countsObsrv(HashMap map, String key1, Observation key2)
    {
        return map.containsKey(key1) ? countObsrv((HashMap)map.get(key1), key2) : 0;
    }

    static int countsStates(HashMap tagBigramCounts, String tag1, String tag2)
    {
        return tagBigramCounts.containsKey(tag1) ? countStates((HashMap)tagBigramCounts.get(tag1), tag2) : 0;
    }

    private static void backtrace(Node n)
    {
        Stack stack = new Stack();
        for(; n != null; n = n.parent)
            stack.push(n);

        for(; !stack.isEmpty(); System.out.println((new StringBuilder()).append(n.tag).append(" ").append(n.word).toString()))
            n = (Node)stack.pop();

    }

    static final boolean ADDONE = true;
}
