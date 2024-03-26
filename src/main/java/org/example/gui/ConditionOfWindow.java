package org.example.gui;

import java.util.Map;

public interface ConditionOfWindow {
    public Map<String, String> save(String nameOfWindow, Boolean windowMinimized, Integer x, Integer y);
    public String[] recover(String nameOfWindow, Map<String, String> map);
}
