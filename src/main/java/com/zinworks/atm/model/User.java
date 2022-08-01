package com.zinworks.atm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
public class User {

    private String accountNumber;
    private int pin;
    private int balance;
    private int overdraft;

}