package model;

import model.common.Common;

import java.io.Serializable;

public class InsuredGroup extends Common<Byte>implements Serializable {
    private Integer fromQty;
    private Integer toQty;
    private Double rate;

    public Integer getFromQty() {
        return fromQty;
    }

    public void setFromQty(Integer fromQty) {
        this.fromQty = fromQty;
    }

    public Integer getToQty() {
        return toQty;
    }

    public void setToQty(Integer toQty) {
        this.toQty = toQty;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
