package org.example.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public final class ConditionOfWindow {
    /**
     * Метод, сохраняющий размеры окна
     */
    public static Map<String, String> save(JComponent window, String nameOfWindow){
        Map<String, String> map = new HashMap<>();
        map.put(nameOfWindow + ".width", Integer.toString(Math.max(window.getSize().width, 100)));
        map.put(nameOfWindow + ".height", Integer.toString(Math.max(window.getSize().height, 100)));
        map.put(nameOfWindow + ".state", String.valueOf(!window.isDisplayable()));
        map.put(nameOfWindow + ".x", String.valueOf(Math.max(window.getX(), 0)));
        map.put(nameOfWindow + ".y", String.valueOf(Math.max(window.getY(), 0)));
        System.out.println(map);
        return map;
    }

    /**
     * Метод, при открытии восстанавливающий размеры окна, которые были при закрытии приложения
     */
    public static String[] recover(String nameOfWindow, Map<String, String> map){
        return new String[]{map.get(nameOfWindow + ".width"), map.get(nameOfWindow + ".height"), map.get(nameOfWindow + ".state"), map.get(nameOfWindow + ".x"), map.get(nameOfWindow + ".y")};
    }
}
