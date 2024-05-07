package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.example.gui.MainApplicationFrame.resourceBundle;

public class RobotCoordinatesWindow extends JInternalFrame implements PropertyChangeListener {
    private final TextArea textArea;
    private int x;
    private int y;

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
        x = newPosition.x;
        y = newPosition.y;
        textArea.setText(getTitle() + ": (" + newPosition.x + ", " + newPosition.y + ")");
    }

    public void translate(){
        setTitle(resourceBundle.getString("robotCoordinatesWindow"));
        textArea.setText(getTitle() + ": (" + x + ", " + y + ")");
    }
}