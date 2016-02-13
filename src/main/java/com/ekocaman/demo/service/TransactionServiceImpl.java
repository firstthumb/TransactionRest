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

        // TODO: Implement

        return true;
    }

    @Override
    public Transaction findByTransactionId(Long transactionId) {
        LOG.info("Finding transaction by Id : {}", transactionId);

        // TODO: Implement

        return null;
    }

    @Override
    public List<Long> findTransactionIdsByTransactionType(String type) {
        LOG.info("Finding transaction ids by type : {}", type);

        // TODO: Implement

        return null;
    }

    @Override
    public double findSumOfTransactionAmountsByParentId(Long parentId) {
        LOG.info("Sum of transaction amounts by parent Id : {}", parentId);

        // TODO: Implement

        return 0D;
    }
}
