package com.pio.foodiepanda.enums;

public enum DeliveryAddressLabel {
    HOME("Home"),
    OFFICE("Office"),
    OTHER("Other"),
    RESTAURANT("Restaurant");

    private String type;

    private DeliveryAddressLabel(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
