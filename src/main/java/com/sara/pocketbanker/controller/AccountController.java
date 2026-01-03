package com.sara.pocketbanker.controller;

import com.sara.pocketbanker.dto.request.AccountRequestDTO;
import com.sara.pocketbanker.dto.request.MoneyOperationRequestDTO;
import com.sara.pocketbanker.dto.response.AccountResponseDTO;
import com.sara.pocketbanker.dto.response.TransactionResponseDTO;
import com.sara.pocketbanker.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;


@RestController
public class AccountController {
    AccountService accountService;

    AccountController(AccountService account){
        this.accountService = account;
    }

    @GetMapping("/accounts/{id}")
    public AccountResponseDTO getAccountDetails(@PathVariable String id){
        return accountService.getAccountDetails(id);
    }

    @PostMapping("/accounts/add")
    public AccountResponseDTO createAccount(@RequestBody AccountRequestDTO dto){
        return accountService.createAccount(dto);
    }

    @GetMapping("/accounts")
    public List<AccountResponseDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PutMapping("/accounts/{id}/deposit")
    public ResponseEntity<TransactionResponseDTO> depositMoney(@PathVariable String id, @Valid @RequestBody MoneyOperationRequestDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.depositMoney(id, dto.amount()));
    }

    @PutMapping("/accounts/{id}/withdraw")
    public ResponseEntity<TransactionResponseDTO> withdrawMoney(@PathVariable String id, @Valid @RequestBody MoneyOperationRequestDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.withdrawMoney(id, dto.amount()));
    }

    @DeleteMapping("/accounts/{id}/delete")
    public void deleteAccount(@PathVariable String id){
        accountService.deleteAccount(id);
    }

    @DeleteMapping("accounts/{accId}/transactions/{trId}/delete")
    public void deleteTransaction(@PathVariable String accId, @PathVariable String trId){
        accountService.deleteTransaction(accId,trId);
    }
}
