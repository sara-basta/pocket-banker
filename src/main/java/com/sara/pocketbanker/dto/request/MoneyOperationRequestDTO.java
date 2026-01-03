package com.sara.pocketbanker.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MoneyOperationRequestDTO(
        @NotNull
        @Positive
        double amount) {
}
