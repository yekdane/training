package service;

import model.*;
import service.common.MainService;
import service.exception.ValidationException;
import view.common.Menu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContractService extends MainService<Long> {
    private Contract contractModel;
    private static Long id = 0L;

    public PersonService personService = new PersonService();

    public ContractService() {
    }

    public Contract getInstance() {
        return contractModel;
    }

    @Override
    public ContractService setModel(model.common.Common contractModel) {
        this.contractModel = contractModel == null ? new Contract() : (Contract) contractModel;
        return this;
    }

    @Override
    public ArrayList<Contract> getRelevantArrayList() {
        return contractArrayList;
    }

    @Override
    public boolean actionBeforeModify(model.common.Common model, ModifyType type) {
        if (type == ModifyType.DELETE) {
            if (model instanceof Person)
                deleteInsuredPersonFromPersonArrayList((Person) model);
            else {// before deleting contract if insured person doesn't exist in other contract,
                // they are deleted from Person Mode too.
                for (Person person : ((Contract) model).getInsurePersonList()) {
                    deleteInsuredPersonFromPersonArrayList(person);
                }
            }
        }
        return true;
    }

    @Override
    //Calculate premium
    protected void edit(model.common.Common model)
            throws ValidationException {
        calculatePremium();
    }

    private void calculatePremium() throws ValidationException {
        Optional<Double> optionalDouble = contractModel.getObligationList().stream()
                .map(obligation -> obligation.getCeiling() * obligation.getRate())
                .reduce((a1, a2) -> a1 + a2);

        if (optionalDouble.isEmpty())
            throw new ValidationException("Obligation in this contract is empty");

        double basePrice = optionalDouble.get() / 12;
        double temp = 0;
        int age;
        ArrayList<Person> insuredPersons = contractModel.getInsurePersonList();

        for (Person insurePerson : insuredPersons) {
            age = getAge(insurePerson.getBirthDate());
            Optional<Double> rate = getRateFromAgeGroupModel(age);

            if (rate.isEmpty())
                throw new ValidationException("could not find rate for " + age + "-year-old person in AgeGroup");

            temp += (rate.get() * basePrice);
        }
        optionalDouble = getRateFromInsuredGroupModel(insuredPersons.size());
        if (optionalDouble.isEmpty())
            throw new ValidationException("could not find rate for " + insuredPersons.size() + " insured people in insuredGroup");

        temp = optionalDouble.get() * temp;
        setMonthlyPremium((long) temp);
        setTotalYearlyPremium((long) (temp * 12));
        menu.showMessage(
                String.format("for this contract \"MonthlyPremium\" is %s and \"YearlyPremium\" is %s",
                        showMoneyFormat(contractModel.getMonthlyPremium()),
                        showMoneyFormat(contractModel.getTotalYearlyPremium())));
    }

    private Optional<Double> getRateFromInsuredGroupModel(int quantity) {
        return insuredGroupArrayList.stream()
                .filter(insuredGroup -> quantity >= insuredGroup.getFromQty() && quantity <= insuredGroup.getToQty())
                .map(insuredGroup -> insuredGroup.getRate()).findFirst();
    }

    private Optional<Double> getRateFromAgeGroupModel(int age) {
        return contractModel.getAgeGroupList().stream()
                .filter(ageGroup -> age >= ageGroup.getFromAge() && age <= ageGroup.getToAge())
                .map(ageGroup -> ageGroup.getRate()).findFirst();
    }

    public void deleteInsuredPersonFromPersonArrayList(Person selectedModel) {
        boolean exsitsInOtherContract = false;
        for (Contract contract : contractArrayList) {
            if (!contract.getId().equals(contractModel.getId()) && contract.getInsurePersonList().contains(selectedModel)) {
                exsitsInOtherContract = true;
                break;
            }
        }
        //if this insured person use in other contract
        // we delete just from current Contract else we delete it from Person model too
        if (!exsitsInOtherContract)
            personArrayList.remove(selectedModel);
    }

    public void add() {
        contractModel = new Contract();
        setId(++id);
        setPolicyholderId();
        setStartDate();
        setEndDate();
        setAgeGroupList();
        setObligationList();
        setMonthlyPremium(0L);
        setMonthlyPremium(0L);
        if (contractModel.getPolicyholderId() != null &&
                !contractModel.getAgeGroupList().isEmpty() &&
                !contractModel.getObligationList().isEmpty())
            contractArrayList.add(contractModel);
    }

    /**
     * in this method you can select person from former list
     * this list will filter by contract date
     */
    public void setInsuredPersonFromList() {
        setInsurePersonList(
                getSelectedList(contractModel.getInsurePersonList(),
                        personService, Menu.NumberType.LONG, true,
                        PersonService.ClassName.INSURED_PERSON.toString()));
    }

    /**
     * in this method you can enter new person
     * you couldn't add person who their age is not in current contract
     */
    public void addInsuredPerson() throws ValidationException {
        personService.add();
        Person insuredPerson = personService.getInstance();
        int age = getAge(insuredPerson.getBirthDate());
        if (getRateFromAgeGroupModel(age).isEmpty())
            throw new ValidationException("this " + age + "_year_old is not in your contract");
        ArrayList<Person> personList = contractModel.getInsurePersonList();
        personList.add(insuredPerson);
        setInsurePersonList(personList);
    }


    /**
     * in this method you can see people that their age are available for current contract's AgeGroupList'
     */
    @Override
    public ArrayList<model.common.Common> getShowSelectedList(ArrayList retList, String className) {
        if (className.equals(Person.class.getSimpleName())) {
            retList = (ArrayList<model.common.Common>) retList.stream().filter(item -> {
                        try {
                            return getRateFromAgeGroupModel(getAge(((Person) item).getBirthDate())).isPresent();
                        } catch (ValidationException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }
            ).collect(Collectors.toList());
        }
        return retList;
    }

    private String getValidDateValue(boolean hasCompare) {
        extraInfoForValidationMethod.put(ExtraInfoKeys.ERR, ", the valid format is yyyy/mm/dd");
        extraInfoForValidationMethod.put(ExtraInfoKeys.COMPARE_VAL, hasCompare ? contractModel.getStartDate() : null);
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_ID, contractModel.getId());
        //filter this policyholder that use in other contracts
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_RECORDS,
                contractArrayList.stream()
                        .filter(item -> item.getPolicyholderId().equals(contractModel.getPolicyholderId()))
                        .collect(Collectors.toList()));

        String validVal = menu.getValidField(Menu.ControlType.DATE, extraInfoForValidationMethod);
        extraInfoForValidationMethod.clear();
        return validVal;
    }

    private void setId(Long id) {
        contractModel.setId(id);
    }

    public void setPolicyholderId() {
        Policyholder temp =
                getSelectedList(null,
                        new PolicyholderService(),
                        Menu.NumberType.LONG, false, Policyholder.class.getSimpleName());
        contractModel.setPolicyholderId((Long) temp.getId());
    }

    public void setObligationList() {
        contractModel.setObligationList(
                getSelectedList(contractModel.getObligationList(),
                        new ObligationService(), Menu.NumberType.LONG, true, Obligation.class.getSimpleName())
        );
    }

    public void setAgeGroupList() {
        contractModel.setAgeGroupList(
                getSelectedList(contractModel.getAgeGroupList(),
                        new AgeGroupService(), Menu.NumberType.BTYE, true, AgeGroup.class.getSimpleName())
        );
    }

    public void setStartDate() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "contract's start date ");
        String startDate = getValidDateValue(false);
        contractModel.setStartDate(startDate);
    }

    public void setEndDate() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "contract's end date ");
        String endDate = getValidDateValue(true);
        contractModel.setEndDate(endDate);
    }

    public void setMonthlyPremium(Long monthlyPremium) {
        contractModel.setMonthlyPremium(monthlyPremium);
    }

    public void setTotalYearlyPremium(Long totalYearlyPremium) {
        contractModel.setTotalYearlyPremium(totalYearlyPremium);
    }

    public void setInsurePersonList(ArrayList<Person> insurePersonList) {
        contractModel.setInsurePersonList(insurePersonList);
    }

    @Override
    public String toString() {
        return "Id='" + contractModel.getId() + '\'' +
                ", policyholderName='" +((Person) findObjectById (personArrayList, contractModel.getPolicyholderId()) ).getName()+ '\'' +
                ", startDate='" + contractModel.getStartDate() + '\'' +
                ", endDate='" + contractModel.getEndDate() + '\'' +
                ", monthlyPremium='" + showMoneyFormat(contractModel.getMonthlyPremium()) + '\'' +
                ", totalYearlyPremium='" + showMoneyFormat(contractModel.getTotalYearlyPremium()) + '\''
                ;
    }
}
