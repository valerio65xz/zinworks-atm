package com.zinworks.atm.controller;

import com.zinworks.atm.model.Balance;
import com.zinworks.atm.model.UserInputModel;
import com.zinworks.atm.model.Withdrawal;
import com.zinworks.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/atm")
public class AtmController {

    private final AtmService atmService;

    @Autowired
    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @PostMapping("balance")
    public ResponseEntity<Balance> getBalance(@RequestBody @Valid UserInputModel userInputModel) {
        Balance balance = atmService.getBalance(userInputModel.getAccountNumber(), userInputModel.getPin());
        return ResponseEntity.ok(balance);
    }

    @PostMapping("withdraw/{amount}")
    public ResponseEntity<Balance> withdraw(
            @RequestBody @Valid UserInputModel userInputModel,
            @PathVariable("amount") int amount
    ) {
        Withdrawal withdraw = atmService.withdraw(userInputModel.getAccountNumber(), userInputModel.getPin(), amount);
        return ResponseEntity.ok(withdraw);
    }

}
