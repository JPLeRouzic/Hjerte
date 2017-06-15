package ML.Classify;

import java.util.ArrayList;

public class Observations
{
    ArrayList observ;
    private int pointer;
    
    public Observations(final ArrayList obs) {
        this.observ = obs;
        this.pointer = 0;
    }
    
    public boolean hasNext() {
        return this.observ.size() > 0 && this.pointer < this.observ.size();
    }
    
    public String currentStateTag() {
        if (this.observ.size() > 0 && this.pointer < this.observ.size()) {
            final String name = this.currentObservation().getFullName();
            return name;
        }
        return null;
    }
    
    public Observation currentObservation()
    {
        if(observ.size() > 0 && pointer < observ.size())
        {
            Observation obs = (Observation)observ.get(pointer);
            return obs;
        }
        return null;
    }
    
    public void gotoNextObservation() {
        if (this.observ.size() > 0 && this.pointer - 1 < this.observ.size()) {
            ++this.pointer;
        }
    }
    
    public Observation next()
    {
        if(observ.size() > 0 && pointer < observ.size())
        {
            Observation obs = (Observation)observ.get(pointer);
            pointer++;
            return obs;
        }
        return null;
    }
    
    public void incPtr() {
        ++this.pointer;
    }
    
    int size() {
        return this.observ.size();
    }
    
    public Observation get(int i)
    {
        if(observ.size() > 0 && i < observ.size())
        {
            Observation obs = (Observation)observ.get(i);
            return obs;
        }
        return null;
    }
    
    public void resetPtr() {
        this.pointer = 0;
    }
}
