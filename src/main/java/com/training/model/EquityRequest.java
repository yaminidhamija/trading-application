package com.training.model;

public class EquityRequest {

    int traderId;
    int equityId;
    int units;

    public int getTraderId() {
        return traderId;
    }

    public void setTraderId(int traderId) {
        this.traderId = traderId;
    }

    public int getEquityId() {
        return equityId;
    }

    public void setEquityId(int equityId) {
        this.equityId = equityId;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
