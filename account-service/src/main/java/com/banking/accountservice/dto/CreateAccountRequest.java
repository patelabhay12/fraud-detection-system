package com.banking.accountservice.dto;

import com.banking.accountservice.enums.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateAccountRequest {

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Initial Deposit is required")
    @Positive(message = "Initial Deposit must be positive")
    private BigDecimal initialDeposit;
}
