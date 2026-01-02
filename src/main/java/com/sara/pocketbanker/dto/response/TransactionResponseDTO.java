package com.sara.pocketbanker.dto.response;

import com.sara.pocketbanker.entity.TransactionType;

import java.time.LocalDateTime;

public record TransactionResponseDTO(
        String id,
        TransactionType type,
        double amount,
        LocalDateTime timestamp,
        String description) {
}
