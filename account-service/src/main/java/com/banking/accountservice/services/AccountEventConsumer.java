package com.banking.accountservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountEventConsumer {


    private final AccountService accountService;
    /**
     * consume transaction.completed event from Kafka
     * Credits receiver account
     * @param payload
     */

    @KafkaListener(topics = "transaction.completed")
    public void consumeTransactionCompleted(@Payload Map<String ,Object> payload){
        try{
            String receiverAccountNumber = (String) payload.get("receiverAccountNumber");
            BigDecimal amount = new BigDecimal(payload.get("amount").toString());

            log.info("Received transaction completed for account number {} and Amount {} ", receiverAccountNumber,amount);
            accountService.creditBalance(receiverAccountNumber,amount);

        }catch(Exception e){
            log.error("Error while crediting Amount {}", e.getMessage());
        }
    }


    /**
     * Consume fraud.detected Event from kafka
     * Block the flagged account
     * @param payload
     */

    @KafkaListener(topics = "fraud.detected")
    public void consumeFraudDetected(@Payload Map<String ,Object> payload){
        try{
            String accountNumber = (String) payload.get("accountNumber");
            log.info("Fraud Detected - blocking account {}", accountNumber);
            accountService.blockAccount(accountNumber);
        }catch(Exception e){
            log.error("Error while Blocking Account {}", e.getMessage());
        }
    }
}
