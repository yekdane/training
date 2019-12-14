package model;

import model.common.Common;

import java.io.Serializable;

public class Obligation extends Common<Long> implements Serializable {
    private String name;
    private Byte fronchise;
    private  Long ceiling;
    private  String serviceName;
    private Double rate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getFronchise() {
        return fronchise;
    }

    public void setFronchise(Byte fronchise) {
        this.fronchise = fronchise;
    }

    public Long getCeiling() {
        return ceiling;
    }

    public void setCeiling(Long ceiling) {
        this.ceiling = ceiling;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
