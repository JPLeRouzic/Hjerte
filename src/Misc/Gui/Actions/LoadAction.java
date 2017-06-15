package Misc.Gui.Actions;

import Misc.XML.XMLDocumentParser;
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

public class LoadAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    private JTextArea window_length_text_field;
    private JTextArea window_overlap_fraction_text_field;
    private JCheckBox save_window_features_check_box;
    private JCheckBox save_overall_file_featurese_check_box;
    private JFileChooser save_file_chooser;
    private Control controller;
    
    public LoadAction(final Control c) {
        super("Load Settings...");
        this.save_file_chooser = null;
        this.controller = c;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.save_file_chooser == null) {
            (this.save_file_chooser = new JFileChooser()).setCurrentDirectory(new File("."));
            this.save_file_chooser.setFileFilter(new FileFilterXML());
        }
        String path = null;
        final int dialog_result = this.save_file_chooser.showOpenDialog(null);
        if (dialog_result == 0) {
            File to_save_to = this.save_file_chooser.getSelectedFile();
            path = to_save_to.getPath();
            final String ext = StringMethods.getExtension(path);
            if (ext == null) {
                path += ".xml";
                to_save_to = new File(path);
            }
            else if (!ext.equals(".xml")) {
                path = StringMethods.removeExtension(path) + ".xml";
                to_save_to = new File(path);
            }
            if (!to_save_to.exists()) {
                JOptionPane.showMessageDialog(null, "The file '" + to_save_to.getName() + "' does not exist", "ERROR", 0);
            }
            else {
                try {
                    final Object[] tmp = (Object[])XMLDocumentParser.parseXMLDocument(path, "save_settings");
                    this.window_length_text_field.setText((String)tmp[0]);
                    this.window_overlap_fraction_text_field.setText((String)tmp[1]);
                    final float rate = (float)tmp[2];
                    this.save_window_features_check_box.setSelected((boolean)tmp[4]);
                    this.save_overall_file_featurese_check_box.setSelected((boolean)tmp[5]);
                }
                catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, e2.getMessage(), "ERROR", 0);
                }
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
