package view;

import service.CreateFile;
import service.UnZip;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Please enter ZIP file path to extract ");
            UnZip unZipFile = new UnZip(scanner.next());
            unZipFile.unZipCurrentFile();
            System.out.println("Please enter file path to save zip file");
            CreateFile newFile = new CreateFile(scanner.next());
            newFile.writeIntoFile(unZipFile.getUnZipContain());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
