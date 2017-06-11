
package ML.Classify;


// Referenced classes of package ML.Classify:
//            Observation

public class Node
{

    public Node(Observation word, String tag, float prob, Node parent)
    {
        this.word = word;
        this.tag = tag;
        probability = prob;
        this.parent = parent;
    }

    public Node(Observation word, String tag)
    {
        this(word, tag, 0.0F, null);
    }

    public float getProbability()
    {
        return probability;
    }

    public void setProbability(float proba)
    {
        probability = proba;
    }

    public Observation word;
    public String tag;
    public float probability;
    public Node parent;
}
