package com.sara.pocketbanker.controller;

import com.sara.pocketbanker.dto.response.TransactionResponseDTO;
import com.sara.pocketbanker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public List<TransactionResponseDTO> transactionsByAccount(@PathVariable String accountId){
        return transactionService.transactionsByAccount(accountId);
    }

    @GetMapping("/transactions/{id}")
    public TransactionResponseDTO transactionsById(@PathVariable String id){
        return transactionService.transactionsById(id);
    }

    @GetMapping("/transactions")
    public List<TransactionResponseDTO> getALlTransactions(){
        return transactionService.getAllTransactions();
    }

    @DeleteMapping("transactions/{trId}/delete")
    public void deleteTransaction(@PathVariable String trId){
        transactionService.deleteTransaction(trId);
    }

}
