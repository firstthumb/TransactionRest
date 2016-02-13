package com.ekocaman.demo.service;

import com.ekocaman.demo.model.Transaction;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface TransactionService {

    /**
     * Save transaction and returns true if it is saved successfully otherwise returns false
     *
     * @param transaction Transaction
     * @throws IllegalArgumentException if transactionId is null or transaction
     */
    public boolean saveTransaction(@NotNull Transaction transaction);

    /**
     * Find transaction by id
     *
     * @param transactionId Transaction Id
     * @return Transaction found if not returns null
     * @throws IllegalArgumentException if transactionId is null
     */
    public Transaction findByTransactionId(@NotNull Long transactionId);

    /**
     * Find transaction Id list
     *
     * @param type Transaction type
     * @return List of transaction id
     * @throws IllegalArgumentException if type is null
     */
    public List<Long> findTransactionIdsByTransactionType(@NotNull String type);

    /**
     * Sum of transaction amounts
     *
     * @param parentId Parent Id
     * @return
     * @throws IllegalArgumentException if parentId is null
     */
    public double findSumOfTransactionAmountsByParentId(@NotNull Long parentId);
}
