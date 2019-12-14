package service.common;

import com.ibm.icu.text.SimpleDateFormat;
import model.*;
import service.*;
import service.annotation.DataTablesInfo;
import service.exception.ValidationException;
import view.common.Menu;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Common<T> {
    @DataTablesInfo(serviceName = PersonService.class, className = Person.class)
    protected static ArrayList<Person> personArrayList = new ArrayList();

    @DataTablesInfo(serviceName = AgeGroupService.class, className = AgeGroup.class)
    protected static ArrayList<AgeGroup> ageGroupsArrayList = new ArrayList();

    @DataTablesInfo(serviceName = InsuredGroupService.class, className = InsuredGroup.class)
    protected static ArrayList<InsuredGroup> insuredGroupArrayList = new ArrayList();

    @DataTablesInfo(serviceName = ObligationService.class, className = Obligation.class)
    protected static ArrayList<Obligation> obligationArrayList = new ArrayList();

    @DataTablesInfo(serviceName = ContractService.class, className = Contract.class)
    protected static ArrayList<Contract> contractArrayList = new ArrayList();

    @DataTablesInfo(serviceName = ClaimService.class, className = Claim.class)
    protected static ArrayList<Claim> claimArrayList = new ArrayList();

    public static final Locale FA_LOCALE = new Locale("fa", "IR");
    private static SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy/MM/dd", FA_LOCALE);
    public static Menu menu = new Menu();
    protected Map<ExtraInfoKeys, Object> extraInfoForValidationMethod = new HashMap<>();

    public void modify(ArrayList items, Number id, ModifyType type, Common service, ClassName className) throws ValidationException {
        service.setModel(null);
        model.common.Common selectedModel = findObjectById(items, id, className);
        if (menu.printSelectedItemForChange(service.setModel(selectedModel), type) &&
                actionBeforeModify(selectedModel, type)) {
            switch (type) {
                case EDIT:
                case CALCULATE:
                    edit( selectedModel);
                    break;
                case DELETE:
                    items.remove(selectedModel);
                    break;
            }
            if (!type.equals(ModifyType.ADD))
                menu.printResultOfChanges(type);
        } else {
            service.setModel(null);
        }
    }

    public abstract Common setModel(model.common.Common model);

    public abstract <L> ArrayList<L> getRelevantArrayList();

    protected void edit( model.common.Common model) throws ValidationException {

    }

    public boolean actionBeforeModify(model.common.Common model, ModifyType type) {
        return true;
    }

    public model.common.Common findObjectById(ArrayList items, Number id) {
        return findObjectById(items, id, null);
    }

    public model.common.Common findObjectById(ArrayList<model.common.Common> items, Number id, ClassName className) {
        if (items.size() == 0)
            throw new NullPointerException("there still is not list ");
        model.common.Common retObj = items.stream().filter(item -> item.getId().equals(id) &&
                (className == null || item.getClass().getSimpleName() == className.toString()))
                .findFirst().orElse(null);
        if (retObj != null)
            return retObj;
        else throw new NullPointerException("this id is not available");
    }

    public static String convertToShamsi(Date date) {
        return (dateFormat.format(date));
    }

    public static Date convertToMilady(String date) throws ValidationException {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new ValidationException(e);
        }
    }

    public String showMoneyFormat(Number money) {
        money = money == null ? 0 : money;
        return NumberFormat.getNumberInstance().format(money);
    }

    public enum ModifyType {
        ADD, EDIT, DELETE, CALCULATE;
    }

    public enum ExtraInfoKeys {
        MSG, ERR, CURRENT_ID, CURRENT_RECORDS, COMPARE_VAL, NOTNULL,IS_BIRTH_DATE;
    }
}
