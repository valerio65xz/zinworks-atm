package com.zinworks.atm.service;

import com.zinworks.atm.exception.ResponseException;
import com.zinworks.atm.model.User;
import com.zinworks.atm.model.Balance;
import com.zinworks.atm.model.Withdrawal;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.zinworks.atm.exception.ResponseErrorEnum.*;

@Service
public class AtmService {

    private final Map<String, User> atmHashMap = new HashMap<>();
    private final int[] banknotes = {10, 30, 30, 20};

    public AtmService(){
        User firstUser = new User(
                "123456789",
                1234,
                800,
                200
        );
        User secondUser = new User(
                "987654321",
                4321,
                1230,
                50
        );

        atmHashMap.put(firstUser.getAccountNumber(), firstUser);
        atmHashMap.put(secondUser.getAccountNumber(), secondUser);
    }

    public Balance getBalance(String accountNumber, int pin){
        User user = atmHashMap.get(accountNumber);

        if (user == null){
            throw new ResponseException(USER_NOT_FOUND);
        }
        if (user.getPin() != pin){
            throw new ResponseException(PIN_NOT_VALID);
        }

        return new Balance(
                user.getAccountNumber(),
                user.getBalance(),
                user.getBalance() + user.getOverdraft()
        );
    }

    public Withdrawal withdraw(String accountNumber, int pin, int amount){
        User user = atmHashMap.get(accountNumber);

        if (user == null){
            throw new ResponseException(USER_NOT_FOUND);
        }
        if (user.getPin() != pin){
            throw new ResponseException(PIN_NOT_VALID);
        }
        if (amount < 5 || amount%5 != 0){
            throw new ResponseException(NOT_VALID_AMOUNT);
        }
        if (user.getBalance() + user.getOverdraft() < amount){
            throw new ResponseException(NOT_ENOUGH_BALANCE);
        }

        int[] banknotesWithdrawn = processWithdraw(amount);

        // when the amount exceeds the balance, I have also to modify the overdraft
        if (amount > user.getBalance()){
            user.setOverdraft(user.getOverdraft() - (amount - user.getBalance()));
            user.setBalance(0);
        }
        else{
            user.setBalance(user.getBalance() - amount);
        }

        return new Withdrawal(
                user.getAccountNumber(),
                user.getBalance(),
                user.getBalance() + user.getOverdraft(),
                banknotesWithdrawn
            );
    }

    private int[] processWithdraw(int amount){
        int[] localBanknotes = new int[4];
        int[] banknotesWithdrawn = new int[4];

        // this prevents the original array to be modified before check if there are banknotes left
        System.arraycopy(banknotes, 0, localBanknotes, 0, 4);

        // I subtract the amount with the biggest available banknote that I can find
        // and I add that banknote in the banknotesWithdrawn array
        while (amount > 0){
            if (amount >= 50 && localBanknotes[0] > 0){
                localBanknotes[0]--;
                banknotesWithdrawn[0]++;
                amount -= 50;
            }
            else if (amount >= 20 && localBanknotes[1] > 0){
                localBanknotes[1]--;
                banknotesWithdrawn[1]++;
                amount -= 20;
            }
            else if (amount >= 10 && localBanknotes[2] > 0){
                localBanknotes[2]--;
                banknotesWithdrawn[2]++;
                amount -= 10;
            }
            else if (localBanknotes[3] > 0){
                localBanknotes[3]--;
                banknotesWithdrawn[3]++;
                amount -= 5;
            }
            else{
                throw new ResponseException(BANKNOTES_TERMINATED);
            }
        }

        // If my banknotes are not terminates, I save my state
        System.arraycopy(localBanknotes, 0, banknotes, 0, 4);
        return banknotesWithdrawn;
    }

}