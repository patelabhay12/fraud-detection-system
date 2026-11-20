package com.banking.accountservice.dto;

import com.banking.accountservice.enums.AccountStatus;
import com.banking.accountservice.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
public class AccountResponse {

    private String id;
    private String accountNumber;
    private String accountHolderName;
    private String email;
    private String phone;
    private AccountType accountType;
    private AccountStatus status;
    private BigDecimal balance;
    private BigDecimal dailyTransactionLimit;
    LocalDateTime createdAt;

}
