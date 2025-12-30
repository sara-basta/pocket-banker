package com.sara.pocketbanker.dto.response;

import com.sara.pocketbanker.model.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountResponseMapper implements Function<Account,AccountResponse> {
    @Override
    public AccountResponse apply(Account acc) {
        return new AccountResponse(
                acc.getAccountNumber(),
                acc.getAccountHolderName(),
                acc.getBalance(),
                acc.getType(),
                acc.getTransactions().stream().toList()
        );
    }
}
