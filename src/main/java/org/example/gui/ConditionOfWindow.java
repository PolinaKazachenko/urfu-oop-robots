package org.example.gui;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

public final class ConditionOfWindow {
    /**
     * Метод, сохраняющий размеры окна
     */
    public static Map<String, String> save(JComponent window){
        String nameOfWindow = window.getName();
        Map<String, String> map = new HashMap<>();
        map.put(nameOfWindow + ".width", Integer.toString(Math.max(window.getSize().width, 100)));
        map.put(nameOfWindow + ".height", Integer.toString(Math.max(window.getSize().height, 100)));
        map.put(nameOfWindow + ".state", String.valueOf(!window.isDisplayable()));
        map.put(nameOfWindow + ".x", String.valueOf(Math.max(window.getX(), 0)));
        map.put(nameOfWindow + ".y", String.valueOf(Math.max(window.getY(), 0)));
        return map;
    }

    /**
     * Метод, при открытии восстанавливающий размеры окна, которые были при закрытии приложения
     */
    public static JInternalFrame recover(Map<String, String> map, JInternalFrame window) throws PropertyVetoException {
        String nameOfWindow = window.getName();
        System.out.println(map+ "*****");
        if (map.get(nameOfWindow + ".width") != null) {
            window.setSize(Integer.parseInt(map.get(nameOfWindow + ".width")), Integer.parseInt(map.get(nameOfWindow + ".height")));
            window.setIcon(Boolean.parseBoolean(map.get(nameOfWindow + ".state")));
            window.setLocation(Integer.parseInt(map.get(nameOfWindow + ".x")), Integer.parseInt(map.get(nameOfWindow + ".y")));
        }
        return window;
    }
}
