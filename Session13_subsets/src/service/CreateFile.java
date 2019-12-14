package service;

import java.io.*;

public class CreateFile {
    private String path;

    public CreateFile(String path) {
        this.path = path;

    }

    private void createDirectory() {
        String directory = path.substring(0, path.lastIndexOf('\\'));
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
    }

    public void writeIntoFile(String content) throws IOException {
        createDirectory();
        try (FileWriter fileWrite = new FileWriter(path,true)) {
            fileWrite.write(content);
        }
    }
}
