package view;

import service.InsuredGroupService;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;

public class InsuredGroupSubMenu extends Menu {
    private static ArrayList<String> insuredGroupSubMenuItem = new ArrayList();

    static {

        /*************************************/
        insuredGroupSubMenuItem.add("1: Add");
        insuredGroupSubMenuItem.add("2: Edit");
        insuredGroupSubMenuItem.add("3: Delete");
        insuredGroupSubMenuItem.add("4: Back");
    }

    public static void manageMainMenu() {
        int userChoice;
        InsuredGroupService insuredGroupService = new InsuredGroupService();
        WhileLabel:
        {
            while (true) {
                userChoice = showMenu(insuredGroupSubMenuItem, InsuredGroupSubMenu.class.getSimpleName());
                switch (userChoice) {
                    case 1:
                        insuredGroupService.add();
                        break;
                    case 2:
                    case 3:
                        callModifyMethod(userChoice == 2 ? Common.ModifyType.EDIT : Common.ModifyType.DELETE,
                                insuredGroupService, NumberType.BTYE);
                        break;
                    case 4:
//                        for (InsuredGroup aa : insuredGroupArrayList) {
//                            System.out.println(insuredGroupService.setInsuredGroupModel(aa).toString());
//                        }
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
