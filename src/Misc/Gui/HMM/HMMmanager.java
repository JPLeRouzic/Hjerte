package Misc.Gui.HMM;

import Misc.Gui.Main.EntryPoint;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
//  class

public class HMMmanager extends JFrame {
    //  four buttons, four fields, four panels, and text area

    private JButton addHMMButton, displayHMMsButton, findHMMButton, deleteHMMButton;
    private JLabel hmmNameLabel;
    private JTextField hmmNameField;
    private JPanel fieldPanelHMM;
    private JTextArea textArea;
    private static JFileChooser file_chooser;

    // constructor
    public HMMmanager() {

        super("HMM Manager");

        // hmmName name label and field
        hmmNameLabel = new JLabel("HMM Title ");
        hmmNameField = new JTextField();
        hmmNameField.setPreferredSize(new Dimension(180, 15));
        fieldPanelHMM = new JPanel();
        fieldPanelHMM.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelHMM.add(hmmNameLabel);
        fieldPanelHMM.add(hmmNameField);

        //  four buttons
        addHMMButton = new JButton(" Save HMM ");
        displayHMMsButton = new JButton(" Display HMMs ");
        findHMMButton = new JButton(" Load HMM ");
        deleteHMMButton = new JButton(" Delete HMM ");

        // first two buttons
        JPanel fieldPanelButtonsA = new JPanel();
        fieldPanelButtonsA.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelButtonsA.add(addHMMButton);
        fieldPanelButtonsA.add(displayHMMsButton);

        // second two buttons
        JPanel fieldPanelButtonsB = new JPanel();
        fieldPanelButtonsB.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelButtonsB.add(findHMMButton);
        fieldPanelButtonsB.add(deleteHMMButton);

        //  Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));
        panel.add(fieldPanelHMM);
        panel.add(fieldPanelButtonsA);
        panel.add(fieldPanelButtonsB);

        // field panel
        JPanel fieldPanel = new JPanel();
        textArea = new JTextArea("", 50, 30);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        // 
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // 
        fieldPanel.add(panel, BorderLayout.NORTH);
        fieldPanel.add(scroll, BorderLayout.CENTER);

        this.setContentPane(fieldPanel);

        // handler
        ButtonHandler handler = new ButtonHandler();
        addHMMButton.addActionListener(handler);
        displayHMMsButton.addActionListener(handler);
        findHMMButton.addActionListener(handler);
        deleteHMMButton.addActionListener(handler);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    // button handler class
    private class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String hmmName = hmmNameField.getText();

            if (event.getActionCommand().contentEquals(" Load HMM ")) {
                loadHMMProxy();
            } else if (event.getActionCommand().contentEquals(" Save HMM ")) {
                saveHMMProxy(hmmName);
            } else {
            }
        }
    }

    public void saveHMMProxy(String hmmName) {
        String path = null;
        if (this.file_chooser == null) {
            (this.file_chooser = new JFileChooser()).setCurrentDirectory(new File("."));
        }
        final int dialog_result = this.file_chooser.showOpenDialog(null);
        if (dialog_result == 0) {
            FileWriter fw = null;
            try {
                File to_save_to = this.file_chooser.getSelectedFile();
                path = to_save_to.getPath();
                final File dest = new File(path);
                fw = new FileWriter(dest);
                SaveHMM.saveHMM(hmmName, fw);
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(HMMmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void loadHMMProxy() {
        String path = null;
        if (file_chooser == null) {
            (file_chooser = new JFileChooser()).setCurrentDirectory(new File("."));
        }
        final int dialog_result = file_chooser.showOpenDialog(null);
        if (dialog_result == 0) {
            FileReader fr = null;
            try {
                File to_load = file_chooser.getSelectedFile();
                path = to_load.getPath();
                final File dest = new File(path);
                fr = new FileReader(dest);
                LoadHMM.loadHMM(fr);
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(HMMmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void showHMM() {
        Collection values = EntryPoint.hmmTrain.transitionsProbs.values();
        Set keys = EntryPoint.hmmTrain.transitionsProbs.keySet();
        Object[] valuesArray = values.toArray();
        Object[] valuesKeys = keys.toArray();
        for (int yuu = 0; yuu < values.size(); yuu++) {

            textArea.append(valuesKeys[yuu].toString());

            textArea.append(" " + valuesArray[yuu].toString());
            textArea.append("   \n");
        }
    }
}
