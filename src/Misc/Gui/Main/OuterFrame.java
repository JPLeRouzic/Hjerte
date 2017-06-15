package Misc.Gui.Main;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.Action;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JMenuBar;
import Misc.Gui.Controller.Control;
import javax.swing.JFrame;

public class OuterFrame extends JFrame
{
    static final long serialVersionUID = 1L;
    public MainPanel mainPanel;
    public Control c;
    public JMenuBar menu;
    
    public OuterFrame(final Control controller) {
        this.setTitle("File managing GUI");
        this.setDefaultCloseOperation(3);
        this.c = controller;
        final Color blue = new Color(0.75f, 0.85f, 1.0f);
        this.getContentPane().setBackground(blue);
        (this.menu = new JMenuBar()).setBackground(blue);
        final JMenu fileMenu = new JMenu("File");
        fileMenu.add(this.c.saveAction);
        fileMenu.add(this.c.loadAction);
        fileMenu.addSeparator();
        fileMenu.addSeparator();
        fileMenu.add(this.c.exitAction);
        final JMenu recordingMenu = new JMenu("Recording");
        recordingMenu.add(this.c.addRecordingsAction);
        recordingMenu.add(this.c.viewFileInfoAction);
        final JMenu helpMenu = new JMenu("About");
        helpMenu.add(this.c.aboutAction);
        this.menu.add(fileMenu);
        this.menu.add(recordingMenu);
        this.menu.add(helpMenu);
        this.setLayout(new BorderLayout(8, 8));
        this.add(this.menu, "North");
    }
    
    public void featureSelectorPanel() {
        (this.mainPanel = new MainPanel(this)).setBackground(Color.blue);
        this.add(this.mainPanel, "West");
        this.pack();
        this.setVisible(true);
    }
}
