package view.common;

import model.*;
import service.*;
import service.common.Common;
import service.exception.ValidationException;
import service.validation.Validation;

import java.util.*;

public class Menu {
    public static Scanner scanner = new Scanner(System.in);

    public static Stack<String> path = new Stack<>();

    public static int showMenu(ArrayList<String> items, String menuName) {
        if (!path.contains(menuName))
            path.push(menuName);
        showMessage("************************** " + String.join(" > ", path));
        for (String item : items) {
            showMessage(item);
        }
        return GetNumberInput("Please choose a number between \" 1 \" to \" " + items.size() + " \" ",
                NumberType.INT);
    }

    public static <T> T GetNumberInput(String msg, NumberType type) {
        showMessage(msg);
        Number field = null;
        while (true) {
            try {
                switch (type) {
                    case INT:
                        field = scanner.nextInt();
                        break;
                    case BTYE:
                        field = scanner.nextByte();
                        break;
                    case LONG:
                        field = scanner.nextLong();
                        break;
                }
                return (T) field;
            } catch (InputMismatchException e) {
                showMessage(msg);
                scanner.nextLine();//clear the buffer
            }
        }
    }

    public static void callModifyMethod(Common.ModifyType type, Common service, NumberType idType) {
        callModifyMethod(service.getRelevantArrayList(), type,
                service, idType, null);
    }

    public static void callModifyMethod(ArrayList items,
                                        Common.ModifyType type,
                                        Common service, NumberType idType, PersonService.ClassName className) {
        try {
            Number id = GetNumberInput("Please enter Id ", idType);
            service.modify(items, id, type, service, className);
        } catch (NullPointerException | ValidationException ex) {
            showMessage(ex.getMessage());
        }
    }

    public boolean printSelectedItemForChange(Common common, Common.ModifyType type) {
        showMessage(common.toString());
        showMessage(String.format("do you want to %S ? y/n", type));
        return (scanner.next().equals("y"));
    }

    public void printResultOfChanges(Common.ModifyType type) {
        showMessage(String.format("\t\t\t\t %S is done!", type));
    }

    public static void showMessage(String msg) {
        System.out.println(msg);
    }

    public static void printSelectedRecordsOfSelectedList(Optional<Person> item, Common common)
            throws RuntimeException {
        if (item.isEmpty())
            showMessage("your List Is empty");
        else showMessage(common.setModel(item.get()).toString());
    }

    public static void printAllRecordsOfSelectedList(Common common)
            throws RuntimeException {
        printAllRecordsOfSelectedList(common.getRelevantArrayList(), common);
    }

    public static void printAllRecordsOfSelectedList(ArrayList items, Common common)
            throws RuntimeException {
        if (items.isEmpty())
            showMessage("your List Is empty");
        items.forEach(item ->
                showMessage(common.setModel((model.common.Common) item).toString())
        );
    }

    public <T> T getValidField(ControlType type, Map<Common.ExtraInfoKeys, Object> extraInfo) {
        String msg = (String) extraInfo.get(Common.ExtraInfoKeys.MSG);
        String err = (String) extraInfo.get(Common.ExtraInfoKeys.ERR);
        err = err == null ? "" : err;
        showMessage("Please enter " + msg);
        while (true) {
            try {
                switch (type) {
                    case DATE:
                        return (T) (Validation.validDate(scanner.next(), (String) extraInfo.get(Common.ExtraInfoKeys.COMPARE_VAL),
                                (Long) extraInfo.get(Common.ExtraInfoKeys.CURRENT_ID),
                                (List<model.common.Common>) extraInfo.get(Common.ExtraInfoKeys.CURRENT_RECORDS),
                                (Boolean) extraInfo.get(Common.ExtraInfoKeys.IS_BIRTH_DATE)
                        ));
                    case STRING:
                        return (T) Validation.validStringField(scanner.next(),
                                extraInfo.get(Common.ExtraInfoKeys.CURRENT_ID),
                                (List<model.common.Common>) extraInfo.get(Common.ExtraInfoKeys.CURRENT_RECORDS));
                    case RATE:
                        return (T) (Validation.validRateField(scanner.nextDouble()));
                    case NUMBER:
                        return (T) (Validation.validNumberField(scanner.nextLong()));
                    case QUANTITY:
                        return (T) (Validation.validQuantityField(scanner.nextInt(), (Integer) extraInfo.get(Common.ExtraInfoKeys.COMPARE_VAL),
                                (Byte) extraInfo.get(Common.ExtraInfoKeys.CURRENT_ID),
                                (List<InsuredGroup>) extraInfo.get(Common.ExtraInfoKeys.CURRENT_RECORDS)));
                    case PERCENT:
                        return (T) (Validation.validPercentField(scanner.nextByte()));
                    case AGE:
                        return (T) (Validation.validAgeField(scanner.nextByte(),
                                (Byte) extraInfo.get(Common.ExtraInfoKeys.COMPARE_VAL),
                                (Byte) extraInfo.get(Common.ExtraInfoKeys.CURRENT_ID),
                                (List<AgeGroup>) extraInfo.get(Common.ExtraInfoKeys.CURRENT_RECORDS)));
                    case TYPE:
                        return (T) (Validation.validType(scanner.nextInt()));
                    case NATIONAL_CODE:
                        return (T) (Validation.validNationalCode(scanner.next(), (Long) extraInfo.get(Common.ExtraInfoKeys.CURRENT_ID),
                                (List<Person>) extraInfo.get(Common.ExtraInfoKeys.CURRENT_RECORDS)));
                    case POSTAL_CODE:
                        return (T) (Validation.validPostalCode(scanner.next()));
                }

            } catch (ValidationException e) {
                showMessage(e.getMessage());
                showMessage("please try again" + err);
            } catch (InputMismatchException e) {
                showMessage("your input is not valid, please try again");
                scanner.nextLine();//clear the buffer
            }
        }
    }

    public enum ControlType {
        DATE, STRING, NUMBER, PERCENT, QUANTITY, RATE, AGE, TYPE, NATIONAL_CODE, POSTAL_CODE;
    }

    public enum NumberType {
        INT, BTYE, LONG;
    }
}
