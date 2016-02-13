package com.ekocaman.demo.service;

import com.ekocaman.demo.model.Transaction;
import com.ekocaman.demo.repository.TransactionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionDao transactionDao;

    @Autowired
    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public boolean saveTransaction(Transaction transaction) {
        LOG.info("Saving transaction ==> {}", transaction);

        Objects.requireNonNull(transaction, "Transaction cannot be null");

        boolean isSuccess = transactionDao.saveTransaction(transaction);
        LOG.info("Save transaction result : {}", isSuccess);

        return isSuccess;
    }

    @Override
    public Transaction findByTransactionId(Long transactionId) {
        LOG.info("Finding transaction by Id : {}", transactionId);

        Objects.requireNonNull(transactionId, "TransactionID cannot be null");

        Transaction transaction = transactionDao.findTransactionById(transactionId);
        LOG.info("Transaction found : {}", transaction);

        return transaction;
    }

    @Override
    public List<Long> findTransactionIdsByTransactionType(String type) {
        LOG.info("Finding transaction ids by type : {}", type);

        Objects.requireNonNull(type, "Transaction type cannot be null");

        return transactionDao.findTransactionIdsByTransactionType(type);
    }

    @Override
    public double findSumOfTransactionAmountsByParentId(Long parentId) {
        LOG.info("Sum of transaction amounts by parent Id : {}", parentId);

        Objects.requireNonNull(parentId, "ParentID cannot be null");

        return transactionDao.findSumOfTransactionAmountsByParentId(parentId);
    }
}
