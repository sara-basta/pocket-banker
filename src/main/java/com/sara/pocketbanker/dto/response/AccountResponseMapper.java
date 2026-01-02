package com.sara.pocketbanker.dto.response;

import com.sara.pocketbanker.entity.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountResponseMapper implements Function<Account, AccountResponseDTO> {
    @Override
    public AccountResponseDTO apply(Account acc) {
        return new AccountResponseDTO(
                acc.getAccountNumber(),
                acc.getAccountHolderName(),
                acc.getBalance(),
                acc.getType(),
                acc.getTransactions().stream().toList()
        );
    }
}
