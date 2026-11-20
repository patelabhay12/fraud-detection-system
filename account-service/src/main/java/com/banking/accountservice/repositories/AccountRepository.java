package com.banking.accountservice.repositories;

import com.banking.accountservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByEmail(String email);

    boolean existsByAccountNumber(String accountNumber);
}
