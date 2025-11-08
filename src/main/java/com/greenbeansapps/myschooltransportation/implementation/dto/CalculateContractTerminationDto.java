package com.greenbeansapps.myschooltransportation.implementation.dto;

public class CalculateContractTerminationDto {

    private float[] monthlyValues;
    private int[] monthsSinceYearStart;
    private float[] alreadyPaid;
    private float percent;

    public CalculateContractTerminationDto(float[] monthlyValues, int[] monthsSinceYearStart, float[] alreadyPaid, float percent) {
        this.monthlyValues = monthlyValues;
        this.monthsSinceYearStart = monthsSinceYearStart;
        this.alreadyPaid = alreadyPaid;
        this.percent = percent;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float[] getAlreadyPaid() {
        return alreadyPaid;
    }

    public void setAlreadyPaid(float[] alreadyPaid) {
        this.alreadyPaid = alreadyPaid;
    }

    public int[] getMonthsSinceYearStart() {
        return monthsSinceYearStart;
    }

    public void setMonthsSinceYearStart(int[] monthsSinceYearStart) {
        this.monthsSinceYearStart = monthsSinceYearStart;
    }

    public float[] getMonthlyValues() {
        return monthlyValues;
    }

    public void setMonthlyValues(float[] monthlyValues) {
        this.monthlyValues = monthlyValues;
    }
}
