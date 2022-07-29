package com.zinworks.atm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Balance {

    private String accountNumber;
    private int balance;
    private int maximumWithdrawalAmount;

}
