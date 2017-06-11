package Misc.Gui.Main;

import javax.swing.*;
import java.awt.*;
//  class

public class Similarity extends JFrame {
    //  four buttons, four fields, four panels, and text area

    JTextArea textArea;

    // constructor
    public Similarity() {

        super("Similarity Manager");

        //  Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));

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

    }

}
