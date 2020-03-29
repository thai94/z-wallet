package com.wallet.constant;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Service {
    CASH_OUT(1, "cashout"),
    CASH_IN(2, "cashin"),
    MONEY_TRANSFER(3, "moneytransfer"),
    MOBILE_CARD(4, "mobilecard");

    private int key;
    private String value;

    Service(int key, String value){
        this.key = key;
        this.value = value;
    }

    public static Service find(int key) {
        for(Service service : Service.values()) {
            if(service.key == key) {
                return  service;
            }
        }
        return  null;
    }
}
