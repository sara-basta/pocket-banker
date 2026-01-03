package com.sara.pocketbanker.dto.request;

import com.sara.pocketbanker.entity.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


public record AccountRequestDTO(
        @NotBlank
        String accountHolderName,
        @PositiveOrZero
        double balance,
        @NotNull
        AccountType type
) {

}
