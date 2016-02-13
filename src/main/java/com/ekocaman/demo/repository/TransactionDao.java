package com.ekocaman.demo.repository;

import com.ekocaman.demo.exc.InvalidParameterException;
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

        if (transaction.getParentId() != null) {
            Transaction parentTransaction = transactions.get(transaction.getParentId());
            if (parentTransaction == null) {
                LOG.warn("Parent transaction {} could not be found", transaction.getTransactionId());
                throw new InvalidParameterException("ParentId could not be found");
            }
        }

        transactions.put(transaction.getTransactionId(), transaction);

        return true;
    }

    public List<Long> findTransactionIdsByTransactionType(String transactionType) {
        LOG.info("Finding transaction by type : {}", transactionType);

        Objects.requireNonNull(transactionType, "Transaction type cannot be null");

        List<Long> result = transactions
                .values()
                .stream()
                .filter(t -> t.getType().equals(transactionType))
                .map(Transaction::getTransactionId)
                .collect(Collectors.toList());

        LOG.info("Result size : {}", result.size());

        return result;
    }

    public double findSumOfTransactionAmountsByParentId(Long parentId) {
        LOG.info("Finding sum of transaction amount by parentId : {}", parentId);

        Objects.requireNonNull(parentId, "Parent Id cannot be null");

        Transaction parentTransaction = transactions.get(parentId);

        BigDecimal totalSum = traverse(parentId).add(new BigDecimal(String.valueOf(parentTransaction.getAmount())));

        return totalSum.doubleValue();
    }

    private BigDecimal traverse(Long parentId) {
        LOG.info("Looking for parent Id : {}", parentId);
        BigDecimal sum = BigDecimal.ZERO;

        for (Transaction transaction : transactions.values()) {
            if (transaction.getParentId() != null
                    && transaction.getParentId().equals(parentId)) {
                sum = sum.add(new BigDecimal(String.valueOf(transaction.getAmount())));
                sum = sum.add(traverse(transaction.getTransactionId()));
            }
        }

        return sum;
    }

    public Transaction findTransactionById(Long transactionId) {
        LOG.info("Finding transaction by id : {}", transactionId);

        Objects.requireNonNull(transactionId, "Transaction Id cannot be null");

        Transaction transaction = transactions.get(transactionId);
        LOG.info("Transaction found : {}", transaction);

        return transaction;
    }

    public void clear() {
        transactions.clear();
    }
}
