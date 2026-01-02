package com.sara.pocketbanker.dto.response;

import com.sara.pocketbanker.entity.AccountType;
import com.sara.pocketbanker.entity.Transaction;


import java.util.List;

public record AccountResponseDTO(
        String accountNumber,
        String accountHolderName,
        double balance,
        AccountType type,

        List<Transaction> transactions
) {
}
