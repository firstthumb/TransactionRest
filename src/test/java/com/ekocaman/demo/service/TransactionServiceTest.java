package com.ekocaman.demo.service;

import com.ekocaman.demo.config.AppConfig;
import com.ekocaman.demo.model.Transaction;
import com.ekocaman.demo.repository.TransactionDao;
import com.ekocaman.demo.request.ImmutableTransactionRequest;
import com.ekocaman.demo.request.TransactionRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppConfig.class)
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionDao transactionDao;

    @Before
    public void setUp() throws Exception {
        {
            Transaction transaction = Transaction.builder()
                    .transactionId(1L)
                    .amount(10D)
                    .type("cars")
                    .build();

            transactionDao.saveTransaction(transaction);
        }

        {
            Transaction transaction = Transaction.builder()
                    .transactionId(2L)
                    .amount(20D)
                    .type("cars")
                    .parentId(1L)
                    .build();

            transactionDao.saveTransaction(transaction);
        }

        {
            Transaction transaction = Transaction.builder()
                    .transactionId(3L)
                    .amount(5D)
                    .type("cars")
                    .parentId(2L)
                    .build();

            transactionDao.saveTransaction(transaction);
        }

        {
            Transaction transaction = Transaction.builder()
                    .transactionId(4L)
                    .amount(1D)
                    .type("cars")
                    .parentId(1L)
                    .build();

            transactionDao.saveTransaction(transaction);
        }

        {
            Transaction transaction = Transaction.builder()
                    .transactionId(5L)
                    .amount(2D)
                    .type("cars")
                    .parentId(3L)
                    .build();

            transactionDao.saveTransaction(transaction);
        }

    }

    @After
    public void tearDown() throws Exception {
        transactionDao.clear();
    }

    //region SAVE TRANSACTION

    @Test
    public void saveTransactionSuccessfully() {
        Transaction transaction = Transaction.builder()
                .transactionId(10L)
                .amount(2D)
                .type("cars")
                .build();

        boolean isSaved = transactionService.saveTransaction(transaction);

        assertThat(Boolean.TRUE, is(isSaved));
    }

    @Test
    public void saveTransactionWithExistingOneSuccessfully() {
        Transaction transaction = Transaction.builder()
                .transactionId(11L)
                .amount(2D)
                .type("cars")
                .build();

        // Save first transaction
        boolean isSaved = transactionService.saveTransaction(transaction);
        assertThat(Boolean.TRUE, is(isSaved));

        Transaction transactionWithSameId = Transaction.builder()
                .transactionId(11L)
                .amount(10D)
                .type("bikes")
                .build();

        // Overwrite the existing transaction with new one
        isSaved = transactionService.saveTransaction(transactionWithSameId);
        assertThat(Boolean.TRUE, is(isSaved));

        // Fetch the latest transaction
        Transaction updatedTransaction = transactionService.findByTransactionId(transaction.getTransactionId());
        assertThat(updatedTransaction.getAmount(), is(transactionWithSameId.getAmount()));
        assertThat(updatedTransaction.getType(), is(transactionWithSameId.getType()));
    }

    @Test(expected = NullPointerException.class)
    public void saveTransactionWithNullTransactionIdAndFails() {
        Transaction transaction = Transaction.builder()
                .transactionId(null)
                .amount(2D)
                .type("cars")
                .build();

        transactionService.saveTransaction(transaction);
    }

    @Test(expected = NullPointerException.class)
    public void saveTransactionWithNullTransactionAndFails() {
        transactionService.saveTransaction(null);
    }

    //endregion

    //region FIND TRANSACTION BY TYPE

    @Test
    public void findTransactionByTypeSuccessfully() {
        // Prepare data for test
        for (long i = 100; i < 110; i++) {
            Transaction transaction = Transaction.builder()
                    .transactionId(i)
                    .amount(2D)
                    .type("cloud")
                    .build();
            transactionService.saveTransaction(transaction);
        }

        List<Long> transactionIds = transactionService.findTransactionIdsByTransactionType("cloud");

        assertThat(transactionIds, is(notNullValue()));
        assertThat(transactionIds, is(not(emptyIterable())));
        for (int i = 0; i < 10; i++) {
            Long transactionId = transactionIds.get(i);
            assertThat(transactionId, is((long) (100 + i)));
        }
    }

    @Test(expected = NullPointerException.class)
    public void findTransactionByNullTypeAndFails() {
        transactionService.findTransactionIdsByTransactionType(null);
    }

    @Test
    public void findTransactionByTypeAndReturnsEmptyResultSuccessfully() {
        List<Long> transactionIds = transactionService.findTransactionIdsByTransactionType("unknown");

        assertThat(transactionIds, is(notNullValue()));
        assertThat(transactionIds, is(emptyIterable()));
    }

    //endregion

    //region FIND TRANSACTION BY ID

    @Test
    public void findTransactionByIdSuccessfully() {
        Transaction transaction = Transaction.builder()
                .transactionId(11L)
                .amount(2D)
                .type("cars")
                .build();

        transactionService.saveTransaction(transaction);

        Transaction savedTransaction = transactionService.findByTransactionId(transaction.getTransactionId());

        assertThat(savedTransaction, is(notNullValue()));
        assertThat(savedTransaction.getTransactionId(), is(transaction.getTransactionId()));
        assertThat(savedTransaction.getAmount(), is(transaction.getAmount()));
        assertThat(savedTransaction.getType(), is(transaction.getType()));
        assertThat(savedTransaction.getParentId(), is(transaction.getParentId()));
        assertThat(savedTransaction, is(transaction));
    }

    @Test(expected = NullPointerException.class)
    public void findTransactionByNullIdAndFails() {
        transactionService.findByTransactionId(null);
    }

    @Test
    public void findTransactionByIdAndReturnsNullSuccessfully() {
        Transaction noTransaction = transactionService.findByTransactionId(Long.MAX_VALUE);

        assertThat(noTransaction, is(nullValue()));
    }

    //endregion

    //region SUM OF AMOUNTS
    @Test
    public void sumOfParentTransaction1Successfully() {
        double sum = transactionService.findSumOfTransactionAmountsByParentId(1L);

        assertThat(sum, is(38d));
    }

    @Test
    public void sumOfParentTransaction4Successfully() {
        double sum = transactionService.findSumOfTransactionAmountsByParentId(4L);

        assertThat(sum, is(1d));
    }

    @Test
    public void sumOfParentTransaction3Successfully() {
        double sum = transactionService.findSumOfTransactionAmountsByParentId(3L);

        assertThat(sum, is(7d));
    }

    @Test(expected = NullPointerException.class)
    public void sumOfParentTransactionWithNullParameter() {
        transactionService.findSumOfTransactionAmountsByParentId(null);
    }
    //endregion
}
