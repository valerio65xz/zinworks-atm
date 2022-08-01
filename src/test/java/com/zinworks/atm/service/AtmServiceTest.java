package com.zinworks.atm.service;

import com.zinworks.atm.BaseUnitTest;
import com.zinworks.atm.exception.ResponseException;
import com.zinworks.atm.model.Balance;
import com.zinworks.atm.model.Withdrawal;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static com.zinworks.atm.exception.ResponseErrorEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AtmServiceTest extends BaseUnitTest {

    @Spy
    private AtmService atmService;

    @Test
    void getBalance_success(){
        String accountNumber = "123456789";
        int pin = 1234;
        Balance balance = new Balance(
            accountNumber,
                800,
                1000
        );

        Balance result = atmService.getBalance(accountNumber, pin);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(balance);
    }

    @Test
    void getBalance_failsForUserNotFound(){
        String accountNumber = "1111";
        int pin = 1234;

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> atmService.getBalance(accountNumber, pin))
                .matches(e -> e.getError().equals(USER_NOT_FOUND));
    }

    @Test
    void getBalance_failsForPinNotValid(){
        String accountNumber = "123456789";
        int pin = 1111;

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> atmService.getBalance(accountNumber, pin))
                .matches(e -> e.getError().equals(PIN_NOT_VALID));
    }

    @Test
    void withdrawal_success(){
        String accountNumber = "123456789";
        int pin = 1234;
        int amount = 900;
        Withdrawal withdrawal = new Withdrawal(
                accountNumber,
                0,
                100,
                new int[]{10, 20, 0, 0}
        );

        Withdrawal result = atmService.withdraw(accountNumber, pin, amount);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(withdrawal);
    }

    @Test
    void withdrawal_failsForInvalidAmount(){
        String accountNumber = "123456789";
        int pin = 1234;
        int amount = -1;

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> atmService.withdraw(accountNumber, pin, amount))
                .matches(e -> e.getError().equals(NOT_VALID_AMOUNT));
    }

    @Test
    void withdrawal_failsForNotEnoughMoney(){
        String accountNumber = "123456789";
        int pin = 1234;
        int amount = 5000;

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> atmService.withdraw(accountNumber, pin, amount))
                .matches(e -> e.getError().equals(NOT_ENOUGH_BALANCE));
    }

    @Test
    void withdrawal_failsForNotBanknotesTerminated(){
        atmService.withdraw("123456789", 1234, 1000);
        atmService.withdraw("987654321", 4321, 500);

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> atmService.withdraw("987654321", 4321, 5))
                .matches(e -> e.getError().equals(BANKNOTES_TERMINATED));
    }

}
