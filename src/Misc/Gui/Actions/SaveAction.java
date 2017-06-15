package Misc.Gui.Actions;

import java.io.IOException;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import Misc.Tools.StringMethods;
import java.awt.Component;
import javax.swing.filechooser.FileFilter;
import Misc.XML.FileFilterXML;
import java.io.File;
import java.awt.event.ActionEvent;
import Misc.Gui.Controller.Control;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;

public class SaveAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    private JTextArea window_length_text_field;
    private JTextArea window_overlap_fraction_text_field;
    private JCheckBox save_window_features_check_box;
    private JCheckBox save_overall_file_featurese_check_box;
    private JFileChooser save_file_chooser;
    private Control controller;
    
    public SaveAction(final Control c) {
        super("Save Settings...");
        this.save_file_chooser = null;
        this.controller = c;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.save_file_chooser == null) {
            (this.save_file_chooser = new JFileChooser()).setCurrentDirectory(new File("."));
            this.save_file_chooser.setFileFilter(new FileFilterXML());
        }
        final int dialog_result = this.save_file_chooser.showSaveDialog(null);
        if (dialog_result == 0) {
            File to_save_to = this.save_file_chooser.getSelectedFile();
            String path = to_save_to.getPath();
            final String ext = StringMethods.getExtension(path);
            if (ext == null) {
                path += ".xml";
                to_save_to = new File(path);
            }
            else if (!ext.equals(".xml")) {
                path = StringMethods.removeExtension(path) + ".xml";
                to_save_to = new File(path);
            }
            if (to_save_to.exists()) {
                final int overwrite = JOptionPane.showConfirmDialog(null, "This file already exists.\nDo you wish to overwrite it?", "WARNING", 0);
                if (overwrite != 0) {
                    path = null;
                }
            }
            if (path == null) {
                final String message = "No path for file ";
                JOptionPane.showMessageDialog(null, message, "ERROR", 0);
                return;
            }
            final File dest = new File(path);
            final String winSize = this.window_length_text_field.getText();
            final String winOverlap = this.window_overlap_fraction_text_field.getText();
            final boolean perWindow = this.save_window_features_check_box.isSelected();
            final boolean overall = this.save_overall_file_featurese_check_box.isSelected();
            try {
                final FileWriter fw = new FileWriter(dest);
                final String sep = System.getProperty("line.separator");
                fw.write("<?xml version=\"1.0\"?>" + sep);
                fw.write("<!DOCTYPE save_settings [" + sep);
                fw.write("\t<!ELEMENT save_settings (windowSize,windowOverlap,samplingRate,normalise,perWindowStats,overallStats,outputType,feature+,aggregator+)>" + sep);
                fw.write("\t<!ELEMENT windowSize (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT windowOverlap (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT samplingRate (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT normalise (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT perWindowStats (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT overallStats (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT outputType (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT feature (name,active,attribute*)>" + sep);
                fw.write("\t<!ELEMENT name (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT active (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT attribute (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT aggregator (aggregatorName, aggregatorFeature*, aggregatorAttribute*)>" + sep);
                fw.write("\t<!ELEMENT aggregatorName (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT aggregatorFeature (#PCDATA)>" + sep);
                fw.write("\t<!ELEMENT aggregatorAttribute (#PCDATA)>" + sep);
                fw.write("]>" + sep);
                fw.write(sep);
                fw.write("<save_settings>" + sep);
                fw.write("\t<windowSize>" + winSize + "</windowSize>" + sep);
                fw.write("\t<windowOverlap>" + winOverlap + "</windowOverlap>" + sep);
                fw.write("\t<perWindowStats>" + perWindow + "</perWindowStats>" + sep);
                fw.write("\t<overallStats>" + overall + "</overallStats>" + sep);
                fw.write("</save_settings>" + sep);
                fw.close();
            }
            catch (IOException e2) {
                JOptionPane.showMessageDialog(null, e2.getMessage(), "ERROR", 0);
                e2.printStackTrace();
            }
        }
    }
    
    public void setObjectReferences(final JTextArea win_length, final JTextArea win_overlap, final JCheckBox save_window, final JCheckBox save_overall) {
        this.window_length_text_field = win_length;
        this.window_overlap_fraction_text_field = win_overlap;
        this.save_window_features_check_box = save_window;
        this.save_overall_file_featurese_check_box = save_overall;
    }
}
