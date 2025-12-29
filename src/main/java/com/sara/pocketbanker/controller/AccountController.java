package com.sara.pocketbanker.controller;

import com.sara.pocketbanker.model.Account;
import com.sara.pocketbanker.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {
    AccountService accountService;

    @Autowired
    AccountController(AccountService account){
        this.accountService = account;
    }

    @GetMapping("/accounts/{id}")
    Account getAccountDetails(@PathVariable String id){
        return accountService.getAccountDetails(id);
    }

    @PostMapping("/accounts")
    void createAccount(@RequestBody Account account){
        accountService.createAccount(account);
    }

    @PutMapping("/accounts/{id}/deposit")
    void depositMoney(@PathVariable String id, @RequestBody double deposit){
        accountService.depositMoney(id,deposit);
    }

    @PutMapping("/accounts/{id}/withdraw")
    void withdrawMoney(@PathVariable String id, @RequestBody double withdraw){
        accountService.withdrawMoney(id,withdraw);
    }
}
