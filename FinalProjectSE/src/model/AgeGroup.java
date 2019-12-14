package model;

import model.common.Common;

import java.io.Serializable;

public class AgeGroup extends Common<Byte>  implements Serializable {
    private  Byte fromAge;
    private Byte toAge;
    private  Double rate;

    public Byte getFromAge() {
        return fromAge;
    }

    public void setFromAge(Byte fromAge) {
        this.fromAge = fromAge;
    }

    public Byte getToAge() {
        return toAge;
    }

    public void setToAge(Byte toAge) {
        this.toAge = toAge;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
