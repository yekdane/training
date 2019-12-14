package model;

import model.common.Common;

import java.io.Serializable;

public class Claim extends Common<Long> implements Serializable {

    private Long insuredId;
    private Long contractId;
    private String claimDate;
    private Long amount;
    private Long insureAmount;
    private Long insurePersonAmount;
    private Long obligationId;
    private Long overCeiling;

    public Long getInsuredId() {
        return insuredId;
    }

    public void setInsuredId(Long insuredId) {
        this.insuredId = insuredId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getInsureAmount() {
        return insureAmount;
    }

    public void setInsureAmount(Long insureAmount) {
        this.insureAmount = insureAmount;
    }

    public Long getInsurePersonAmount() {
        return insurePersonAmount;
    }

    public void setInsurePersonAmount(Long insurePersonAmount) {
        this.insurePersonAmount = insurePersonAmount;
    }

    public Long getObligationId() {
        return obligationId;
    }

    public void setObligationId(Long obligationId) {
        this.obligationId = obligationId;
    }

    public Long getOverCeiling() {
        return overCeiling;
    }

    public void setOverCeiling(Long overCeiling) {
        this.overCeiling = overCeiling;
    }
}
