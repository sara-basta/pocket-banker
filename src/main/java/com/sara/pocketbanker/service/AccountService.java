package com.sara.pocketbanker.service;


import com.sara.pocketbanker.dto.request.AccountRequestDTO;
import com.sara.pocketbanker.dto.request.TransactionRequestDTO;
import com.sara.pocketbanker.dto.response.AccountResponseDTO;
import com.sara.pocketbanker.dto.response.TransactionResponseDTO;
import com.sara.pocketbanker.exception.ResourceNotFoundException;
import com.sara.pocketbanker.entity.Account;
import com.sara.pocketbanker.entity.Transaction;
import com.sara.pocketbanker.entity.TransactionType;
import com.sara.pocketbanker.mapper.AccountMapper;
import com.sara.pocketbanker.mapper.TransactionMapper;
import com.sara.pocketbanker.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class AccountService {

    private final AccountRepository accRepo;

    private final TransactionService transactionService;

    // making the logger static so that all instances of AccountService share the same logger
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);


    public AccountService(TransactionService transactionService, AccountRepository accRepo) {
        this.transactionService = transactionService;
        this.accRepo = accRepo;
    }

    public AccountResponseDTO getAccountDetails(String id) {
        Account acc = accRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No account found with this id: " + id));
        return AccountMapper.toResponse(acc);
    }

    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        Account account = AccountMapper.toEntity(dto);
        account.setAccountNumber(generateAccountNumber());

        accRepo.save(account);

        log.info("Account created successfully!");
        return AccountMapper.toResponse(account);
    }

    public TransactionResponseDTO depositMoney(String id, double deposit) {
        Account account = accRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No account found with this id: " + id));
        account.setBalance(account.getBalance()+deposit);
        Transaction tr = transactionService.createTransaction(
                account,
                new TransactionRequestDTO(TransactionType.DEPOSIT,
                        deposit,
                        "Deposit"
                )
        );
//        account.getTransactions().add(tr);
        accRepo.save(account);
        log.info("Money deposited: accountId={}, amount={}", id, deposit);
        return TransactionMapper.toResponse(tr);
    }

    public TransactionResponseDTO withdrawMoney(String id, double withdraw) {
        Account account = accRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No account found with this id: " + id));
        if(withdraw > account.getBalance()) {
            throw new IllegalArgumentException("Balance insufficient");
        }
        account.setBalance(account.getBalance()-withdraw);
        Transaction tr = transactionService.createTransaction(
                account,
                new TransactionRequestDTO(TransactionType.WITHDRAWAL,
                        withdraw,
                        "Withdraw"
                )
        );
//        account.getTransactions().add(tr);
        accRepo.save(account);
        log.info("Money withdrawn: accountId={}, amount={}",id, withdraw);
        return TransactionMapper.toResponse(tr);
    }


    public void deleteAccount(String accountId) {
        if (!accRepo.existsById(accountId)) {
            throw new ResourceNotFoundException("No account found with this id: " + accountId);
        }
        accRepo.deleteById(accountId);
        log.info("Account deleted: {}", accountId);
    }

    public List<AccountResponseDTO> getAllAccounts() {
        return accRepo.findAll().stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    public String generateAccountNumber() {
        return "A-" + UUID.randomUUID();
    }
}
