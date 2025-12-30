package com.sara.pocketbanker.service;


import com.sara.pocketbanker.dto.response.AccountResponse;
import com.sara.pocketbanker.dto.response.AccountResponseMapper;
import com.sara.pocketbanker.exception.ResourceNotFoundException;
import com.sara.pocketbanker.model.Account;
import com.sara.pocketbanker.model.AccountType;
import com.sara.pocketbanker.model.Transaction;
import com.sara.pocketbanker.model.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final TransactionService transactionService;
    private final AccountResponseMapper accountResponseMapper;
    List<Account> accounts = new ArrayList<>(List.of(
            new Account("A12","Sara",20000, AccountType.PERSONAL, LocalDate.now(),true,new ArrayList<>()),
            new Account("A13","Basta",10000, AccountType.SAVINGS, LocalDate.now(),true,new ArrayList<>()),
            new Account("A14","Malika",26000, AccountType.PERSONAL, LocalDate.now(),true,new ArrayList<>())
    ));

    public AccountService(TransactionService transactionService, AccountResponseMapper accountResponseMapper) {
        this.transactionService = transactionService;
        this.accountResponseMapper = accountResponseMapper;
    }

    public AccountResponse getAccountDetails(String id) {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(id))
                .findFirst()
                .map(accountResponseMapper)
                .orElseThrow(() -> new ResourceNotFoundException("No account found with this id: "+id));
    }

    public void createAccount(Account account) {
        if(account.getBalance()<0){
            System.out.println("Balance must be positive!");
            return;
        }
        accounts.add(account);
        System.out.println("Account created with success!");
    }

    public void depositMoney(String id, double deposit) {
        Account account = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No account found with this id: "+id));
        if(deposit < 0) {
            throw new IllegalArgumentException("deposit amount must be positive");
        }
        account.setBalance(account.getBalance()+deposit);
        Transaction tr = transactionService.recordTransaction(
                id,
                TransactionType.DEPOSIT,
                deposit,
                "Deposit"
        );
        account.getTransactions().add(tr);

        System.out.println("Money deposited with success!");
    }

    public void withdrawMoney(String id, double withdraw) {
        Account account = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No account found with this id: "+id));        if(withdraw > account.getBalance() || withdraw < 0) {
            throw new IllegalArgumentException("withdraw amount invalid or balance insufficient");
        }
        account.setBalance(account.getBalance()-withdraw);
        Transaction tr = transactionService.recordTransaction(
                id,
                TransactionType.WITHDRAWAL,
                withdraw,
                "Withdraw"
        );
        account.getTransactions().add(tr);

        System.out.println("Money withdrawn with success!");
    }
}
