package com.sara.pocketbanker.dto.request;

import com.sara.pocketbanker.entity.Account;
import com.sara.pocketbanker.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

@Service
public class AccountRequestMapper implements Function<AccountRequestDTO,Account> {

    private final AccountService accountService;

    public AccountRequestMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Account apply(AccountRequestDTO dto) {
        return new Account(
                accountService.getNextAccountNumber(),
                dto.accountHolderName(),
                dto.balance(),
                dto.type(),
                LocalDate.now(),
                true,
                new ArrayList<>()
        );
    }
}
