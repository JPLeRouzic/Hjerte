
package Misc.Gui.Actions;

import java.awt.event.ActionEvent;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;

public class SamplingRateAction extends AbstractAction
{

    public SamplingRateAction()
    {
        sampleRateIndex = 2;
    }

    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i < samplingRates.length; i++)
            if(e.getSource().equals(samplingRates[i]))
                sampleRateIndex = i;

    }

    public void setTarget(JRadioButtonMenuItem s[])
    {
        samplingRates = new JRadioButtonMenuItem[s.length];
        for(int i = 0; i < samplingRates.length; i++)
            samplingRates[i] = s[i];

    }

    public int getSelected()
    {
        return sampleRateIndex;
    }

    public float getSamplingRate()
    {
        float base = Float.parseFloat(samplingRates[sampleRateIndex].getText());
        return base * 1000F;
    }

    public void setSelected(int i)
    {
        if(samplingRates == null || i < 0 || i >= samplingRates.length)
        {
            System.err.println((new StringBuilder()).append("INTERNAL ERROR: ").append(i).append(" does not correspond to any sampling rate index").toString());
        } else
        {
            samplingRates[i].setSelected(true);
            sampleRateIndex = i;
        }
    }

    static final long serialVersionUID = 1L;
    private int sampleRateIndex;
    public JRadioButtonMenuItem samplingRates[];
}
