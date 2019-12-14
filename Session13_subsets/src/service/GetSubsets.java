package service;

import java.io.*;
import java.util.Optional;

public class GetSubsets {
    private String targetPath;
    private CreateFile targetFile;

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        targetFile = new CreateFile(targetPath);
    }

    public void getAllSubsets(String currentDir, int level) throws IOException {
        File sourceDir = new File(currentDir);
        for (File subFile : sourceDir.listFiles()) {
            targetFile.writeIntoFile("\t".repeat(level) + subFile.getName() + "\r\n");
            if (subFile.isDirectory())
                getAllSubsets(subFile.getAbsolutePath(), level + 1);
        }
    }

    public void getFirstLevelSubsets(String currentDir) throws IOException {
        File sourceDir = new File(currentDir);
        for (String subFile : sourceDir.list()) {
            targetFile.writeIntoFile(subFile + "\r\n");
        }
    }

    //----------------------------------------------//
    private String getExtension(String fileName) {
        Optional<String> optional = Optional.ofNullable(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf('.') + 1));
        return optional.isPresent() ? optional.get() : "";
    }

    public void readAndWriteFileContent(File file) throws IOException {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(fileReader);
        ) {
            char[] arrayChar = new char[1024];
            int length;
            //targetFile.writeIntoFile(String.format("\r\n fileName = %s \r\n", file.getName()));
            while ((length = buffer.read(arrayChar)) != -1) {
                targetFile.writeIntoFile(new String(arrayChar, 0, length));
            }
        }
    }

    public void getContentOfFirstLevelSubsets(String currentDir) throws IOException {
        File sourceDir = new File(currentDir);
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile()
                        &&
                        getExtension(file.getName()).equals("txt");
            }
        };
        for (File subFile : sourceDir.listFiles(filter)) {
            readAndWriteFileContent(subFile);
        }
    }
}
