package view;

import model.AgeGroup;
import service.AgeGroupService;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;

public class AgeGroupSubMenu extends Menu {
    private static ArrayList<String> ageGroupSubMenuItem = new ArrayList();

    static {

        /*************************************/
        ageGroupSubMenuItem.add("1: Add");
        ageGroupSubMenuItem.add("2: Delete");
        ageGroupSubMenuItem.add("3: ShowAll");
        ageGroupSubMenuItem.add("4: Back");
    }

    public static void manageMainMenu() {
        int userChoice;
        AgeGroupService ageGroupService = new AgeGroupService();
        WhileLabel:
        {
            while (true) {
                userChoice = showMenu(ageGroupSubMenuItem, AgeGroupSubMenu.class.getSimpleName());
                switch (userChoice) {
                    case 1:
                        ageGroupService.add();
                        break;
                    case 2:
                        callModifyMethod(Common.ModifyType.DELETE,
                                ageGroupService, NumberType.BTYE);
                        break;
                    case 3:
                        printAllRecordsOfSelectedList(ageGroupService);
                        break;
                    case 4:
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
