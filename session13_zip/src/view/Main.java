package view;

import service.CreateFile;
import service.ZipFile;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Please enter file path to ZIP");
            ZipFile zipFile=new ZipFile(scanner.next());
            zipFile.zipCurrentFile();
            System.out.println("Please enter file path to save zip file");
            CreateFile newFile=new CreateFile(scanner.next());
            newFile.writeIntoFile(zipFile.getZipContain());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
