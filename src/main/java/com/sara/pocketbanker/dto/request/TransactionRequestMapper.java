package com.sara.pocketbanker.dto.request;

import com.sara.pocketbanker.entity.Transaction;
import com.sara.pocketbanker.service.TransactionService;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

public class TransactionRequestMapper implements BiFunction<TransactionRequestDTO, String,Transaction> {

    private final TransactionService transactionService;

    public TransactionRequestMapper(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public Transaction apply(TransactionRequestDTO dto, String accountId) {
        return new Transaction(
                transactionService.getNextTransactionId(),
                accountId,
                dto.type(),
                dto.amount(),
                LocalDateTime.now(),
                dto.description()
        );
    }
}
