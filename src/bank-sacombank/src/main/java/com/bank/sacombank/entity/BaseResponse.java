package com.bank.sacombank.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {
    public int returncode;
}
