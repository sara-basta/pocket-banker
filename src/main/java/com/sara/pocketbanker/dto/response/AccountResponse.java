package com.sara.pocketbanker.dto.response;

import com.sara.pocketbanker.model.AccountType;
import com.sara.pocketbanker.model.Transaction;


import java.util.List;

public record AccountResponse(
        String accountNumber,
        String accountHolderName,
        double balance,
        AccountType type,

        List<Transaction> transactions
) {
}
