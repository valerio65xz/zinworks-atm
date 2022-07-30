package com.zinworks.atm.model;

import lombok.Getter;

@Getter
public class Withdrawal extends Balance{

    private final int fifhties;
    private final int twenties;
    private final int tens;
    private final int fives;

    public Withdrawal(String accountNumber, int balance, int maximumWithdrawalAmount, int[] banknotesWithdrawn){
        super(accountNumber, balance, maximumWithdrawalAmount);

        this.fifhties = banknotesWithdrawn[0];
        this.twenties = banknotesWithdrawn[1];
        this.tens = banknotesWithdrawn[2];
        this.fives = banknotesWithdrawn[3];
    }

}