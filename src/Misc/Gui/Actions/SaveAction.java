
package Misc.Gui.Actions;

import Misc.Gui.Controller.Control;
import Misc.Tools.StringMethods;
import Misc.XML.FileFilterXML;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;

public class SaveAction extends AbstractAction
{

    static final long serialVersionUID = 1L;
    private JTextArea window_length_text_field;
    private JTextArea window_overlap_fraction_text_field;
    private JCheckBox save_window_features_check_box;
    private JCheckBox save_overall_file_featurese_check_box;
    private JFileChooser save_file_chooser;
    private Control controller;

    public SaveAction(Control c)
    {
        super("Save Settings...");
        save_file_chooser = null;
        controller = c;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(save_file_chooser == null)
        {
            save_file_chooser = new JFileChooser();
            save_file_chooser.setCurrentDirectory(new File("."));
            save_file_chooser.setFileFilter(new FileFilterXML());
        }
        int dialog_result = save_file_chooser.showSaveDialog(null);
        if(dialog_result == 0)
        {
            File to_save_to = save_file_chooser.getSelectedFile();
            String path = to_save_to.getPath();
            String ext = StringMethods.getExtension(path);
            if(ext == null)
            {
                path = (new StringBuilder()).append(path).append(".xml").toString();
                to_save_to = new File(path);
            } else
            if(!ext.equals(".xml"))
            {
                path = (new StringBuilder()).append(StringMethods.removeExtension(path)).append(".xml").toString();
                to_save_to = new File(path);
            }
            if(to_save_to.exists())
            {
                int overwrite = JOptionPane.showConfirmDialog(null, "This file already exists.\nDo you wish to overwrite it?", "WARNING", 0);
                if(overwrite != 0)
                    path = null;
            }
            if(path == null)
            {
                String message = "No path for file ";
                JOptionPane.showMessageDialog(null, message, "ERROR", 0);
                return;
            }
            File dest = new File(path);
            String winSize = window_length_text_field.getText();
            String winOverlap = window_overlap_fraction_text_field.getText();
            boolean perWindow = save_window_features_check_box.isSelected();
            boolean overall = save_overall_file_featurese_check_box.isSelected();
            try
            {
                FileWriter fw = new FileWriter(dest);
                String sep = System.getProperty("line.separator");
                fw.write((new StringBuilder()).append("<?xml version=\"1.0\"?>").append(sep).toString());
                fw.write((new StringBuilder()).append("<!DOCTYPE save_settings [").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT save_settings (windowSize,windowOverlap,samplingRate,normalise,perWindowStats,overallStats,outputType,feature+,aggregator+)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT windowSize (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT windowOverlap (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT samplingRate (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT normalise (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT perWindowStats (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT overallStats (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT outputType (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT feature (name,active,attribute*)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT name (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT active (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT attribute (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT aggregator (aggregatorName, aggregatorFeature*, aggregatorAttribute*)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT aggregatorName (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT aggregatorFeature (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<!ELEMENT aggregatorAttribute (#PCDATA)>").append(sep).toString());
                fw.write((new StringBuilder()).append("]>").append(sep).toString());
                fw.write(sep);
                fw.write((new StringBuilder()).append("<save_settings>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<windowSize>").append(winSize).append("</windowSize>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<windowOverlap>").append(winOverlap).append("</windowOverlap>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<perWindowStats>").append(perWindow).append("</perWindowStats>").append(sep).toString());
                fw.write((new StringBuilder()).append("\t<overallStats>").append(overall).append("</overallStats>").append(sep).toString());
                fw.write((new StringBuilder()).append("</save_settings>").append(sep).toString());
                fw.close();
            }
            catch(IOException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", 0);
                e1.printStackTrace();
            }
        }
    }

    public void setObjectReferences(JTextArea win_length, JTextArea win_overlap, JCheckBox save_window, JCheckBox save_overall)
    {
        window_length_text_field = win_length;
        window_overlap_fraction_text_field = win_overlap;
        save_window_features_check_box = save_window;
        save_overall_file_featurese_check_box = save_overall;
    }
}
