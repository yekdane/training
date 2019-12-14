package view;

import service.GetSubsets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static List<String> menuItem = new ArrayList();

    static {
        menuItem.add("1: Get all subsets");
        menuItem.add("2: Get first level subsets");
        menuItem.add("3: Get contents of all txt files");
    }

    public static int showMenu() {
        for (String item : menuItem) {
            System.out.println(item);
        }
        System.out.println("Please choose a number ");

        return scanner.nextInt();
    }

    public static void main(String[] args) {
        int userChoice = showMenu();
        if (userChoice < 1 || userChoice > 3)
            System.out.println("\t\t\t\t\t\t Your number is not correct Please try again");
        else {
            System.out.println("Please enter directory path to seek ");
            String path = scanner.next();
            System.out.println("Please enter target file path to write result ");
            GetSubsets getSubsets = new GetSubsets();
            getSubsets.setTargetPath(scanner.next());

            try {
                switch (userChoice) {
                    case 1:
                        getSubsets.getAllSubsets(path, 0);
                        break;
                    case 2:
                        getSubsets.getFirstLevelSubsets(path);
                        break;
                    case 3:
                        getSubsets.getContentOfFirstLevelSubsets(path);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
