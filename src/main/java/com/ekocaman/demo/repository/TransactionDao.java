package com.ekocaman.demo.repository;

import com.ekocaman.demo.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class TransactionDao {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionDao.class);

    private Map<Long, Transaction> transactions = new HashMap<>();

    public boolean saveTransaction(Transaction transaction) {
        LOG.info("Saving transaction ==> {}", transaction);

        Objects.requireNonNull(transaction, "Transaction cannot be null");
        Objects.requireNonNull(transaction.getTransactionId(), "TransactionID cannot be null");
        Objects.requireNonNull(transaction.getAmount(), "Amount cannot be null");
        Objects.requireNonNull(transaction.getType(), "Type cannot be null");

        transactions.put(transaction.getTransactionId(), transaction);

        return true;
    }

    public List<Long> findTransactionIdsByTransactionType(String transactionType) {
        LOG.info("Finding transaction by type : {}", transactionType);

        // TODO: Implement

        return null;
    }

    public double findSumOfTransactionAmountsByParentId(Long parentId) {
        LOG.info("Finding sum of transaction amount by parentId : {}", parentId);

        // TODO: Implement

        return 0D;
    }

    public Transaction findTransactionById(Long transactionId) {
        LOG.info("Finding transaction by id : {}", transactionId);

        // TODO: Implement

        return null;
    }

    public void clear() {
        transactions.clear();
    }
}
