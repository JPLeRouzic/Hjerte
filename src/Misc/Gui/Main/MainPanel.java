package Misc.Gui.Main;

import ML.Classify.Classify;
import Misc.Gui.Controller.Control;
import Misc.Gui.Controller.FilesTableModel;
import Misc.Gui.HMM.HMMmanager;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.swing.*;

public final class MainPanel extends JPanel
        implements ActionListener {

    static final long serialVersionUID = 1L;
    public OuterFrame outer_frame;
    private JPanel recordings_panel;
    private JScrollPane recordings_scroll_pane;

    public JTable heart_sound_files_table;

    JButton extract_features_button;
    JButton classify_button;

    private final Control controll;

    public MainPanel(OuterFrame aThis) {
        outer_frame = aThis;
        controll = EntryPoint.controller;
        Color blue = new Color(0.75F, 0.85F, 1.0F);
        int horizontal_gap = 6;
        int vertical_gap = 11;
        setLayout(new BorderLayout(horizontal_gap, vertical_gap));
        add(new JLabel("RECORDINGS:"), "North");
        recordings_panel = null;
        setUpFilesTable();
        JPanel button_panel = new JPanel(new GridLayout(2, 2, horizontal_gap, vertical_gap));
        button_panel.setBackground(blue);

        extract_features_button = new JButton("Extract Features and train");
        button_panel.add(extract_features_button);
        extract_features_button.addActionListener(this);

        classify_button = new JButton("Classify new file with trained ML");
        button_panel.add(classify_button);
        classify_button.addActionListener(this);

        button_panel.add(new JLabel(""));
        add(button_panel, "South");

        addTableMouseListener();
        controll.removeRecordingsAction.setModel(controll, heart_sound_files_table);
        controll.viewFileInfoAction.setTable(heart_sound_files_table);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(extract_features_button)) {
            // add files to train
            controll.addRecordingsAction.addFile();
            
            // Train
            GenObs genoa = new GenObs();
            ArrayList obs = genoa.generateObsFromFiles(controll);
            ExtrFeatrsTrain.trainOnFeatures(obs);
            // At this point we can manage the trained HMM at EntryPoint.hmmTrain
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    //instance manager
                    HMMmanager manager = new HMMmanager();
                    manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    manager.setSize(new Dimension(400, 400));
                    manager.setVisible(true);

                    manager.showHMM();
                }
            });

            // Classify            
        } else if (event.getSource().equals(classify_button)) {
            Classify classify;

            // remove files
            controll.removeRecordingsAction.removeFiles();

            // add one file to classify
            controll.addRecordingsAction.addFile();

            classify = new Classify(
                    controll,
                    outer_frame,
                    controll.exfeat.recordingInfo);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    //instance manager
                    Similarity simi = new Similarity();
                    simi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    simi.setSize(new Dimension(400, 400));
                    simi.setVisible(true);
                }
            });
        }
    }

    public void addTableMouseListener() {
        heart_sound_files_table.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    viewRecordingInformation();
                }
            }
        });
    }

    private void viewRecordingInformation() {
        int selected_rows[] = heart_sound_files_table.getSelectedRows();
        for (int i = 0; i < selected_rows.length; i++);
    }

    private void setUpFilesTable() {
        if (heart_sound_files_table != null) {
            remove(heart_sound_files_table);
        }
        Object column_names[] = {
            new String("Name"), new String("Path")
        };
        int number_recordings = 0;
        controll.filesList = new FilesTableModel(column_names, number_recordings, 61);

        heart_sound_files_table = new JTable(controll.filesList);
        recordings_scroll_pane = new JScrollPane(heart_sound_files_table);

        recordings_panel = new JPanel(new GridLayout(1, 1));
        recordings_panel.add(recordings_scroll_pane);
        add(recordings_panel, "Center");
        controll.filesList.fireTableDataChanged();
        repaint();
        outer_frame.repaint();
    }
}
