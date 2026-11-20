package com.banking.accountservice.services.impls;

import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.dto.CreateAccountRequest;
import com.banking.accountservice.entities.Account;
import com.banking.accountservice.enums.AccountStatus;
import com.banking.accountservice.enums.AccountType;
import com.banking.accountservice.repositories.AccountRepository;
import com.banking.accountservice.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private static final SecureRandom random = new SecureRandom();

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) throws Exception {

        log.info("Request Information...{}", request);

        if(accountRepository.existsByEmail(request.getEmail())){
          throw new Exception("Account Already Exists for email "+request.getEmail());
        }
        Account account = new Account();
        account.setAccountHolderName(request.getAccountHolderName());
        account.setEmail(request.getEmail());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialDeposit());
        account.setPhone(request.getPhone());
        account.setAccountNumber(generateNextAccountNumber());
        account.setDailyTransactionLimit(request.getAccountType()== AccountType.SAVING ? new BigDecimal("1000000"):new BigDecimal("500000"));
        Account saved  = accountRepository.save(account);
        log.info("Account created {} ",saved.getAccountNumber());
        return mapToResponse(saved);
    }

    @Override
    public AccountResponse getAccount(String accountNumber)  {
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return mapToResponse(existingAccount);
    }

    @Override
    public BigDecimal getBalance(String accountNumber) throws Exception {
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account does not exist"));
        return existingAccount.getBalance();
    }

    @Override
    public void blockAccount(String accountNumber) throws Exception {
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new Exception("Account does not exist"));
//        accountRepository.save(existingAccount.);
        existingAccount.setStatus(AccountStatus.BLOCKED);
        accountRepository.save(existingAccount);
        log.info("Account blocked {} ",existingAccount.getAccountNumber());
    }

    @Override
    public void deductBalance(String accountNumber, BigDecimal amount) throws Exception {
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new Exception("Account does not exist"));

        if(existingAccount.getStatus()!=AccountStatus.ACTIVE){
            throw new RuntimeException("Account is not ACTIVE"); 
        }
        if(existingAccount.getBalance().compareTo(amount) <= 0){
            throw new Exception("Insufficient Balance");
        }
        existingAccount.setBalance(existingAccount.getBalance().subtract(amount));
        accountRepository.save(existingAccount);
    }

    @Override
    public void creditBalance(String accountNumber, BigDecimal amount) throws Exception {
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new Exception("Account does not exist"));
        existingAccount.setBalance(existingAccount.getBalance().add(amount));
        accountRepository.save(existingAccount);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }


    private AccountResponse mapToResponse(Account account){
        return AccountResponse.builder()
                .id(account.getId())
                .phone(account.getPhone())
                .email(account.getEmail())
                .accountHolderName(account.getAccountHolderName())
                .accountNumber(account.getAccountNumber())
                .status(account.getStatus())
                .dailyTransactionLimit(account.getDailyTransactionLimit())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .createdAt(account.getCreatedAt())
                .build();
    }

    private String generateNextAccountNumber() {
        String accountNumber;
        do{
            long number =random.nextLong(1000000000000L);
            accountNumber = String.format("%012d",number);
        }while(accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}
