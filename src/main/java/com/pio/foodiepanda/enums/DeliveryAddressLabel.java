package com.pio.foodiepanda.enums;

public enum DeliveryAddressLabel {
    HOME("Home"),
    OFFICE("Office"),
    OTHER("Other"),
    RESTAURANT("Restaurant");

    private final String type;

    DeliveryAddressLabel(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
