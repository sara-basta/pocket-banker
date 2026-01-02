package com.sara.pocketbanker.dto.request;

import com.sara.pocketbanker.entity.TransactionType;

public record TransactionRequestDTO(
        TransactionType type,
        double amount,
        String description
) {
}
