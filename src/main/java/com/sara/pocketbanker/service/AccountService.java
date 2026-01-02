package com.sara.pocketbanker.service;


import com.sara.pocketbanker.dto.response.AccountResponseDTO;
import com.sara.pocketbanker.dto.response.AccountResponseMapper;
import com.sara.pocketbanker.exception.ResourceNotFoundException;
import com.sara.pocketbanker.entity.Account;
import com.sara.pocketbanker.entity.AccountType;
import com.sara.pocketbanker.entity.Transaction;
import com.sara.pocketbanker.entity.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final TransactionService transactionService;
    private final AccountResponseMapper accountResponseMapper;
    List<Account> accounts = new ArrayList<>(List.of(
            new Account("A1","Sara",20000, AccountType.PERSONAL, LocalDate.now(),true,new ArrayList<>()),
            new Account("A2","Basta",10000, AccountType.SAVINGS, LocalDate.now(),true,new ArrayList<>())
    ));

    public AccountService(TransactionService transactionService, AccountResponseMapper accountResponseMapper) {
        this.transactionService = transactionService;
        this.accountResponseMapper = accountResponseMapper;
    }

    public AccountResponseDTO getAccountDetails(String id) {
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
        Account account = getAccount(id);
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
        Account account = getAccount(id);
        if(withdraw > account.getBalance() || withdraw < 0) {
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

    public Account getAccount(String id) {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No account found with this id: "+ id));
    }

    public void deleteAccount(String id) {
        boolean deleted = accounts.removeIf(acc -> acc.getAccountNumber().equals(id));

        if(!deleted){
            throw new ResourceNotFoundException("No account found with this id: "+ id);
        }
    }

    public void deleteTransaction(String accId, String trId) {
        Account account = getAccount(accId);
        List<Transaction> transactions = account.getTransactions();

        boolean removed = transactions.removeIf(tr -> tr.getTransactionId().equals(trId));

        boolean removedGlobal = transactionService.deleteTransactionEntity(trId);

        if(!removed || !removedGlobal) {
            throw  new ResourceNotFoundException("No transaction found for this account with this id : " + trId);
        }
    }

    public List<AccountResponseDTO> getAllAccounts() {
        return accounts.stream()
                .map(accountResponseMapper)
                .toList();
    }

    public String getNextAccountNumber() {
        return "A" + (accounts.size() +1);
    }
}
