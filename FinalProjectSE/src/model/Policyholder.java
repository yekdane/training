package model;

import java.io.Serializable;

public class Policyholder extends Person implements Serializable {
    private PolicyholderType type;
    private String officeTel;

    public PolicyholderType getType() {
        return type;
    }

    public void setType(PolicyholderType type) {
        this.type = type;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public enum PolicyholderType {
        NATURAL, LEGAL;
    }
}
