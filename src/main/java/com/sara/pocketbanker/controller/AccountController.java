package com.sara.pocketbanker.controller;

import com.sara.pocketbanker.dto.response.AccountResponseDTO;
import com.sara.pocketbanker.entity.Account;
import com.sara.pocketbanker.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController {
    AccountService accountService;

    @Autowired
    AccountController(AccountService account){
        this.accountService = account;
    }

    @GetMapping("/accounts/{id}")
    AccountResponseDTO getAccountDetails(@PathVariable String id){
        return accountService.getAccountDetails(id);
    }

    @PostMapping("/accounts/add")
    void createAccount(@RequestBody Account account){
        accountService.createAccount(account);
    }

    @GetMapping("/accounts")
    List<AccountResponseDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PutMapping("/accounts/{id}/deposit")
    void depositMoney(@PathVariable String id, @RequestBody double deposit){
        accountService.depositMoney(id,deposit);
    }

    @PutMapping("/accounts/{id}/withdraw")
    void withdrawMoney(@PathVariable String id, @RequestBody double withdraw){
        accountService.withdrawMoney(id,withdraw);
    }

    @DeleteMapping("/accounts/{id}/delete")
    void deleteAccount(@PathVariable String id){
        accountService.deleteAccount(id);
    }

    @DeleteMapping("accounts/{accId}/transactions/{trId}/delete")
    void deleteTransaction(@PathVariable String accId, @PathVariable String trId){
        accountService.deleteTransaction(accId,trId);
    }
}
