package com.ekocaman.demo.repository;

import com.ekocaman.demo.config.AppConfig;
import com.ekocaman.demo.model.Transaction;
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
public class TransactionDaoTest {

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

        boolean isSaved = transactionDao.saveTransaction(transaction);

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
        boolean isSaved = transactionDao.saveTransaction(transaction);
        assertThat(Boolean.TRUE, is(isSaved));

        Transaction transactionWithSameId = Transaction.builder()
                .transactionId(11L)
                .amount(10D)
                .type("bikes")
                .build();

        // Overwrite the existing transaction with new one
        isSaved = transactionDao.saveTransaction(transactionWithSameId);
        assertThat(Boolean.TRUE, is(isSaved));

        // Fetch the latest transaction
        Transaction updatedTransaction = transactionDao.findTransactionById(transaction.getTransactionId());
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

        transactionDao.saveTransaction(transaction);
    }

    @Test(expected = NullPointerException.class)
    public void saveTransactionWithNullTransactionAndFails() {
        transactionDao.saveTransaction(null);
    }

    //endregion
}
