package com.example.huxinwu.ipaddress.model;

public class AddressItem {

    private String formatted_address;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    @Override
    public String toString() {
        return "AddressItem{" +
                "formatted_address='" + formatted_address + '\'' +
                '}';
    }
}
