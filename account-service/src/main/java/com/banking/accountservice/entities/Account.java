package com.banking.accountservice.entities;

import com.banking.accountservice.enums.AccountStatus;
import com.banking.accountservice.enums.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private static final Object EnumType = ;
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private String id;

    private String accountNumber;

    private String accountHolderName;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private AccountStatus status;
}
