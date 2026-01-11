package com.sara.pocketbanker.service;

import com.sara.pocketbanker.dto.request.TransactionRequestDTO;
import com.sara.pocketbanker.dto.response.TransactionResponseDTO;
import com.sara.pocketbanker.entity.Account;
import com.sara.pocketbanker.exception.ResourceNotFoundException;
import com.sara.pocketbanker.entity.Transaction;
import com.sara.pocketbanker.mapper.TransactionMapper;
import com.sara.pocketbanker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository trRepo;

    public TransactionService(TransactionRepository trRepo) {
        this.trRepo = trRepo;
    }
    public Transaction createTransaction(Account account, TransactionRequestDTO dto) {
        Transaction tr = TransactionMapper.toEntity(dto);

        tr.setAccount(account);
        tr.setTransactionId(generateTransactionId());

        return trRepo.save(tr);
    }

    public List<TransactionResponseDTO> transactionsByAccount(String accountId) {
        return trRepo.findByAccount_AccountNumber(accountId).stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public TransactionResponseDTO transactionsById(String transactionId) {
        Transaction tr = trRepo.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "There is no transaction with this id: " + transactionId
                ));
        return TransactionMapper.toResponse(tr);
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return trRepo.findAll().stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public void deleteTransaction(String transactionId) {
        if (!trRepo.existsById(transactionId)) {
            throw new ResourceNotFoundException("There is no transaction with this id: " + transactionId);
        }
        trRepo.deleteById(transactionId);

    }

    private String generateTransactionId() {
        return "T-" + UUID.randomUUID();
    }

}
