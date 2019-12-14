package service;

import model.Obligation;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;

public class ObligationService extends Common<Long> {
    private Obligation obligationModel;
    private static Long id = 0L;

    @Override
    public ObligationService setModel(model.common.Common currentObligationModel) {
        this.obligationModel = currentObligationModel == null ? new Obligation() : (Obligation) currentObligationModel;
        return this;
    }

    @Override
    public ArrayList<Obligation> getRelevantArrayList() {
        return obligationArrayList;
    }

    public void add() {
        obligationModel = new Obligation();
        setId(++id);
        setName();
        setFronchise();
        setCeiling();
        setServiceName();
        setRate();
        obligationArrayList.add(obligationModel);
    }

    public void setId(Long id) {
        obligationModel.setId(id);
    }

    public void setName() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "obligation name");
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_ID, obligationModel.getId());
        extraInfoForValidationMethod.put(ExtraInfoKeys.CURRENT_RECORDS, obligationArrayList);
        String name = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        obligationModel.setName(name);
        extraInfoForValidationMethod.clear();
    }

    public void setFronchise() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "fronchise");
        Byte fronchise = menu.getValidField(Menu.ControlType.PERCENT,
                extraInfoForValidationMethod);
        obligationModel.setFronchise(fronchise);
    }

    public void setCeiling() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "ceiling");
        Long ceiling = menu.getValidField(Menu.ControlType.NUMBER,
                extraInfoForValidationMethod);
        obligationModel.setCeiling(ceiling);
    }

    public void setServiceName() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "service names");
        extraInfoForValidationMethod.put(ExtraInfoKeys.NOTNULL, false);
        String serviceName = menu.getValidField(Menu.ControlType.STRING,
                extraInfoForValidationMethod);
        obligationModel.setServiceName(serviceName);
    }

    public void setRate() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "obligation rate");
        Double rate = menu.getValidField(Menu.ControlType.RATE,
                extraInfoForValidationMethod);
        obligationModel.setRate(rate);
    }

    @Override
    public String toString() {
        return "id='" + obligationModel.getId() + '\'' +
                ", name='" + obligationModel.getName() + '\'' +
                ", fronchise=" + obligationModel.getFronchise() +
                ", ceiling=" + showMoneyFormat(obligationModel.getCeiling()) +
                ", serviceName='" + obligationModel.getServiceName() + '\'' +
                ", rate=" + obligationModel.getRate();
    }
}
