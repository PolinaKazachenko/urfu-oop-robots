package org.example.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RobotDownloader extends JFrame {
    private JPanel panel;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private URLClassLoader classLoader;

    public RobotDownloader() {
        super("Robot Loader");

        panel = new JPanel();
        label = new JLabel("JAR File:");
        textField = new JTextField(20);
        button = new JButton("Load");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadJar();
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(button);

        add(panel);

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadJar() {
        String path = textField.getText();
        File file = new File(path);

        try {
            if (!file.getName().endsWith(".jar")) {
                throw new IOException("У файла должно быть расширение .jar");
            }

            URL url = file.toURI().toURL();
            classLoader = new URLClassLoader(new URL[] { url });
            Class<?> robotClass = classLoader.loadClass("Robot");
            Robot robot = (Robot) robotClass.newInstance();

        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}