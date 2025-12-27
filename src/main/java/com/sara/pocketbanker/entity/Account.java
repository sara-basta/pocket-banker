package com.sara.pocketbanker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private AccountType type;
    private LocalDate createdAt;
    private Boolean isActive;

    private List<Transaction> transactions = new ArrayList<>();
}
