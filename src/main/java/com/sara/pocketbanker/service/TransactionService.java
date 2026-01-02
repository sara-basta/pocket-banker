package com.sara.pocketbanker.service;

import com.sara.pocketbanker.dto.response.TransactionResponseDTO;
import com.sara.pocketbanker.dto.response.TransactionResponseMapper;
import com.sara.pocketbanker.exception.ResourceNotFoundException;
import com.sara.pocketbanker.entity.Transaction;
import com.sara.pocketbanker.entity.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionResponseMapper transactionResponseMapper;
    List<Transaction> transactions = new ArrayList<>();

    public TransactionService(TransactionResponseMapper transactionResponseMapper) {
        this.transactionResponseMapper = transactionResponseMapper;
    }

    public Transaction recordTransaction(String accountId,
            TransactionType type, double amount, String description) {
        Transaction tr = new Transaction(
                getNextTransactionId(),
                accountId,
                type,
                amount,
                LocalDateTime.now(),
                description
        );
        transactions.add(tr);
        return tr;
    }

    public List<TransactionResponseDTO> transactionsByAccount(String accountId) {
        return transactions.stream()
                .filter(tr -> tr.getAccountId().equals(accountId))
                .map(transactionResponseMapper)
                .toList();
    }

    public TransactionResponseDTO transactionsById(String id) {
        return transactions.stream()
                .filter(tr -> tr.getTransactionId().equals(id))
                .map(transactionResponseMapper)
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("There is no transaction with this id: "+id));
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return transactions.stream()
                .map(transactionResponseMapper)
                .toList();
    }

    // Entity-level deletion because of DTO (read-only)
    public boolean deleteTransactionEntity(String transactionId) {
        return transactions.removeIf(tr -> tr.getTransactionId().equals(transactionId));
    }

    public String getNextTransactionId() {
        return "T" + (transactions.size()+1);
    }
}
