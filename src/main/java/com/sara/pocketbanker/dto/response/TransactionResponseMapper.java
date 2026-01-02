package com.sara.pocketbanker.dto.response;

import com.sara.pocketbanker.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TransactionResponseMapper implements Function<Transaction,TransactionResponseDTO> {
    @Override
    public TransactionResponseDTO apply(Transaction tr) {
        return new TransactionResponseDTO(
                tr.getTransactionId(),
                tr.getType(),
                tr.getAmount(),
                tr.getTimestamp(),
                tr.getDescription()
        );
    }
}
