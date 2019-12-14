package service;

import model.InsuredGroup;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;

public class InsuredGroupService extends Common<Byte> {
    private InsuredGroup insuredGroupModel;
    private static Byte id = 0;

    @Override
    public InsuredGroupService setModel(model.common.Common insuredGroupModel) {
        this.insuredGroupModel = insuredGroupModel == null ? new InsuredGroup() : (InsuredGroup) insuredGroupModel;
        return this;
    }

    @Override
    public ArrayList<InsuredGroup> getRelevantArrayList() {
        return insuredGroupArrayList;
    }

    public void add() {
        edit(new InsuredGroup());
        setId(++id);
        insuredGroupArrayList.add(insuredGroupModel);
    }

    @Override
    protected void edit(model.common.Common currentInsuredGroup) {
        this.insuredGroupModel = (InsuredGroup) currentInsuredGroup;
        setFromQty();
        setToQty();
        setRate();
    }

    private void setId(Byte id) {
        insuredGroupModel.setId(id);
    }

    private Integer getValidQtyValue(boolean hasCompare) {
        extraInfoForValidationMethod.put(ExtraInfoKeys.COMPARE_VAL, hasCompare ? insuredGroupModel.getFromQty() : null);
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_ID, insuredGroupModel.getId());
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_RECORDS, insuredGroupArrayList);
        Integer validVal = menu.getValidField(Menu.ControlType.QUANTITY, extraInfoForValidationMethod);
        extraInfoForValidationMethod.clear();
        return validVal;
    }

    private void setFromQty() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "from quantity");
        insuredGroupModel.setFromQty(getValidQtyValue(false));
    }

    private void setToQty() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "to quantity");
        insuredGroupModel.setToQty(getValidQtyValue(true));
    }

    private void setRate() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "insured group rate");
        Double rate = menu.getValidField(Menu.ControlType.RATE, extraInfoForValidationMethod);
        insuredGroupModel.setRate(rate);
    }

    @Override
    public String toString() {
        return "fromQty=" + insuredGroupModel.getFromQty() +
                ", toQty=" + insuredGroupModel.getToQty() +
                ", rate=" + insuredGroupModel.getRate();
    }
}
