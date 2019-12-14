package service;

import model.Address;
import model.Person;
import model.Policyholder;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;


public class PersonService extends Common<Long> {
    private Person personModel;
    private AddressService addressService = new AddressService();
    private static Long id = 0L;
    protected String birthDataCaption, nationalCodeCaption;

    @Override
    public PersonService setModel(model.common.Common currentPersonModel) {
        this.personModel = currentPersonModel == null ? new Person() : (Person) currentPersonModel;
        if (currentPersonModel instanceof Policyholder &&
                ((Policyholder) currentPersonModel).getType() == Policyholder.PolicyholderType.LEGAL
        ) {
            birthDataCaption = "constitution date";
            nationalCodeCaption = "economic code";
        } else {
            birthDataCaption = "birth date";
            nationalCodeCaption = "national code";
        }
        addressService.setModel(this.personModel.getAddress());
        return this;
    }

    @Override
    public ArrayList<Person> getRelevantArrayList() {
        return personArrayList;
    }

    public Person getInstance() {
        return personModel;
    }

    public void add() {
        editPerson(new Person());
        personArrayList.add(personModel);
    }

    protected void editPerson(Person currentPerson) {
        this.setModel(currentPerson);
        if (currentPerson.getId() == null)
            setId(++id);
        setName();
        setFamily();
        setBirthDate();
        setNationalCode();
        setTel();
        setAddress();
    }

    private ArrayList<Person> filterPersonListByClassType(ArrayList<Person> items, String className) {
        return (ArrayList<Person>) items.stream()
                .filter(item -> item.getClass().getSimpleName().equals(className))
                .collect(Collectors.toList());
    }

    public ArrayList<Person> getInsuredPersons() {
        return filterPersonListByClassType(personArrayList, Person.class.getSimpleName());
    }

    public ArrayList<Person> getPolicyholder(ArrayList<Person> items) {
        return filterPersonListByClassType(items, Policyholder.class.getSimpleName());
    }

    public ArrayList<Person> searchByName(String name) {
        String finalName = name.toLowerCase();
        return (ArrayList<Person>) getInsuredPersons().stream().
                filter(item -> item.getName().toLowerCase().contains(finalName) || item.getFamily().toLowerCase().contains(finalName))
                .collect(Collectors.toList());

    }

    public Optional<Person> searchByNationalCode(String nationalCode) {
        return getInsuredPersons().stream().
                filter(item -> item.getNationalCode().equals(nationalCode)).findFirst();
    }

    private void setId(Long id) {
        personModel.setId(id);
    }

    private void setName() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "name");
        String name = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        personModel.setName(name);
    }

    private void setFamily() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "family");
        String family = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        personModel.setFamily(family);
    }

    private void setBirthDate() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, birthDataCaption);
        extraInfoForValidationMethod.put(ExtraInfoKeys.ERR, ", the valid format is yyyy/mm/dd");
        extraInfoForValidationMethod.put(ExtraInfoKeys.IS_BIRTH_DATE, true);

        String birthDate = menu.getValidField(Menu.ControlType.DATE, extraInfoForValidationMethod);

        personModel.setBirthDate(birthDate);
        extraInfoForValidationMethod.clear();
    }

    private void setNationalCode() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, nationalCodeCaption);
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_ID, personModel.getId());
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_RECORDS, personArrayList);
        String nationalCode = menu.getValidField(Menu.ControlType.NATIONAL_CODE, extraInfoForValidationMethod);
        personModel.setNationalCode(nationalCode);
        extraInfoForValidationMethod.clear();
    }

    private void setTel() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "phone");
        String tel = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        personModel.setTel(tel);
    }

    private void setAddress() {
        addressService.edit(personModel.getAddress() == null ? new Address() : personModel.getAddress());
        personModel.setAddress(addressService.getInstance());
    }

    @Override
    public String toString() {
        return "id=" + personModel.getId() +
                ", name='" + personModel.getName() + '\'' +
                ", family='" + personModel.getFamily() + '\'' +
                ", " + birthDataCaption + "='" + personModel.getBirthDate() + '\'' +
                ", " + nationalCodeCaption + " = '" + personModel.getNationalCode() + '\'' +
                ", address=" + addressService.toString() +
                ", tel='" + personModel.getTel() + '\'';
    }

    public enum ClassName implements service.common.ClassName {
        POLICYHOLDER(Policyholder.class.getSimpleName()), INSURED_PERSON(Person.class.getSimpleName());
        String val;

        ClassName(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }
    }
}
