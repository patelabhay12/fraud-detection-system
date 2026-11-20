package com.banking.accountservice.services;


import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.dto.CreateAccountRequest;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(@Valid CreateAccountRequest request) throws Exception;

    AccountResponse getAccount(String accountNumber) throws Exception;

    BigDecimal getBalance(String accountNumber) throws Exception;

    void blockAccount(String accountNumber) throws Exception;

    void deductBalance(String accountNumber, BigDecimal amount) throws Exception;

    void creditBalance(String accountNumber, BigDecimal amount) throws Exception;

    List<AccountResponse> getAllAccounts();
}
