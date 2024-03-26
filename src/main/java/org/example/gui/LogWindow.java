package org.example.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.example.log.LogChangeListener;
import org.example.log.LogEntry;
import org.example.log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener, ConditionOfWindow
{
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) 
    {
        super("Протокол работы", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    /**
     * Метод, сохраняющий размеры окна
     */
    public Map<String, String> save(String nameOfWindow, Boolean windowMinimized, Integer x, Integer y){
        Map<String, String> map = new HashMap<>();
        map.put(nameOfWindow + ".width", Integer.toString(m_logContent.getSize().width));
        map.put(nameOfWindow + ".height", Integer.toString(m_logContent.getSize().height));
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
