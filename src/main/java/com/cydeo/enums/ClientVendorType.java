package com.cydeo.enums;

public enum ClientVendorType {

    VENDOR("Vendor"), CLIENT("Client");

    public final String value;
    ClientVendorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
