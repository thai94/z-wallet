package com.bank.sacombank.database.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "customer")
@Data
public class Customer implements Serializable {

    @Id
    @Column(name = "cmnd")
    public String cmnd;
    @Column(name = "sdt")
    public String sdt;
    @Column(name = "full_name")
    public String fullName;
    @Column(name = "address")
    public String address;
    @Column(name = "dob")
    public Date dob;
}
