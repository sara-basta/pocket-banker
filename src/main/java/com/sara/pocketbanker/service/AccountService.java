package com.sara.pocketbanker.service;


import com.sara.pocketbanker.dto.request.AccountRequestDTO;
import com.sara.pocketbanker.dto.request.TransactionRequestDTO;
import com.sara.pocketbanker.dto.response.AccountResponseDTO;
import com.sara.pocketbanker.dto.response.TransactionResponseDTO;
import com.sara.pocketbanker.exception.ResourceNotFoundException;
import com.sara.pocketbanker.entity.Account;
import com.sara.pocketbanker.entity.AccountType;
import com.sara.pocketbanker.entity.Transaction;
import com.sara.pocketbanker.entity.TransactionType;
import com.sara.pocketbanker.mapper.AccountMapper;
import com.sara.pocketbanker.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final TransactionService transactionService;

    // making the logger static so that all instances of AccountService share the same logger
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);


    List<Account> accounts = new ArrayList<>(List.of(
            new Account("A1","Sara",20000, AccountType.PERSONAL, LocalDate.now(),true,new ArrayList<>()),
            new Account("A2","Basta",10000, AccountType.SAVINGS, LocalDate.now(),true,new ArrayList<>())
    ));

    public AccountService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public AccountResponseDTO getAccountDetails(String id) {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(id))
                .findFirst()
                .map(AccountMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("No account found with this id: "+id));
    }

    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        Account account = AccountMapper.toEntity(dto);
        account.setAccountNumber(getNextAccountNumber());

        accounts.add(account);

        log.info("Account created successfully!");
        return AccountMapper.toResponse(account);
    }

    public TransactionResponseDTO depositMoney(String id, double deposit) {
        Account account = getAccount(id);
        account.setBalance(account.getBalance()+deposit);
        Transaction tr = transactionService.createTransaction(
                id,
                new TransactionRequestDTO(TransactionType.DEPOSIT,
                        deposit,
                        "Deposit"
                )
        );
        account.getTransactions().add(tr);

        log.info("Money deposited: accountId={}, amount={}", id, deposit);
        return TransactionMapper.toResponse(tr);
    }

    public TransactionResponseDTO withdrawMoney(String id, double withdraw) {
        Account account = getAccount(id);
        if(withdraw > account.getBalance()) {
            throw new IllegalArgumentException("Balance insufficient");
        }
        account.setBalance(account.getBalance()-withdraw);
        Transaction tr = transactionService.createTransaction(
                id,
                new TransactionRequestDTO(TransactionType.WITHDRAWAL,
                        withdraw,
                        "Withdraw"
                )
        );
        account.getTransactions().add(tr);

        log.info("Money withdrawn: accountId={}, amount={}",id, withdraw);
        return TransactionMapper.toResponse(tr);
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
                .map(AccountMapper::toResponse)
                .toList();
    }

    public String getNextAccountNumber() {
        return "A-" + UUID.randomUUID();
    }
}
