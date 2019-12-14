package service.file;

import service.annotation.DataTablesInfo;
import service.common.Common;
import view.common.Menu;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadWrite {
    private static String directory = "";

    public static Menu menu = new Menu();

    private static void setPath() {
        Map<service.common.Common.ExtraInfoKeys, Object> extraInfoForValidationMethod = new HashMap<>();
        extraInfoForValidationMethod.put(service.common.Common.ExtraInfoKeys.MSG, "enter directory path");
        directory = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
    }

    private static void createDirectory() {
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
    }

    private static <T> void writeToFile(ArrayList<T> items, String fileName) {
        createDirectory();
        if (items.size() > 0) {
            File file = new File(directory + "\\" + fileName + ".txt");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(items);
            } catch (IOException e) {
                menu.showMessage(e.getMessage());
            }
        }
    }

    private static void setIdInService(Common service, ArrayList items) {
        try {
            Field field = service.getClass().getDeclaredField("id");
            field.setAccessible(true);
            Number newId = (Number) ((model.common.Common) items.get(items.size() - 1)).getId();
            field.set(service, newId);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            menu.showMessage(e.getMessage());
        }
    }

    private static <T> ArrayList<T> readFromFile(String fileName, Common service) {
        File file = new File(directory + "\\" + fileName + ".txt");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<T> items = (ArrayList<T>) ois.readObject();
            setIdInService(service, items);
            return items;
        } catch (IOException | ClassNotFoundException e) {
            menu.showMessage(e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void readOrWriteFile(int fileAction) {
        setPath();
        Class aClass = Common.class;
        for (Field field : aClass.getDeclaredFields()) {
            Annotation[] annotations = field.getAnnotationsByType(DataTablesInfo.class);
            for (Annotation annotation : annotations) {
                DataTablesInfo dataTablesInfo = ((DataTablesInfo) annotation);
                Class bClass = null;
                try {
                    bClass = Class.forName(dataTablesInfo.serviceName().getName());
                    Common service = null;
                    service = (Common) bClass.getDeclaredConstructor().newInstance();
                    field.setAccessible(true);
                    String fileName = dataTablesInfo.className().getSimpleName();
                    if (fileAction == 2)
                        writeToFile((ArrayList<model.common.Common>) field.get(menu), fileName);
                    else
                        field.set(service, readFromFile(fileName, service));
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException |
                        InstantiationException | InvocationTargetException e) {
                    menu.showMessage(e.getMessage());
                }
            }
        }
    }
}
