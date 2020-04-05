package com.bank.viettin.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {
    public int returncode;
}
