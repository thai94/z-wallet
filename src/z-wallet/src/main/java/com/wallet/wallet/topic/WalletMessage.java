package com.wallet.wallet.topic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class WalletMessage implements Serializable {
    public String userid = "";
    public long balance = 0;
}
