package View;
import service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GenerateMenu {
    private static List<String> menuItem = new ArrayList();
    public static Scanner scanner = new Scanner(System.in);
    public static BasisTwo basisTwo = new BasisTwo();
    public static BasisEight basisEight = new BasisEight();
    public static BasisTen basisTen = new BasisTen();
    public static BasisHex basisHex = new BasisHex();

    static {
        menuItem.add("1: convert basis 10 to ( 2 , 8 , 16)");
        menuItem.add("2: convert basis 2 to ( 8 , 10 , 16)");
        menuItem.add("3: convert basis 8 to ( 2 , 10 , 16)");
        menuItem.add("4: convert basis 16 to ( 2 , 8 , 10)");
        menuItem.add("5: Finish");
    }

    public static int showMenu() {
        for (String item : menuItem) {
            System.out.println(item);
        }
        System.out.println("Please choose a number between \" 1 \" to \" 5 \" ");

        return scanner.nextInt();
    }

    public static void main(String[] args) {
        int userChoice;

        WhileLabel:
        {
            while (true) {
                try {
                    userChoice = GenerateMenu.showMenu();
                    switch (userChoice) {
                        case 1:
                            System.out.println("Enter your number to Convert: ");
                            BasisTen.setBasisTenValNumber(scanner.nextInt());
                            BasisTen.doActions();
                            break;
                        case 2:
                            System.out.println("Enter your number ('0','1') to Convert: ");
                            BasisTwo.setBasisTwoValNumber(scanner.next());
                            BasisTwo.doActions();
                            break;
                        case 3:
                            System.out.println("Enter your number ('0' to '7') to Convert: ");
                            BasisEight.setBasisEightValNumber(scanner.next());
                            BasisEight.doActions();
                            break;
                        case 4:
                            System.out.println("Enter your number ('0' to '7' or 'A' to 'Z') to Convert: ");
                            BasisHex.setBasisHexValNumber(scanner.next());
                            BasisHex.doActions();
                            break;
                        case 5:
                            System.out.println("\t\t\t\t\t\tThank you for choose this app ");
                            break WhileLabel;
                        default:
                            System.out.println("\t\t\t\t\t\t Your number is not correct Please try again");
                            break;
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }
}
