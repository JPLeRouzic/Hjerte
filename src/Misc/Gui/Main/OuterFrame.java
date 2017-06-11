
package Misc.Gui.Main;

import Misc.Gui.Controller.Control;
import java.awt.*;
import javax.swing.*;

public class OuterFrame extends JFrame
{

    static final long serialVersionUID = 1L;
    public MainPanel mainPanel;
    public Control c;
    public JMenuBar menu;

    public OuterFrame(Control controller)
    {
        setTitle("File managing GUI");
        setDefaultCloseOperation(3);
        c = controller;
        Color blue = new Color(0.75F, 0.85F, 1.0F);
        getContentPane().setBackground(blue);
        menu = new JMenuBar();
        menu.setBackground(blue);
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(c.saveAction);
        fileMenu.add(c.loadAction);
        fileMenu.addSeparator();
        fileMenu.addSeparator();
        fileMenu.add(c.exitAction);
        JMenu recordingMenu = new JMenu("Recording");
        recordingMenu.add(c.addRecordingsAction);
        recordingMenu.add(c.viewFileInfoAction);
        JMenu helpMenu = new JMenu("About");
        helpMenu.add(c.aboutAction);
        menu.add(fileMenu);
        menu.add(recordingMenu);
        menu.add(helpMenu);
        setLayout(new BorderLayout(8, 8));
        add(menu, "North");
    }

    public void featureSelectorPanel()
    {
        mainPanel = new MainPanel(this);
        mainPanel.setBackground(Color.blue);
        add(mainPanel, "West");
        pack();
        setVisible(true);
    }
}
