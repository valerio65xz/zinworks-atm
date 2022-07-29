package com.zinworks.atm.service;

import com.zinworks.atm.exception.ResponseException;
import com.zinworks.atm.model.User;
import com.zinworks.atm.model.Balance;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    public Balance withdraw(String accountNumber, int pin, int amount){
        User user = atmHashMap.get(accountNumber);

        if (user == null){
            throw new ResponseException(USER_NOT_FOUND);
        }
        if (user.getPin() != pin){
            throw new ResponseException(PIN_NOT_VALID);
        }

        if (user.getBalance() + user.getOverdraft() < amount){
            throw new ResponseException(NOT_ENOUGH_AMOUNT);
        }

        // 1000 , 900 + 200
        // 50, 0 100
        if (amount > user.getBalance()){
            user.setOverdraft(user.getOverdraft() - (amount - user.getBalance()));
            user.setBalance(0);
        }
        else{
            user.setBalance(user.getBalance() - amount);
        }

        processWithdraw(amount);
        atmHashMap.replace(accountNumber, user);

        return new Balance(
                    user.getAccountNumber(),
                    user.getBalance(),
                    user.getBalance() + user.getOverdraft()
            );
    }

    public void processWithdraw(int amount){
        if (amount%5 != 0){
            throw new ResponseException(NOT_VALID_AMOUNT);
        }

        while (amount > 0){
            if (amount%50 == 0 && banknotes[0] > 0){
                banknotes[0]--;
                amount -= 50;
            }
            else if (amount%20 == 0 && banknotes[1] > 0){
                banknotes[1]--;
                amount -= 20;
            }
            else if (amount%10 == 0 && banknotes[2] > 0){
                banknotes[2]--;
                amount -= 10;
            }
            else if (banknotes[3] > 0){
                banknotes[3]--;
                amount -= 5;
            }
            else{
                throw new ResponseException(BANKNOTES_TERMINATED);
            }
        }
    }

}
