package model;

import model.common.Common;

import java.io.Serializable;
import java.util.ArrayList;

public class Contract extends Common<Long>implements Serializable {
    private Long policyholderId;
    private ArrayList<Obligation> obligationList = new ArrayList<>();
    private ArrayList<AgeGroup> ageGroupList = new ArrayList<>();
    private String startDate;
    private String endDate;
    private Long monthlyPremium;
    private Long totalYearlyPremium;
    private ArrayList<Person> insurePersonList = new ArrayList<>();

    public Long getPolicyholderId() {
        return policyholderId;
    }

    public void setPolicyholderId(Long policyholderId) {
        this.policyholderId = policyholderId;
    }

    public ArrayList<Obligation> getObligationList() {
        return obligationList;
    }

    public void setObligationList(ArrayList<Obligation> obligationList) {
        this.obligationList = obligationList;
    }

    public ArrayList<AgeGroup> getAgeGroupList() {
        return ageGroupList;
    }

    public void setAgeGroupList(ArrayList<AgeGroup> ageGroupList) {
        this.ageGroupList = ageGroupList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getMonthlyPremium() {
        return monthlyPremium;
    }

    public void setMonthlyPremium(Long monthlyPremium) {
        this.monthlyPremium = monthlyPremium;
    }

    public Long getTotalYearlyPremium() {
        return totalYearlyPremium;
    }

    public void setTotalYearlyPremium(Long totalYearlyPremium) {
        this.totalYearlyPremium = totalYearlyPremium;
    }

    public ArrayList<Person> getInsurePersonList() {
        return insurePersonList;
    }

    public void setInsurePersonList(ArrayList<Person> insurePersonList) {
        this.insurePersonList = insurePersonList;
    }
}
