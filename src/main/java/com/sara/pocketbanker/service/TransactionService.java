package com.sara.pocketbanker.service;

import com.sara.pocketbanker.exception.ResourceNotFoundException;
import com.sara.pocketbanker.model.Transaction;
import com.sara.pocketbanker.model.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    List<Transaction> transactions = new ArrayList<>();

    public Transaction recordTransaction(String accountId,
            TransactionType type, double amount, String description) {
        Transaction tr = new Transaction(
                "T" + (transactions.size() + 1),
                accountId,
                type,
                amount,
                LocalDateTime.now(),
                description
        );
        transactions.add(tr);
        return tr;
    }

    public List<Transaction> transactionsByAccount(String accountId) {
        return transactions.stream()
                .filter(tr -> tr.getAccountId().equals(accountId))
                .toList();
    }

    public Transaction transactionsById(String id) {
        return transactions.stream()
                .filter(tr -> tr.getTransactionId().equals(id))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("There is no transaction with this id: "+id));
    }
}
