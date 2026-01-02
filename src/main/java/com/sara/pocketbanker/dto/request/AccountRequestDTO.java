package com.sara.pocketbanker.dto.request;

import com.sara.pocketbanker.entity.AccountType;


public record AccountRequestDTO(
        String accountHolderName,
        double balance,
        AccountType type
) {

}
