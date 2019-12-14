package view;

import model.Obligation;
import service.ObligationService;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;

public class ObligationSubMenu extends Menu {
    private static ArrayList<String> obligationSubMenuItem = new ArrayList();

    static {

        /*************************************/
        obligationSubMenuItem.add("1: Add");
        obligationSubMenuItem.add("2: Delete");
        obligationSubMenuItem.add("3: Back");
    }

    public static void manageMainMenu() {
        int userChoice;
        ObligationService obligationService = new ObligationService();
        WhileLabel:
        {
            while (true) {
                userChoice = showMenu(obligationSubMenuItem, ObligationSubMenu.class.getSimpleName());
                switch (userChoice) {
                    case 1:
                        obligationService.add();
                        break;
                    case 2:
                        callModifyMethod(Common.ModifyType.DELETE,
                                obligationService, NumberType.LONG);
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
