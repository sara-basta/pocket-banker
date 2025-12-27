package com.sara.pocketbanker.controller;

import com.sara.pocketbanker.entity.Account;
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

//    GET /accounts/{id} → Get account details
    @GetMapping("/accounts/{id}")
    Account getAccountDetails(@PathVariable String id){
        return accountService.getAccountDetails(id);
    }

//    POST /accounts → Create a new account
    @PostMapping("/accounts")
    void createAccount(@RequestBody Account account){
        accountService.createAccount(account);
    }

//    PUT /accounts/{id}/deposit → Deposit money
    @PutMapping("/accounts/{id}/deposit")
    void depositMoney(@PathVariable String id, @RequestBody double deposit){
        accountService.depositMoney(id,deposit);
    }

//    PUT /accounts/{id}/withdraw → Withdraw money
    @PutMapping("/accounts/{id}/withdraw")
    void withdrawMoney(@PathVariable String id, @RequestBody double withdraw){
        accountService.withdrawMoney(id,withdraw);
    }
}
