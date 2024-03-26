package org.example.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Класс-"сущность" для работы с файлом
 */
public class WorkingWithFile {
    /**
     * Запись в файл размеров окна
     */
    protected void writeCondition(Map<String, String> map, Boolean newRecord){
        File file = new File(System.getProperty("user.home") + "/window_condition.txt");
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
    protected Map<String, String> readCondition(String nameOfWindow){
        String homeDirectory = System.getProperty("user.home");
        File file = new File(homeDirectory + "/window_condition.txt");
        Map<String, String> map = new HashMap<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] value = line.split(" ");
                if (Objects.equals(value[0].split("\\.")[0], nameOfWindow))
                    map.put(value[0], value[1]);
            }
            scanner.close();
        } catch (Exception ex) {
            System.out.println("Файл не найден: " + file.getName());
        }
        return map;
    }
}
