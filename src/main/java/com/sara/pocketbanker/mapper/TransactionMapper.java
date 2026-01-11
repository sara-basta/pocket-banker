package com.sara.pocketbanker.mapper;

import com.sara.pocketbanker.dto.request.TransactionRequestDTO;
import com.sara.pocketbanker.dto.response.TransactionResponseDTO;
import com.sara.pocketbanker.entity.Transaction;

import java.time.LocalDateTime;

public final class TransactionMapper {

    public static Transaction toEntity(TransactionRequestDTO dto) {
        return new Transaction(
                null,
                null,
                dto.type(),
                dto.amount(),
                LocalDateTime.now(),
                dto.description()
        );
    }

    public static TransactionResponseDTO toResponse(Transaction tr) {
        return new TransactionResponseDTO(
                tr.getTransactionId(),
                tr.getType(),
                tr.getAmount(),
                tr.getTimestamp(),
                tr.getDescription()
        );
    }
}
