package Misc.Gui.Actions;

import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.AbstractAction;

public class SamplingRateAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    private int sampleRateIndex;
    public JRadioButtonMenuItem[] samplingRates;
    
    public SamplingRateAction() {
        this.sampleRateIndex = 2;
    }
    
    public void actionPerformed(final ActionEvent e) {
        for (int i = 0; i < this.samplingRates.length; ++i) {
            if (e.getSource().equals(this.samplingRates[i])) {
                this.sampleRateIndex = i;
            }
        }
    }
    
    public void setTarget(final JRadioButtonMenuItem[] s) {
        this.samplingRates = new JRadioButtonMenuItem[s.length];
        for (int i = 0; i < this.samplingRates.length; ++i) {
            this.samplingRates[i] = s[i];
        }
    }
    
    public int getSelected() {
        return this.sampleRateIndex;
    }
    
    public float getSamplingRate() {
        final float base = Float.parseFloat(this.samplingRates[this.sampleRateIndex].getText());
        return base * 1000.0f;
    }
    
    public void setSelected(final int i) {
        if (this.samplingRates == null || i < 0 || i >= this.samplingRates.length) {
            System.err.println("INTERNAL ERROR: " + i + " does not correspond to any sampling rate index");
        }
        else {
            this.samplingRates[i].setSelected(true);
            this.sampleRateIndex = i;
        }
    }
}
