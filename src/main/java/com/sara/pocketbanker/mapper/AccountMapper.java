package com.sara.pocketbanker.mapper;

import com.sara.pocketbanker.dto.request.AccountRequestDTO;
import com.sara.pocketbanker.dto.response.AccountResponseDTO;
import com.sara.pocketbanker.entity.Account;

import java.time.LocalDate;
import java.util.ArrayList;

public final class AccountMapper {
    public AccountMapper() {}

    public static Account toEntity(AccountRequestDTO dto) {
        return new Account(
                null,
                dto.accountHolderName(),
                dto.balance(),
                dto.type(),
                LocalDate.now(),
                true,
                new ArrayList<>()
        );
    }

    public static AccountResponseDTO toResponse(Account acc) {
        return new AccountResponseDTO(
                acc.getAccountNumber(),
                acc.getAccountHolderName(),
                acc.getBalance(),
                acc.getType(),
                acc.getTransactions().stream().toList()
        );
    }
}
