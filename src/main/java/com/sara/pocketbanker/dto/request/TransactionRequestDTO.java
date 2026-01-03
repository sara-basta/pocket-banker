package com.sara.pocketbanker.dto.request;

import com.sara.pocketbanker.entity.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequestDTO(
        @NotNull
        TransactionType type,
        @Positive
        @NotNull
        double amount,
        @NotBlank
        String description
) {
}
