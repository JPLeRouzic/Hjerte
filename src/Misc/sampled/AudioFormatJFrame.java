
package Misc.sampled;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import javax.sound.sampled.AudioFormat;
import javax.swing.*;

public class AudioFormatJFrame extends JFrame
    implements ActionListener
{

    public AudioFormatJFrame()
    {
        sampling_rate_rb_group = new ButtonGroup();
        sr_8000_rb = new JRadioButton("8 kHz");
        sr_11025_rb = new JRadioButton("11.025 kHz");
        sr_16000_rb = new JRadioButton("16 kHz");
        sr_22050_rb = new JRadioButton("22.05 kHz");
        sr_44100_rb = new JRadioButton("44.1 kHz");
        sr_other_rb = new JRadioButton("Other (kHz):");
        sr_text_area = new JTextArea("");
        bit_depth_rb_group = new ButtonGroup();
        bd_8_rb = new JRadioButton("8 bit");
        bd_16_rb = new JRadioButton("16 bit");
        bd_other_rb = new JRadioButton("Other:");
        bd_text_area = new JTextArea("");
        channels_rb_group = new ButtonGroup();
        chan_1_rb = new JRadioButton("Mono");
        chan_2_rb = new JRadioButton("Stereo");
        chan_other_rb = new JRadioButton("Other:");
        chan_text_area = new JTextArea("");
        signed_rb_group = new ButtonGroup();
        signed_rb = new JRadioButton("Signed PCM");
        unsigned_rb = new JRadioButton("Unsigned PCM");
        endian_rb_group = new ButtonGroup();
        big_endian_rb = new JRadioButton("Big Endian");
        little_endian_rb = new JRadioButton("Little Endian");
        sampling_rate_rb_group.add(sr_8000_rb);
        sampling_rate_rb_group.add(sr_11025_rb);
        sampling_rate_rb_group.add(sr_16000_rb);
        sampling_rate_rb_group.add(sr_22050_rb);
        sampling_rate_rb_group.add(sr_44100_rb);
        sampling_rate_rb_group.add(sr_other_rb);
        bit_depth_rb_group.add(bd_8_rb);
        bit_depth_rb_group.add(bd_16_rb);
        bit_depth_rb_group.add(bd_other_rb);
        channels_rb_group.add(chan_1_rb);
        channels_rb_group.add(chan_2_rb);
        channels_rb_group.add(chan_other_rb);
        signed_rb_group.add(signed_rb);
        signed_rb_group.add(unsigned_rb);
        endian_rb_group.add(big_endian_rb);
        endian_rb_group.add(little_endian_rb);
        low_quality_button = new JButton("Low Quality Settings");
        mid_quality_button = new JButton("Mid Quality Settings");
        high_quality_button = new JButton("High Quality Settings");
        cancel_button = new JButton("Cancel");
        ok_button = new JButton("OK");
        low_quality_button.addActionListener(this);
        mid_quality_button.addActionListener(this);
        high_quality_button.addActionListener(this);
        cancel_button.addActionListener(this);
        ok_button.addActionListener(this);
        setAudioFormat(getStandardMidQualityRecordAudioFormat());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                AudioFormatJFrame.this.cancel();
            }
        });
        setTitle("PCM Audio Format Selector");
        int horizontal_gap = 6;
        int vertical_gap = 11;
        (content_pane = getContentPane()).setLayout(new BorderLayout(6, 11));
        settings_panel = new JPanel(new GridLayout(19, 2, 6, 11));
        button_panel = new JPanel(new GridLayout(3, 2, 6, 11));
        settings_panel.add(new JLabel("Sampling Rate:"));
        settings_panel.add(sr_8000_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(sr_11025_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(sr_16000_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(sr_22050_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(sr_44100_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(sr_other_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(sr_text_area);
        settings_panel.add(new JLabel("Bit Depth:"));
        settings_panel.add(bd_8_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(bd_16_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(bd_other_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(bd_text_area);
        settings_panel.add(new JLabel("Channels:"));
        settings_panel.add(chan_1_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(chan_2_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(chan_other_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(chan_text_area);
        settings_panel.add(new JLabel("Signed Samples:"));
        settings_panel.add(signed_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(unsigned_rb);
        settings_panel.add(new JLabel("Byte Order:"));
        settings_panel.add(big_endian_rb);
        settings_panel.add(new JLabel(""));
        settings_panel.add(little_endian_rb);
        button_panel.add(low_quality_button);
        button_panel.add(new JLabel(""));
        button_panel.add(mid_quality_button);
        button_panel.add(cancel_button);
        button_panel.add(high_quality_button);
        button_panel.add(ok_button);
        content_pane.add(settings_panel, "Center");
        content_pane.add(button_panel, "South");
        pack();
    }

    public static AudioFormat getStandardLowQualityRecordAudioFormat()
    {
        return defineAudioFormat(8000F, 8, 1, true, true);
    }

    public static AudioFormat getStandardMidQualityRecordAudioFormat()
    {
        return defineAudioFormat(16000F, 16, 1, true, true);
    }

    public static AudioFormat getStandardHighQualityRecordAudioFormat()
    {
        return defineAudioFormat(44100F, 16, 1, true, true);
    }

    public static AudioFormat defineAudioFormat(float sample_rate, int sample_size, int channels, boolean signed, boolean big_endian)
    {
        return new AudioFormat(sample_rate, sample_size, channels, signed, big_endian);
    }

    public void setAudioFormat(AudioFormat audio_format)
    {
        if(audio_format != null)
        {
            float sample_rate = audio_format.getSampleRate();
            if(sample_rate == 8000F)
                sr_8000_rb.setSelected(true);
            else
            if(sample_rate == 11025F)
                sr_11025_rb.setSelected(true);
            else
            if(sample_rate == 16000F)
                sr_16000_rb.setSelected(true);
            else
            if(sample_rate == 22050F)
                sr_22050_rb.setSelected(true);
            else
            if(sample_rate == 44100F)
            {
                sr_44100_rb.setSelected(true);
            } else
            {
                sr_other_rb.setSelected(true);
                sr_text_area.setText((new Float(sample_rate * 1000F)).toString());
            }
            int bit_depth = audio_format.getSampleSizeInBits();
            if(bit_depth == 8)
                bd_8_rb.setSelected(true);
            else
            if(bit_depth == 16)
            {
                bd_16_rb.setSelected(true);
            } else
            {
                bd_other_rb.setSelected(true);
                bd_text_area.setText(Integer.toString(bit_depth));
            }
            int channels = audio_format.getChannels();
            if(channels == 1)
                chan_1_rb.setSelected(true);
            else
            if(channels == 2)
            {
                chan_2_rb.setSelected(true);
            } else
            {
                chan_other_rb.setSelected(true);
                chan_text_area.setText((new Integer(bit_depth)).toString());
            }
            javax.sound.sampled.AudioFormat.Encoding encoding = audio_format.getEncoding();
            if(encoding == javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED)
                signed_rb.setSelected(true);
            else
            if(encoding == javax.sound.sampled.AudioFormat.Encoding.PCM_UNSIGNED)
                unsigned_rb.setSelected(true);
            boolean is_big_endian = audio_format.isBigEndian();
            if(is_big_endian)
                big_endian_rb.setSelected(true);
            else
                little_endian_rb.setSelected(true);
        }
    }

    public AudioFormat getAudioFormat(boolean allow_text_selections)
        throws Exception
    {
        if(!allow_text_selections)
        {
            if(sr_other_rb.isSelected())
                throw new Exception((new StringBuilder()).append("Illegal sampling rate of ").append(sr_text_area.getText()).append(".\nOnly sampling rates of 8, 11.025, 16, 22.05 and 44.1 kHz are\naccepted under the current settings.").toString());
            if(bd_other_rb.isSelected())
                throw new Exception((new StringBuilder()).append("Illegal bit depth of ").append(bd_text_area.getText()).append(".\nOnly bit depths of 8 or 16 bits are accepted under the current settings.").toString());
            if(chan_other_rb.isSelected())
                throw new Exception((new StringBuilder()).append("Illegal number of channels (").append(chan_text_area.getText()).append(").\nOnly 1 or 2 channels are accepted under the current settings.").toString());
        }
        float sample_rate = 8000F;
        if(sr_8000_rb.isSelected())
            sample_rate = 8000F;
        else
        if(sr_11025_rb.isSelected())
            sample_rate = 11025F;
        else
        if(sr_16000_rb.isSelected())
            sample_rate = 16000F;
        else
        if(sr_22050_rb.isSelected())
            sample_rate = 22050F;
        else
        if(sr_44100_rb.isSelected())
            sample_rate = 44100F;
        else
        if(sr_other_rb.isSelected())
            sample_rate = Float.parseFloat(sr_text_area.getText());
        int bit_depth = 8;
        if(bd_8_rb.isSelected())
            bit_depth = 8;
        else
        if(bd_16_rb.isSelected())
            bit_depth = 16;
        else
        if(bd_other_rb.isSelected())
            bit_depth = Integer.parseInt(bd_text_area.getText());
        int channels = 1;
        if(chan_1_rb.isSelected())
            channels = 1;
        else
        if(chan_2_rb.isSelected())
            channels = 2;
        else
        if(chan_other_rb.isSelected())
            channels = Integer.parseInt(chan_text_area.getText());
        boolean is_signed = true;
        if(signed_rb.isSelected())
            is_signed = true;
        else
        if(unsigned_rb.isSelected())
            is_signed = false;
        boolean is_big_endian = true;
        if(big_endian_rb.isSelected())
            is_big_endian = true;
        else
        if(little_endian_rb.isSelected())
            is_big_endian = false;
        return new AudioFormat(sample_rate, bit_depth, channels, is_signed, is_big_endian);
    }

    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource().equals(low_quality_button))
            setAudioFormat(getStandardLowQualityRecordAudioFormat());
        else
        if(event.getSource().equals(mid_quality_button))
            setAudioFormat(getStandardMidQualityRecordAudioFormat());
        else
        if(event.getSource().equals(high_quality_button))
            setAudioFormat(getStandardHighQualityRecordAudioFormat());
        else
        if(event.getSource().equals(cancel_button))
            cancel();
        else
        if(event.getSource().equals(ok_button))
            setVisible(false);
    }

    public void setVisible(boolean b)
    {
        super.setVisible(b);
        try
        {
            if(b)
                temp_format = getAudioFormat(true);
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.exit(0);
        }
    }

    private void cancel()
    {
        setAudioFormat(temp_format);
        setVisible(false);
    }

    private AudioFormat temp_format;
    private Container content_pane;
    private JPanel settings_panel;
    private JPanel button_panel;
    private ButtonGroup sampling_rate_rb_group;
    private JRadioButton sr_8000_rb;
    private JRadioButton sr_11025_rb;
    private JRadioButton sr_16000_rb;
    private JRadioButton sr_22050_rb;
    private JRadioButton sr_44100_rb;
    private JRadioButton sr_other_rb;
    private JTextArea sr_text_area;
    private ButtonGroup bit_depth_rb_group;
    private JRadioButton bd_8_rb;
    private JRadioButton bd_16_rb;
    private JRadioButton bd_other_rb;
    private JTextArea bd_text_area;
    private ButtonGroup channels_rb_group;
    private JRadioButton chan_1_rb;
    private JRadioButton chan_2_rb;
    private JRadioButton chan_other_rb;
    private JTextArea chan_text_area;
    private ButtonGroup signed_rb_group;
    private JRadioButton signed_rb;
    private JRadioButton unsigned_rb;
    private ButtonGroup endian_rb_group;
    private JRadioButton big_endian_rb;
    private JRadioButton little_endian_rb;
    private JButton low_quality_button;
    private JButton mid_quality_button;
    private JButton high_quality_button;
    private JButton cancel_button;
    private JButton ok_button;

}
