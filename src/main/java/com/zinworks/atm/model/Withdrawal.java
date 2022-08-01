package com.zinworks.atm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class Withdrawal extends Balance{

    private int fifhties;
    private int twenties;
    private int tens;
    private int fives;

    public Withdrawal(String accountNumber, int balance, int maximumWithdrawalAmount, int[] banknotesWithdrawn){
        super(accountNumber, balance, maximumWithdrawalAmount);

        this.fifhties = banknotesWithdrawn[0];
        this.twenties = banknotesWithdrawn[1];
        this.tens = banknotesWithdrawn[2];
        this.fives = banknotesWithdrawn[3];
    }

}