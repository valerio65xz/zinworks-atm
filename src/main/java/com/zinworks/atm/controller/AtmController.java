package com.zinworks.atm.controller;

import com.zinworks.atm.model.Balance;
import com.zinworks.atm.model.Withdrawal;
import com.zinworks.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm")
public class AtmController {

    private final AtmService atmService;

    @Autowired
    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @GetMapping("balance/{accountNumber}/{pin}")
    public ResponseEntity<Balance> getBalance(
            @PathVariable("accountNumber") String accountNumber,
            @PathVariable("pin") int pin
    ) {
        Balance balance = atmService.getBalance(accountNumber, pin);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("withdraw/{accountNumber}/{pin}/{amount}")
    public ResponseEntity<Balance> withdraw(
            @PathVariable("accountNumber") String accountNumber,
            @PathVariable("pin") int pin,
            @PathVariable("amount") int amount
    ) {
        Withdrawal withdraw = atmService.withdraw(accountNumber, pin, amount);
        return ResponseEntity.ok(withdraw);
    }

}