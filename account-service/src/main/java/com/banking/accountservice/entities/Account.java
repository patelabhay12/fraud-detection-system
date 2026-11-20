package com.banking.accountservice.entities;

import com.banking.accountservice.enums.AccountStatus;
import com.banking.accountservice.enums.AccountType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private String id;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountHolderName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(nullable = false,precision = 15,scale = 2)
    private BigDecimal balance;

    @Column(nullable = false,precision = 15,scale = 2)
    private BigDecimal dailyTransactionLimit;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
