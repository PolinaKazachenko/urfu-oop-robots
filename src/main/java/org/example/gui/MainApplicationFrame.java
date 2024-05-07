package org.example.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import org.example.log.Logger;

import static javax.swing.text.StyleConstants.setIcon;


public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final WorkingWithFile workingWithFile = new WorkingWithFile();
    private List<JComponent> windows = new ArrayList<>();
    private GameModel gameModel = new GameModel();
    private GameVisualizer gameVisualizer = new GameVisualizer(gameModel);
    private GameController gameController = new GameController(gameModel, gameVisualizer);
    protected static ResourceBundle resourceBundle;

    private JMenu lookAndFeelMenu;
    private JMenu testMenu;
    private JMenu closeMenu;
    private JMenu languageMenu;
    private JMenuItem systemLookAndFeel;
    private JMenuItem crossplatformLookAndFeel;
    private JMenuItem addLogMessageItem;
    private JMenuItem exit;
    private LogWindow logWindow;
    private GameWindow gameWindow;
    private RobotCoordinatesWindow robotCoordinatesWindow;

    public MainApplicationFrame() throws PropertyVetoException {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);

        setContentPane(desktopPane);

        logWindow = createLogWindow();
        logWindow.setName("log");
        logWindow = (LogWindow) ConditionOfWindow.recover(workingWithFile.readCondition(logWindow), logWindow);
        windows.add(logWindow);
        addWindow(logWindow);


        gameWindow = new GameWindow(gameVisualizer);
        gameWindow.setName("game");
        gameWindow = (GameWindow) ConditionOfWindow.recover(workingWithFile.readCondition(gameWindow), gameWindow);
        windows.add(gameWindow);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        robotCoordinatesWindow = new RobotCoordinatesWindow();
        robotCoordinatesWindow.setVisible(true);
        robotCoordinatesWindow.setName("coordinates");
        robotCoordinatesWindow = (RobotCoordinatesWindow) ConditionOfWindow.recover(workingWithFile.readCondition(robotCoordinatesWindow), robotCoordinatesWindow);
        windows.add(robotCoordinatesWindow);
        addWindow(robotCoordinatesWindow);

        gameModel.setPropertyChangeListener(robotCoordinatesWindow);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow(windows);
            }
        });
    }

    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }

    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }

        closeMenu = new JMenu("Меню");
        exit = new JMenuItem("Выход");
        closeMenu.add(exit).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow(windows);
            }
        });

        languageMenu = new JMenu("Язык");
        languageMenu.add(new JMenuItem("Русский")).addActionListener(e -> {
            resourceBundle = ResourceBundle.getBundle("text", new Locale("ru"));
            translator();
        });
        languageMenu.add(new JMenuItem("Rysskiy")).addActionListener(e -> {
            resourceBundle = ResourceBundle.getBundle("text", new Locale("en"));
            translator();
        });

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(closeMenu);
        menuBar.add(languageMenu);

        return menuBar;
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }


    /**
     * Функция для закрытия приложения. Выводится окно подтверждения выхода
     */
    protected void closeWindow(List<JComponent> windows){
        int option = JOptionPane.showConfirmDialog(null, resourceBundle.getString("exitOrNot"), resourceBundle.getString("exit"), JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            boolean append = false;
            for (JComponent window: windows){
                if (append) {
                    workingWithFile.writeCondition(ConditionOfWindow.save(window), false);
                } else {
                    workingWithFile.writeCondition(ConditionOfWindow.save(window), true);
                    append = true;
                }
            }
            this.dispose();
            System.exit(0);
        }
    }

    /**
     * Переводит названия и весь текст с русского на транслит и наоборот
     */
    private void translator(){
        lookAndFeelMenu.setText(resourceBundle.getString("lookAndFeelMenu"));
        testMenu.setText(resourceBundle.getString("testMenu"));
        closeMenu.setText(resourceBundle.getString("closeMenu"));
        languageMenu.setText(resourceBundle.getString("languageMenu"));
        systemLookAndFeel.setText(resourceBundle.getString("systemLookAndFeel"));
        crossplatformLookAndFeel.setText(resourceBundle.getString("crossplatformLookAndFeel"));
        addLogMessageItem.setText(resourceBundle.getString("addLogMessageItem"));
        exit.setText(resourceBundle.getString("exit"));
        logWindow.translate();
        gameWindow.translate();
        robotCoordinatesWindow.translate();
    }
}
