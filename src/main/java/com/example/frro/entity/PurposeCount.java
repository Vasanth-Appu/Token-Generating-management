package com.example.frro.entity;

public class PurposeCount {

    private String purpose;
    private int total;
    private int openCount;
    private int closedCount;

    // Getters and setters
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    public int getClosedCount() {
        return closedCount;
    }

    public void setClosedCount(int closedCount) {
        this.closedCount = closedCount;
    }
}

