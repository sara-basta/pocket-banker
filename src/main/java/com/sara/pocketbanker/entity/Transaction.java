package com.sara.pocketbanker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private String transactionId;
    @ManyToOne
    @JoinColumn(name = "accountNumber")
    @JsonIgnore
    private Account account;
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;
    private String description;
}
