package org.example.gui;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Класс-"сущность" для работы с файлом
 */
public class WorkingWithFile {
    private final String pathName  = System.getProperty("user.home") + "/window_condition.txt";
    /**
     * Запись в файл размеров окна
     */
    protected void writeCondition(Map<String, String> map, Boolean newRecord){
        File file = new File(pathName);
        try(FileWriter writer = new FileWriter(file, !newRecord))
        {
            for (String parametr: map.keySet()){
                String value = map.get(parametr);
                writer.append(parametr).append(" ").append(value).append('\n');
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Чтение из файла размеров окна
     */
    protected Map<String, String> readCondition(JComponent window){
        File file = new File(pathName);
        Map<String, String> map = new HashMap<>();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] value = line.split(" ");
                if (Objects.equals(value[0].split("\\.")[0], window.getName()))
                    map.put(value[0], value[1]);
            }
        } catch (Exception ex) {
            System.out.println("Файл не найден: " + file.getName());
        }
        return map;
    }
}
