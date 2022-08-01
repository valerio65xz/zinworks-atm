package com.zinworks.atm.controller;

import com.zinworks.atm.ControllerTest;
import com.zinworks.atm.model.Balance;
import com.zinworks.atm.model.UserInputModel;
import com.zinworks.atm.model.Withdrawal;
import com.zinworks.atm.response.ErrorResponse;
import com.zinworks.atm.service.AtmService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.zinworks.atm.exception.ResponseErrorEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class AtmControllerTest extends ControllerTest {

    @MockBean
    private AtmService atmService;

    @Test
    public void getBalance_success() {
        String url = "/atm/balance";
        UserInputModel userInputModel = random(UserInputModel.class);
        Balance balance = random(Balance.class);

        when(atmService.getBalance(any(), anyInt())).thenReturn(balance);

        Balance result = performAndExpect(post(url), userInputModel, Balance.class);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(balance);

        verify(atmService).getBalance(userInputModel.getAccountNumber(), userInputModel.getPin());
    }

    @Test
    public void getBalance_failsForBeanValidation() {
        String url = "/atm/balance";
        UserInputModel userInputModel = new UserInputModel("accountNumber", null);

        ErrorResponse result = performAndExpect(post(url), userInputModel, ErrorResponse.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getMessage().contains("pin must not be null")).isEqualTo(true);
    }

    @Test
    public void withdrawal_success() {
        int amount = 1234;
        String url = "/atm/withdraw/" + amount;
        UserInputModel userInputModel = random(UserInputModel.class);
        Withdrawal withdrawal = random(Withdrawal.class);

        when(atmService.withdraw(any(), anyInt(), anyInt())).thenReturn(withdrawal);

        Withdrawal result = performAndExpect(post(url), userInputModel, Withdrawal.class);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(withdrawal);

        verify(atmService).withdraw(userInputModel.getAccountNumber(), userInputModel.getPin(), amount);
    }

}
