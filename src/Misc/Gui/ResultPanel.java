package Misc.Gui;

import ML.Classify.Classify;
import Misc.Gui.Controller.Control;
import Misc.Gui.Controller.FilesTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public final class ResultPanel extends JPanel
        implements ActionListener {

    static final long serialVersionUID = 1L;
    public OuterFrame outer_frame;
    public JPanel result_panel;
    private JScrollPane recordings_scroll_pane;

    private final Control controll;

    public ResultPanel(OuterFrame aThis) {
        outer_frame = aThis;
        controll = EntryPoint.controller;
        Color blue = new Color(0.75F, 0.85F, 1.0F);
        int horizontal_gap = 6;
        int vertical_gap = 11;
        setLayout(new BorderLayout(horizontal_gap, vertical_gap));
        add(new JLabel("Results:"), "North");
        result_panel = new JPanel();
        /* example
        JLabel jlabel = new JLabel("This is a label");
        jlabel.setFont(new Font("Verdana",1,20));    
        result_panel.add(jlabel);
        add(result_panel, "Center");
         */
        JPanel button_panel = new JPanel(new GridLayout(4, 2, horizontal_gap, vertical_gap));
        button_panel.setBackground(blue);

        button_panel.add(new JLabel(""));
        add(button_panel, "South");

        addTableMouseListener();
    }

    public void actionPerformed(ActionEvent event) {

    }

    public void addTableMouseListener() {

    }
}
