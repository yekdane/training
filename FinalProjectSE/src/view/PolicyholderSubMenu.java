package view;

import model.Policyholder;
import service.PersonService;
import service.PolicyholderService;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;

public class PolicyholderSubMenu extends Menu {
    private static ArrayList<String> policyholderSubMenuItem = new ArrayList();

    static {

        /*************************************/
        policyholderSubMenuItem.add("1: Add");
        policyholderSubMenuItem.add("2: Edit");
        policyholderSubMenuItem.add("3: Delete");
        policyholderSubMenuItem.add("4: Show Legal Policyholder");
        policyholderSubMenuItem.add("5: Show Natural Policyholder");
        policyholderSubMenuItem.add("6: Back");
    }

    public static void manageMainMenu() {
        int userChoice;
        PolicyholderService policyholderService = new PolicyholderService();
        WhileLabel:
        {
            while (true) {
                userChoice = showMenu(policyholderSubMenuItem, PolicyholderSubMenu.class.getSimpleName());
                switch (userChoice) {
                    case 1:
                        policyholderService.add();
                        break;
                    case 2:
                    case 3:
                        callModifyMethod((new PersonService()).getRelevantArrayList(), userChoice == 2 ? Common.ModifyType.EDIT : Common.ModifyType.DELETE,
                                policyholderService, NumberType.LONG,
                                PersonService.ClassName.POLICYHOLDER );
                        break;
                    case 4:
                    case 5:
                        Policyholder.PolicyholderType type = userChoice == 4 ?
                                Policyholder.PolicyholderType.LEGAL :
                                Policyholder.PolicyholderType.NATURAL;

                        printAllRecordsOfSelectedList(policyholderService.showByType( type),
                                policyholderService);
                        break;
                    case 6:
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
