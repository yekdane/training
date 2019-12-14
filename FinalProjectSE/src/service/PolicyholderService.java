package service;

import model.Person;
import model.Policyholder;
import model.common.Common;
import view.common.Menu;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class PolicyholderService extends PersonService {
    //private static Long id = 0L;
    private Policyholder policyholderModel;

    @Override
    public PolicyholderService setModel(Common policyholderModel) {
        this.policyholderModel = policyholderModel == null ? new Policyholder() : (Policyholder) policyholderModel;
        super.setModel(this.policyholderModel);
        return this;
    }

    public void add() {
        //++id;
        edit( new Policyholder());
        personArrayList.add(policyholderModel);
    }

    @Override
    protected void edit( Common currentPolicyholder) {
        this.policyholderModel = (Policyholder) currentPolicyholder;
        setType();
        super.editPerson( policyholderModel);
        if (policyholderModel.getType() == Policyholder.PolicyholderType.LEGAL)
            setOfficeTel();
    }

    public ArrayList<Person> showByType( Policyholder.PolicyholderType type) {
        PersonService personService = new PersonService();
        return (ArrayList<Person>) personService.getPolicyholder(personArrayList).stream()
                .filter(item -> ((Policyholder) item).getType().equals(type)).collect(Collectors.toList());
    }

    private void setType() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "0 if type is NATURAL and 1 if type is LEGAL");
        int type = menu.getValidField(Menu.ControlType.TYPE, extraInfoForValidationMethod);
        if (type == 0) {
            policyholderModel.setType(Policyholder.PolicyholderType.NATURAL);
            policyholderModel.setOfficeTel("");
        } else policyholderModel.setType(Policyholder.PolicyholderType.LEGAL);
    }

    private void setOfficeTel() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "office phone");
        String officePhone = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        policyholderModel.setOfficeTel(officePhone);
    }

    @Override
    public String toString() {
        Policyholder.PolicyholderType type = policyholderModel.getType();
        return super.toString() +
                ",type=" + type +
                (type == Policyholder.PolicyholderType.LEGAL ?
                        (", officeTel='" + policyholderModel.getOfficeTel() + '\'') : "");
    }
}
