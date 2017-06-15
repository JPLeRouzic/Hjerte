package Misc.Gui.Main;

import java.awt.Container;
import java.awt.Component;
import javax.swing.JScrollPane;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFrame;

public class Similarity extends JFrame
{
    JTextArea textArea;
    
    public Similarity() {
        super("Similarity Manager");
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));
        final JPanel fieldPanel = new JPanel();
        (this.textArea = new JTextArea("", 50, 30)).setEditable(false);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        final JScrollPane scroll = new JScrollPane(this.textArea);
        scroll.setVerticalScrollBarPolicy(20);
        scroll.setHorizontalScrollBarPolicy(32);
        fieldPanel.add(panel, "North");
        fieldPanel.add(scroll, "Center");
        this.setContentPane(fieldPanel);
    }
}
