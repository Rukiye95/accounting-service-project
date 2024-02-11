package com.cydeo.enums;

public enum InvoiceType {

    PURCHASE("Purchase"), SALES("Sales");

    public final String value;

    InvoiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
