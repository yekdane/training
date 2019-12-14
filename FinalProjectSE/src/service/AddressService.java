package service;

import model.Address;
import service.common.Common;
import view.common.Menu;

import java.util.ArrayList;


public class AddressService extends Common<Long> {
    private Address addressModel;
    private static Long id = 0L;

    @Override
    public AddressService setModel(model.common.Common addressModel) {
        this.addressModel = (Address) addressModel;
        return this;
    }

    @Override
    public  ArrayList<Address> getRelevantArrayList() {
        return null;
    }

    public Address getInstance() {
        if (addressModel == null)
            throw new NullPointerException("your model is not created ");
        return addressModel;
    }

    public void add() {
        edit(new Address());
    }

    public void edit(Address currentAddress) {
        addressModel = currentAddress;
        if (addressModel.getId() == null)
            setId(++id);
        setCountry();
        setProvince();
        setCity();
        setAlley();
        setPostalCode();
    }

    private void setId(Long id) {
        addressModel.setId(id);
    }

    private void setCountry() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "country name");
        String country = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        addressModel.setCountry(country);
    }

    private void setProvince() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "province name");
        String province = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        addressModel.setProvince(province);
    }

    private void setCity() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "city name");
        String city = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        addressModel.setCity(city);
    }

    private void setAlley() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "alley name");
        String alley = menu.getValidField(Menu.ControlType.STRING, extraInfoForValidationMethod);
        addressModel.setAlley(alley);
    }

    private void setPostalCode() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "postalCode");
        String postalCode = menu.getValidField(Menu.ControlType.POSTAL_CODE, extraInfoForValidationMethod);
        addressModel.setPostalCode(postalCode);
    }

    @Override
    public String toString() {
        return ", id='" + addressModel.getId() + '\'' +
                ", country='" + addressModel.getCountry() + '\'' +
                ", province='" + addressModel.getProvince() + '\'' +
                ", city='" + addressModel.getCity() + '\'' +
                ", alley='" + addressModel.getAlley() + '\'' +
                ", postalCode='" + addressModel.getPostalCode() + '\'';
    }
}
