package com.training.model;

public class EquityResponse extends Response {

    private int equityId;
    private String equityName;
    private Double unitPrice;
    private int totalHoldings;

    public int getEquityId() {
        return equityId;
    }

    public void setEquityId(int equityId) {
        this.equityId = equityId;
    }

    public String getEquityName() {
        return equityName;
    }

    public void setEquityName(String equityName) {
        this.equityName = equityName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalHoldings() {
        return totalHoldings;
    }

    public void setTotalHoldings(int totalHoldings) {
        this.totalHoldings = totalHoldings;
    }
}
