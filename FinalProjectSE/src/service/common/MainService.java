package service.common;

import model.Person;
import service.exception.ValidationException;
import view.common.Menu;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public abstract class MainService<T> extends Common<T> {

    public static int getAge(String inputDate) throws ValidationException {
        LocalDate birthDate = null;
        birthDate = convertToMilady(inputDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(birthDate,
                (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears();
    }

    public ArrayList<model.common.Common> getShowSelectedList(ArrayList retList, String className) {
        return null;
    }

    public <T> T getSelectedList(ArrayList formerModel,
                                 Common service,
                                 Menu.NumberType idType,
                                 boolean multiSelect, String className) {
        return getSelectedList(formerModel, service.getRelevantArrayList(), service, idType, multiSelect, className);
    }

    /**
     * in this method at the first you can see all records that you don't chose former
     * then you can enter "Id" from list to add to your list
     *
     * @param formerModel it's you list and it can be NULL if you just want to choose one record
     */
    public <T> T getSelectedList(ArrayList formerModel,
                                 ArrayList availableModel,
                                 Common service,
                                 Menu.NumberType idType,
                                 boolean multiSelect, String className) {
        while (true) {
            ArrayList<model.common.Common> retList =
                    (ArrayList<model.common.Common>) availableModel.stream()
                            .filter(item -> item.getClass().getSimpleName().equals(className)  //for separate Insured Person And Policyholder
                                            && !(multiSelect && formerModel.contains(item)
                                    )
                            ).collect(Collectors.toList());
            //extra filter
            if (multiSelect) {
                retList = getShowSelectedList(retList, className);
            }
            if (retList.size() == 0) {
                // get insuredPerson, obligation and ageGroup list or policyholder id
                if (className != Person.class.getSimpleName() && (!multiSelect || formerModel.size() == 0))
                    throw new RuntimeException("there isn't anything to select, please fill " + className);
                // there is no more record than selected records to add to obligation/ageGroup list
                if (multiSelect)
                    return (T) formerModel;
            }
            menu.printAllRecordsOfSelectedList(retList, service);
            Number input = Menu.GetNumberInput("Please choose Id from " + className + " List, or enter \"0\" to exit ", idType);
            if (input.toString().equals("0"))
                break;
            model.common.Common common = null;
            try {
                common = findObjectById(retList, input);
                if (multiSelect)
                    formerModel.add(common);
                else return (T) common;
            } catch (NullPointerException ex) {// if id is not exist in current list
                menu.showMessage(ex.getMessage());
            }
        }
        return (T) formerModel;
    }

}

