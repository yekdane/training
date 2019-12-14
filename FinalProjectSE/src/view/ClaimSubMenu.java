package view;

import service.ClaimService;
import service.PersonService;
import service.exception.ValidationException;
import view.common.Menu;

import java.util.ArrayList;

public class ClaimSubMenu extends Menu {
    private static ArrayList<String> claimSubMenuItem = new ArrayList();

    static {

        /*************************************/
        claimSubMenuItem.add("1: Add");
        claimSubMenuItem.add("2: search Claim By National Code");
        claimSubMenuItem.add("3: Back");
    }

    public static void manageMainMenu() {
        int userChoice;
        ClaimService claimService = new ClaimService();
        try {
            WhileLabel:
            {
                while (true) {
                    userChoice = showMenu(claimSubMenuItem, ClaimSubMenu.class.getSimpleName());
                    switch (userChoice) {
                        case 1:
                            claimService.add();
                            showMessage(claimService.toString());
                            break;
                        case 2:
                            showMessage("Please enter national code:");
                            printAllRecordsOfSelectedList(
                                    claimService.searchByNationalCode(
                                            (new PersonService()).searchByNationalCode( scanner.next())
                                    ), claimService);
                            break;
                        case 3:
                            break WhileLabel;
                        default:
                            showMessage("\t\t\t\t\t\t Your number is not correct Please try again");
                            break;
                    }
                }
            }
        } catch (RuntimeException | ValidationException e) {
            System.out.println(e.getMessage());
        }
        path.pop();
    }
}
