package view;

import model.Person;
import service.ContractService;
import service.PersonService;
import service.common.Common;
import service.exception.ValidationException;
import view.common.Menu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ContractSubMenu extends Menu {
    private static ArrayList<String> contractSubMenuItem = new ArrayList();
    private static ArrayList<String> contractInsuredSubMenuItem = new ArrayList();
    private static ContractService contractService;

    static {
        contractInsuredSubMenuItem.add("1: Add");
        contractInsuredSubMenuItem.add("2: select from insured person list");
        contractInsuredSubMenuItem.add("3: Remove");
        contractInsuredSubMenuItem.add("4: Back");
        /*************************************/
        contractSubMenuItem.add("1: New Contract");
        contractSubMenuItem.add("2: Add/Remove contract insured");
        contractSubMenuItem.add("3: Remove contract");
        contractSubMenuItem.add("4: calculate contract premium");
        contractSubMenuItem.add("5: Back");
        contractService = new ContractService();
    }

    public static void manageMainMenu() {
        int userChoice;

        try {
            WhileLabel:
            {
                while (true) {
                    userChoice = showMenu(contractSubMenuItem, ContractSubMenu.class.getSimpleName());
                    switch (userChoice) {
                        case 1:
                            contractService.add();
                            break;
                        case 2:
                            manageSubMenu();
                            break;
                        case 3:
                        case 4:
                            callModifyMethod(userChoice == 3 ? Common.ModifyType.DELETE : Common.ModifyType.CALCULATE,
                                    contractService, NumberType.LONG);
                            break;
                        case 5:
                            break WhileLabel;
                        default:
                            showMessage("\t\t\t\t\t\t Your number is not correct Please try again");
                            break;
                    }
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        path.pop();
    }

    public static void manageSubMenu() {
        int userChoice;
        printAllRecordsOfSelectedList(contractService);
        callModifyMethod(Common.ModifyType.ADD, contractService, NumberType.LONG);
        if (contractService.getInstance().getId() != null) {
            System.out.println(contractService.getInstance().getId());
            WhileLabel:
            {
                while (true) {
                    userChoice = showMenu(contractInsuredSubMenuItem, " insured Submenu");
                    switch (userChoice) {
                        case 1:
                            try {
                                contractService.addInsuredPerson();
                            } catch (ValidationException e) {
                                showMessage(e.getMessage());
                            }
                            break;
                        case 2:
                            contractService.setInsuredPersonFromList();
                            break;
                        case 3:
                            callModifyMethod(contractService.getInstance().getInsurePersonList(),
                                    Common.ModifyType.DELETE, contractService.personService,
                                    NumberType.LONG, PersonService.ClassName.INSURED_PERSON);
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
}
