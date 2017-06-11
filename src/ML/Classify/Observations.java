
package ML.Classify;

import java.util.ArrayList;

public class Observations
{
    ArrayList observ; // ArrayList of Observations
    private int pointer;

    public Observations(ArrayList obs)
    {
        observ = obs;
        pointer = 0;
    }

    public boolean hasNext()
    {
        return observ.size() > 0 && pointer < observ.size();
    }

    public String currentStateTag()
    {
        if(observ.size() > 0 && pointer < observ.size())
        {
            String name = currentObservation().getFullName();
            return name;
        } else
        {
            return null;
        }
    }

    public Observation currentObservation()
    {
        if(observ.size() > 0 && pointer < observ.size())
        {
            Observation obs = (Observation)observ.get(pointer);
            return obs;
        } else
        {
            return null;
        }
    }

    public void gotoNextObservation()
    {
        if(observ.size() > 0 && pointer - 1 < observ.size())
            pointer++;
    }

    public Observation next()
    {
        if(observ.size() > 0 && pointer < observ.size())
        {
            Observation obs = (Observation)observ.get(pointer);
            pointer++;
            return obs;
        } else
        {
            return null;
        }
    }

    public void incPtr()
    {
        pointer++;
    }

    int size() {
        return observ.size() ;
    }
    
    public Observation get(int i)
    {
        if(observ.size() > 0 && i < observ.size())
        {
            Observation obs = (Observation)observ.get(i);
            return obs;
        } else
        {
            return null;
        }
    }

    public void resetPtr() {
        pointer = 0 ;
    }

}
