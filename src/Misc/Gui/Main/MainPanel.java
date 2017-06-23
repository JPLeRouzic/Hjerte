package Misc.Gui.Main;

import javax.swing.table.TableModel;
import Misc.Gui.Controller.FilesTableModel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import ML.Classify.Observation;
import ML.Classify.Classify;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import Misc.Gui.HMM.HMMmanager;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Color;
import Misc.Gui.Controller.Control;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public final class MainPanel extends JPanel implements ActionListener
{
    static final long serialVersionUID = 1L;
    public OuterFrame outer_frame;
    private JPanel recordings_panel;
    private JScrollPane recordings_scroll_pane;
    public JTable heart_sound_files_table;
    JButton extract_features_button;
    JButton classify_button;
    private final Control controll;
    
    public MainPanel(final OuterFrame aThis) {
        this.outer_frame = aThis;
        this.controll = EntryPoint.controller;
        final Color blue = new Color(0.75f, 0.85f, 1.0f);
        final int horizontal_gap = 6;
        final int vertical_gap = 11;
        this.setLayout(new BorderLayout(horizontal_gap, vertical_gap));
        this.add(new JLabel("RECORDINGS:"), "North");
        this.recordings_panel = null;
        this.setUpFilesTable();
        final JPanel button_panel = new JPanel(new GridLayout(2, 2, horizontal_gap, vertical_gap));
        button_panel.setBackground(blue);
        button_panel.add(this.extract_features_button = new JButton("Extract Features and train"));
        this.extract_features_button.addActionListener(this);
        button_panel.add(this.classify_button = new JButton("Classify new file with trained ML"));
        this.classify_button.addActionListener(this);
        button_panel.add(new JLabel(""));
        this.add(button_panel, "South");
        this.addTableMouseListener();
        this.controll.removeRecordingsAction.setModel(this.controll, this.heart_sound_files_table);
        this.controll.viewFileInfoAction.setTable(this.heart_sound_files_table);
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getSource().equals(this.extract_features_button)) {
            this.controll.addRecordingsAction.addFile();
            final GenObs genoa = new GenObs();
        if(this.controll.exfeat != null) {
            final ArrayList obs = genoa.generateObsFromFiles(this.controll);
            ExtrFeatrsTrain.trainOnFeatures(obs);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final HMMmanager manager = new HMMmanager();
                    manager.setDefaultCloseOperation(3);
                    manager.setSize(new Dimension(400, 400));
                    manager.setVisible(true);
                    manager.showHMM();
                }
            });
        }
        }
        else if (event.getSource().equals(this.classify_button)) {
            this.controll.removeRecordingsAction.removeFiles();
            this.controll.addRecordingsAction.addFile();
            if(this.controll.exfeat != null) {
            final Classify classify = new Classify(this.controll, this.outer_frame, this.controll.exfeat.recordingInfo);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final Similarity simi = new Similarity();
                    float score ;
                    
                    simi.textArea.setText("Similarity score (between 0 and 1)\n");
                    final String similScore = EntryPoint.hmmTest.getSimilarity();
                    score = Float.parseFloat(similScore) ;                    
                    if (score < 0.5) {
                        simi.textArea.setText("The similarity score of the tested file is too low\n");
                    } else
                    if ((score > 0.5) && (score < 0.75)) {
                        simi.textArea.setText("The tested file has some similarity to the training set\n");
                    }
                    else {
                        simi.textArea.setText("The tested file has good similarity to the training set\n");
                    }
                    simi.textArea.append("Value found: " + similScore);
                    simi.textArea.append("\n");
                    simi.textArea.append("Hear sounds found having good correlation to the training set\n");
                    final ArrayList due = EntryPoint.hmmTest.getObsrvSeq();
                    for (int idx = 0; idx < EntryPoint.hmmTest.worksWell.size(); ++idx) {
                        final String str = (String) EntryPoint.hmmTest.worksWell.get(idx);
                        final Character trez = str.charAt(1);
                        final String yon = trez.toString();
                        if (Integer.parseInt(yon) < 5) {
                            simi.textArea.append("\n");
                            simi.textArea.append(str.substring(0, 2));
                            simi.textArea.append(",    ");
                            final Observation undex = (Observation) due.get(idx);
                            simi.textArea.append(String.valueOf(undex.getRawIndex()));
                        }
                    }
                    simi.setDefaultCloseOperation(3);
                    simi.setSize(new Dimension(400, 400));
                    simi.setVisible(true);
                }
            });
            }
        }
    }
    
    public void addTableMouseListener() {
        this.heart_sound_files_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent event) {
                if (event.getClickCount() == 2) {
                    MainPanel.this.viewRecordingInformation();
                }
            }
        });
    }
    
    private void viewRecordingInformation() {
        final int[] selected_rows = this.heart_sound_files_table.getSelectedRows();
        for (int i = 0; i < selected_rows.length; ++i) {}
    }
    
    private void setUpFilesTable() {
        if (this.heart_sound_files_table != null) {
            this.remove(this.heart_sound_files_table);
        }
        final Object[] column_names = { new String("Name"), new String("Path") };
        final int number_recordings = 0;
        this.controll.filesList = new FilesTableModel(column_names, number_recordings, 61);
        this.heart_sound_files_table = new JTable(this.controll.filesList);
        this.recordings_scroll_pane = new JScrollPane(this.heart_sound_files_table);
        (this.recordings_panel = new JPanel(new GridLayout(1, 1))).add(this.recordings_scroll_pane);
        this.add(this.recordings_panel, "Center");
        this.controll.filesList.fireTableDataChanged();
        this.repaint();
        this.outer_frame.repaint();
    }
}
