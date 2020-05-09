package com.wallet.constant;

import lombok.Getter;

@Getter
public enum Service {
    WITHDRAW(1, "withdraw"),
    WALLET_TOPUP(2, "wallettopup"),
    MONEY_TRANSFER(3, "moneytransfer"),
    MOBILE_CARD(4, "mobilecard"),
    LINK_CARD(5, "linkCard"),
    UN_LINK_CARD(6, "unLinkCard"),
    VERIFY_USER(7, "verifyUser");

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
