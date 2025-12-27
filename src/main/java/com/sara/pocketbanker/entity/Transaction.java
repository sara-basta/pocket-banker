package com.sara.pocketbanker.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {
    private String transactionId;
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;
    private String description;
}
