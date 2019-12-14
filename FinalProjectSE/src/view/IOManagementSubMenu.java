package view;

import service.PersonService;
import service.file.ReadWrite;
import view.common.Menu;

import java.util.ArrayList;

public class IOManagementSubMenu extends Menu {
    private static ArrayList<String> iOManagementSubMenuItem = new ArrayList();

    static {

        /*************************************/
        iOManagementSubMenuItem.add("1: read from file");
        iOManagementSubMenuItem.add("2: write to file");
        iOManagementSubMenuItem.add("3: Back");
    }

    public static void manageMainMenu() {
        int userChoice;
        WhileLabel:
        {
            while (true) {
                userChoice = showMenu(iOManagementSubMenuItem, IOManagementSubMenu.class.getSimpleName());
                switch (userChoice) {
                    case 1:
                    case 2:
                        ReadWrite.readOrWriteFile(userChoice);
//                        printAllRecordsOfSelectedList(
//                                personArrayList,new PersonService());
                        break;
                    case 3:
                        break WhileLabel;
                    default:
                        showMessage("\t\t\t\t\t\t Your number is not correct Please try again");
                        break;
                }
            }
        }
        path.pop();
    }
}
