package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class RobotCoordinatesWindow extends JInternalFrame {

    public RobotCoordinatesWindow(GameVisualizer gameVisualizer) {
        super("Координаты робота", true, true, true, true);
        setSize(200, 100);
        TextArea textArea = new TextArea("Координаты робота: (" + "x" + ", " + "y" + ")");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);
        getContentPane().add(panel);

        gameVisualizer.setPropertyChangeListener(evt -> {
            if ("position".equals(evt.getPropertyName())) {
                Point newPosition = (Point) evt.getNewValue();
                textArea.setText("Координаты робота: (" + newPosition.x + ", " + newPosition.y + ")");
                textArea.invalidate();
            }
        });
    }

}