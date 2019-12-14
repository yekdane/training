package view;

import service.common.Common;
import view.common.Menu;

import java.text.ParseException;
import java.util.ArrayList;

public class GenerateMenu extends Menu {
    private static ArrayList<String> menuItem = new ArrayList();
    private static PolicyholderSubMenu policyholderSubMenu;
    private static AgeGroupSubMenu ageGroupSubMenu;
    private static InsuredGroupSubMenu insuredGroupSubMenu;
    private static ObligationSubMenu obligationSubMenu;
    private static ContractSubMenu contractSubMenu;
    private static ClaimSubMenu claimSubMenu;
    private static InsurePersonSubMenu insurePersonSubMenu;
    private static IOManagementSubMenu ioManagementSubMenu;

    static {
        menuItem.add("1: Policyholder Management");
        menuItem.add("2: Age Group Management");
        menuItem.add("3: Insured Group Management");
        menuItem.add("4: Obligation Management");
        menuItem.add("5: Contract Management");
        menuItem.add("6: Claim Management");
        menuItem.add("7: Insure Person");
        menuItem.add("8: I/O Management");
        menuItem.add("9: Finish");
    }

    public static void main(String[] args) throws ParseException {
        int userChoice;

        WhileLabel:
        {
            while (true) {
                userChoice = showMenu(menuItem, "MainMenu");
                switch (userChoice) {
                    case 1:
                        policyholderSubMenu.manageMainMenu();
                        break;
                    case 2:
                        ageGroupSubMenu.manageMainMenu();
                        break;
                    case 3:
                        insuredGroupSubMenu.manageMainMenu();
                        break;
                    case 4:
                        obligationSubMenu.manageMainMenu();
                        break;
                    case 5:
                        contractSubMenu.manageMainMenu();
                        break;
                    case 6:
                        claimSubMenu.manageMainMenu();
                        break;
                    case 7:
                        insurePersonSubMenu.manageMainMenu();
                        break;
                    case 8:
                        ioManagementSubMenu.manageMainMenu();
                        break;
                    case 9:
                        break WhileLabel;
                    default:
                        showMessage("\t\t\t\t\t\t Your number is not correct Please try again");
                        break;
                }
            }
        }
        path.pop();
    }

//    public static void setIdInService(Common service, Number newId) {
//        try {
//            Field field = service.getClass().getDeclaredField("id");
//            field.setAccessible(true);
//            field.set(service, newId);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//    }
}
