package view;

import service.PersonService;
import view.common.Menu;

import java.util.ArrayList;

public class InsurePersonSubMenu extends Menu {
    private static ArrayList<String> insureSubmenuItem = new ArrayList();

    static {
        insureSubmenuItem.add("1: search By Name");
        insureSubmenuItem.add("2: Search By National Code");
        insureSubmenuItem.add("3: Back");
    }

    public static void manageMainMenu() {
        int userChoice;
        PersonService personService = new PersonService();
        WhileLabel:
        {
            while (true) {
                userChoice = showMenu(insureSubmenuItem, InsurePersonSubMenu.class.getSimpleName());
                switch (userChoice) {
                    case 1:
                        showMessage("Please enter name:");
                        printAllRecordsOfSelectedList(
                                personService.searchByName(scanner.next()),
                                personService);
                        break;
                    case 2:
                        showMessage("Please enter national code:");
                        printSelectedRecordsOfSelectedList(
                                personService.searchByNationalCode(scanner.next()),
                                personService);
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
