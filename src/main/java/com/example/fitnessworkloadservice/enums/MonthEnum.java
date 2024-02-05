package com.example.fitnessworkloadservice.enums;

public enum MonthEnum {

    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);

    private final int monthValue;

    public static MonthEnum getMonthEnum(int monthValue) {
        for (MonthEnum month : MonthEnum.values()) {
            if (month.getMonthValue() == monthValue) {
                return month;
            }
        }
        throw new IllegalArgumentException("Invalid month value: " + monthValue);
    }

    MonthEnum(int monthValue) {
        this.monthValue = monthValue;
    }

    public int getMonthValue() {
        return monthValue;
    }
}
