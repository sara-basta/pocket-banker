package com.sara.pocketbanker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String transactionId;
    private String accountId;
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;
    private String description;
}
