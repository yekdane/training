package service;

import model.AgeGroup;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;

public class AgeGroupService extends Common<Byte> {
    private AgeGroup ageGroupModel;
    private static Byte id = 0;

    @Override
    public AgeGroupService setModel(model.common.Common policyholderModel) {
        this.ageGroupModel = policyholderModel == null ? new AgeGroup() : (AgeGroup) policyholderModel;
        return this;
    }

    @Override
    public ArrayList<AgeGroup> getRelevantArrayList() {
        return ageGroupsArrayList;
    }

    public void add() {
        ageGroupModel = new AgeGroup();
        setId(++id);
        setFromAge();
        setToAge();
        setRate();
        ageGroupsArrayList.add(ageGroupModel);
    }

    private Byte getValidValue(ArrayList<AgeGroup> items, boolean hasCompare) {
        extraInfoForValidationMethod.put(ExtraInfoKeys.COMPARE_VAL, hasCompare ? ageGroupModel.getFromAge() : null);
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_ID, ageGroupModel.getId());
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_RECORDS, items);
        Byte validVal = menu.getValidField(Menu.ControlType.AGE, extraInfoForValidationMethod);
        extraInfoForValidationMethod.clear();
        return validVal;
    }

    private void setId(Byte id) {
        ageGroupModel.setId(id);
    }

    private void setFromAge() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "from Age");
        ageGroupModel.setFromAge(getValidValue(ageGroupsArrayList, false));
    }

    private void setToAge() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "to Age");
        ageGroupModel.setToAge(getValidValue(ageGroupsArrayList, true));
    }

    private void setRate() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "insurance rate");
        Double rate = menu.getValidField(Menu.ControlType.RATE, extraInfoForValidationMethod);
        ageGroupModel.setRate(rate);
    }

    @Override
    public String toString() {
        return "id=" + ageGroupModel.getId() +
                ", fromAge=" + ageGroupModel.getFromAge() +
                ", toAge=" + ageGroupModel.getToAge() +
                ", rate=" + ageGroupModel.getRate();
    }
}
