package org.example.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import static org.example.gui.MainApplicationFrame.resourceBundle;

public class GameWindow extends JInternalFrame
{
    public GameWindow(GameVisualizer m_visualizer)
    {
        super("Игровое поле", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void translate(){
        setTitle(resourceBundle.getString("gameWindow"));
    }

}
