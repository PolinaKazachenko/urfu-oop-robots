package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RobotCoordinatesWindow extends JInternalFrame implements PropertyChangeListener {
    private final TextArea textArea;

    public RobotCoordinatesWindow() {
        super("Координаты робота", true, true, true, true);
        setSize(200, 100);
        textArea = new TextArea("Координаты робота: (" + "x" + ", " + "y" + ")");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Point newPosition = (Point) evt.getNewValue();
        textArea.setText("Координаты робота: (" + newPosition.x + ", " + newPosition.y + ")");
    }
}