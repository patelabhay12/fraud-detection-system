package com.banking.accountservice.controller;


import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.dto.CreateAccountRequest;
import com.banking.accountservice.services.AccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
    }


    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) throws Exception {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber) throws Exception {
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    @PutMapping("/{accountNumber}/block")
    public ResponseEntity<String> blockAccount(@PathVariable String accountNumber) throws Exception {
        accountService.blockAccount(accountNumber);
        return ResponseEntity.ok("Account blocked Successfully...");
    }


    /*
     * SAGA STEP 1 - Deduct Balance
     * Called by transaction service when transfer is initiated
     */

    @PutMapping("/{accountNumber}/deduct")
    public ResponseEntity<String> deductBalance(@PathVariable String accountNumber,@RequestParam BigDecimal amount) throws Exception {
        accountService.deductBalance(accountNumber,amount);
        return ResponseEntity.ok("Account deducted Successfully...");
    }

    /*
     * SAGA STEP 4 - Compensating transaction endpoint
     * Called By transaction service
     * 1. Fraud Detected -> refund sender (undo step 1)
     * 2. Transaction completed -> credit receiver
     */

    @PutMapping("/{accountNumber}/credit")
    public ResponseEntity<String> creditBalance(@PathVariable String accountNumber,@RequestParam BigDecimal amount) throws Exception {
        accountService.creditBalance(accountNumber,amount);
        return ResponseEntity.ok("Account credited Successfully...");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AccountResponse>> getAllAccounts(){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
    }
}
