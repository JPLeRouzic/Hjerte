package Misc.Gui.HMM;

import Misc.Gui.Main.EntryPoint;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.Set;
// class

public class HMMmanager extends JFrame {
    // four buttons, four fields, four panels, and text area

    private JButton addHMMButton, displayHMMsButton, findHMMButton, deleteHMMButton;
    private JLabel authorLabel, hmmNameLabel, portFolioLabel, trackUsageLabel;
    private JTextField authorField, hmmNameField, portFolioField, trackUsageField;
    private JPanel fieldPanelAuthor, fieldPanelHMM, fieldPanelPortFolio, fieldPanelHMMUsage;
    private JTextArea textArea;

    //constructor
    public HMMmanager() {

        super("HMM Manager");

        // author name label and field
        authorLabel = new JLabel("Author Name ");
        authorField = new JTextField();
        authorField.setPreferredSize(new Dimension(180, 15));
        fieldPanelAuthor = new JPanel();
        fieldPanelAuthor.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelAuthor.add(authorLabel);
        fieldPanelAuthor.add(authorField);

        //hmmName name label and field
        hmmNameLabel = new JLabel("HMM Title ");
        hmmNameField = new JTextField();
        hmmNameField.setPreferredSize(new Dimension(180, 15));
        fieldPanelHMM = new JPanel();
        fieldPanelHMM.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelHMM.add(hmmNameLabel);
        fieldPanelHMM.add(hmmNameField);

        // portFolio name label and field
        portFolioLabel = new JLabel("PortFolio Name ");
        portFolioField = new JTextField();
        portFolioField.setPreferredSize(new Dimension(180, 15));
        fieldPanelPortFolio = new JPanel();
        fieldPanelPortFolio.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelPortFolio.add(portFolioLabel);
        fieldPanelPortFolio.add(portFolioField);

        // track length name label and field
        trackUsageLabel = new JLabel("HMM usage");
        trackUsageField = new JTextField("", 16);
        portFolioField.setPreferredSize(new Dimension(180, 15));
        fieldPanelHMMUsage = new JPanel();
        fieldPanelHMMUsage.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelHMMUsage.add(trackUsageLabel);
        fieldPanelHMMUsage.add(trackUsageField);

        // four buttons
        addHMMButton = new JButton(" Add HMM ");
        displayHMMsButton = new JButton(" Display HMMs ");
        findHMMButton = new JButton(" Find HMM ");
        deleteHMMButton = new JButton(" Delete HMM ");

        //first two buttons
        JPanel fieldPanelButtonsA = new JPanel();
        fieldPanelButtonsA.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelButtonsA.add(addHMMButton);
        fieldPanelButtonsA.add(displayHMMsButton);

        //second two buttons
        JPanel fieldPanelButtonsB = new JPanel();
        fieldPanelButtonsB.setLayout(new GridLayout(1, 2, 5, 5));
        fieldPanelButtonsB.add(findHMMButton);
        fieldPanelButtonsB.add(deleteHMMButton);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));
        panel.add(fieldPanelAuthor);
        panel.add(fieldPanelHMM);
        panel.add(fieldPanelPortFolio);
        panel.add(fieldPanelHMMUsage);
        panel.add(fieldPanelButtonsA);
        panel.add(fieldPanelButtonsB);

        //field panel
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

        //handler
        ButtonHandler handler = new ButtonHandler();
        addHMMButton.addActionListener(handler);
        displayHMMsButton.addActionListener(handler);
        findHMMButton.addActionListener(handler);
        deleteHMMButton.addActionListener(handler);
    }

    //button handler class
    private class ButtonHandler implements ActionListener {
        // override action performed

        @Override
        public void actionPerformed(ActionEvent event) {
            int length = 0;
            String author = authorField.getText();
            String portFolio = portFolioField.getText();
            String hmmName = hmmNameField.getText();

            //add HMM button
            if (event.getActionCommand().contentEquals(" Add HMM ")) {

                //exception handling
                try {
                    length = Integer.parseInt(trackUsageField.getText());
                } catch (Exception e) {
                    textArea.setText("HMM Usage must be integer.");
                    return;
                }

                // if length is not positive
                if (length <= 0) {
                    textArea.setText("HMM Usage must be positive.");
                    return;
                }

                //author field is empty
                if (author.trim().length() == 0) {
                    textArea.setText("Please fill out complete information. \nAuthor Field must be not empty.");
                    return;
                }

                //portFolio field is empty
                if (portFolio.trim().length() == 0) {
                    textArea.setText("Please fill out complete information. \nPortFolio Field must be not empty.");
                    return;
                }

                //hmmName field is empty
                if (hmmName.trim().length() == 0) {
                    textArea.setText("Please fill out complete information. \nHMM Field must be not empty.");
                    return;
                }

                HMMrecord list = new HMMrecord(author, hmmName, portFolio, length);
                textArea.setText(list.toString());
            }
        }
    }

    public void showHMM() {
        Collection values = EntryPoint.hmmTrain.transitionsProbs.values();
        Set keys = EntryPoint.hmmTrain.transitionsProbs.keySet();
        Object[] valuesArray = values.toArray();
        Object[] valuesKeys = keys.toArray();
        for (int yuu = 0; yuu < values.size(); yuu++) {

//            System.out.println(valuesKeys[yuu]);
            textArea.append(valuesKeys[yuu].toString());

//            System.out.println(valuesArray[yuu]);
            textArea.append(" " + valuesArray[yuu].toString());
            textArea.append("   \n") ;
        }
    }
}
