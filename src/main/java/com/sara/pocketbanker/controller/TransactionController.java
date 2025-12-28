package com.sara.pocketbanker.controller;

import com.sara.pocketbanker.model.Transaction;
import com.sara.pocketbanker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    TransactionService transactionService;

    @Autowired
    TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

//    GET /transactions/{accountId} → List all transactions for an account
    @GetMapping("/transactions/{accountId}")
    List<Transaction> transactionsByAccount(@PathVariable String accountId){
        return transactionService.transactionsByAccount(accountId);
    }
}
