package com.sara.pocketbanker.service;

import com.sara.pocketbanker.dto.request.TransactionRequestDTO;
import com.sara.pocketbanker.dto.response.TransactionResponseDTO;
import com.sara.pocketbanker.exception.ResourceNotFoundException;
import com.sara.pocketbanker.entity.Transaction;
import com.sara.pocketbanker.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    List<Transaction> transactions = new ArrayList<>();

    public Transaction createTransaction(String accountId, TransactionRequestDTO dto) {
        Transaction tr = TransactionMapper.toEntity(dto, accountId);
        tr.setTransactionId(getNextTransactionId());
        transactions.add(tr);
        return tr;
    }

    public List<TransactionResponseDTO> transactionsByAccount(String accountId) {
        return transactions.stream()
                .filter(tr -> tr.getAccountId().equals(accountId))
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public TransactionResponseDTO transactionsById(String id) {
        return transactions.stream()
                .filter(tr -> tr.getTransactionId().equals(id))
                .map(TransactionMapper::toResponse)
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("There is no transaction with this id: "+id));
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return transactions.stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    // Entity-level deletion because of DTO (read-only)
    public boolean deleteTransactionEntity(String transactionId) {
        return transactions.removeIf(tr -> tr.getTransactionId().equals(transactionId));
    }

    public String getNextTransactionId() {
        return "T-" + UUID.randomUUID();
    }

}
