package org.example.gui;

import org.example.log.LogChangeListener;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements ConditionOfWindow
{
    private final GameVisualizer m_visualizer;
    public GameWindow() 
    {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    /**
     * Метод, сохраняющий размеры окна
     */
    public Map<String, String> save(String nameOfWindow, Boolean windowMinimized, Integer x, Integer y){
        Map<String, String> map = new HashMap<>();
        map.put(nameOfWindow + ".width", Integer.toString(m_visualizer.getSize().width));
        map.put(nameOfWindow + ".height", Integer.toString(m_visualizer.getSize().height));
        map.put(nameOfWindow + ".state", Boolean.toString(windowMinimized));
        map.put(nameOfWindow + ".x", Integer.toString(x));
        map.put(nameOfWindow + ".y", Integer.toString(y));
        return map;
    };

    /**
     * Метод, при открытии восстанавливающий размеры окна, которые были при закрытии приложения
     */
    public String[] recover(String nameOfWindow, Map<String, String> map){
        return new String[]{map.get(nameOfWindow + ".width"), map.get(nameOfWindow + ".height"), map.get(nameOfWindow + ".state"), map.get(nameOfWindow + ".x"), map.get(nameOfWindow + ".y")};
    }
}
