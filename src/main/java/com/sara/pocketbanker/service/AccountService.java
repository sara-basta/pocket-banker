package com.sara.pocketbanker.service;

import com.sara.pocketbanker.entity.Account;
import com.sara.pocketbanker.entity.AccountType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    List<Account> accounts = new ArrayList<>(List.of(
            new Account("A12","Sara",20000, AccountType.PERSONAL, LocalDate.now(),true,new ArrayList<>()),
            new Account("A13","Basta",10000, AccountType.SAVINGS, LocalDate.now(),true,new ArrayList<>()),
            new Account("A14","Malika",26000, AccountType.PERSONAL, LocalDate.now(),true,new ArrayList<>())
    ));

    public Account getAccountDetails(String id) {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No account found with this id!"));
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
        Account account = getAccountDetails(id);
        if(deposit < 0) {
            throw new RuntimeException("deposit amount must be positive");
        }
        account.setBalance(account.getBalance()+deposit);
        System.out.println("Money deposited with success!");
    }

    public void withdrawMoney(String id, double withdraw) {
        Account account = getAccountDetails(id);
        if(withdraw > account.getBalance() || withdraw < 0) {
            throw new RuntimeException("withdraw amount invalid or balance insufficient");
        }
        account.setBalance(account.getBalance()-withdraw);
        System.out.println("Money withdrawn with success!");
    }
}
