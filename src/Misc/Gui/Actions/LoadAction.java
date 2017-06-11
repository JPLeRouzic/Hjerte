
package Misc.Gui.Actions;

import Misc.Gui.Controller.Control;
import Misc.Tools.StringMethods;
import Misc.XML.FileFilterXML;
import Misc.XML.XMLDocumentParser;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;

public class LoadAction extends AbstractAction
{

    static final long serialVersionUID = 1L;
    private JTextArea window_length_text_field;
    private JTextArea window_overlap_fraction_text_field;
    private JCheckBox save_window_features_check_box;
    private JCheckBox save_overall_file_featurese_check_box;
    private JFileChooser save_file_chooser;
    private Control controller;

    public LoadAction(Control c)
    {
        super("Load Settings...");
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
        String path = null;
        int dialog_result = save_file_chooser.showOpenDialog(null);
        if(dialog_result == 0)
        {
            File to_save_to = save_file_chooser.getSelectedFile();
            path = to_save_to.getPath();
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
            if(!to_save_to.exists())
                JOptionPane.showMessageDialog(null, (new StringBuilder()).append("The file '").append(to_save_to.getName()).append("' does not exist").toString(), "ERROR", 0);
            else
                try
                {
                    Object tmp[] = (Object[])(Object[])XMLDocumentParser.parseXMLDocument(path, "save_settings");
                    window_length_text_field.setText((String)tmp[0]);
                    window_overlap_fraction_text_field.setText((String)tmp[1]);
                    float rate = ((Float)tmp[2]).floatValue();
                    save_window_features_check_box.setSelected(((Boolean)tmp[4]).booleanValue());
                    save_overall_file_featurese_check_box.setSelected(((Boolean)tmp[5]).booleanValue());
                }
                catch(Exception e1)
                {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", 0);
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
