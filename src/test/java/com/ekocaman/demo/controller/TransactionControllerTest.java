package com.ekocaman.demo.controller;

import com.ekocaman.demo.config.AppConfig;
import com.ekocaman.demo.model.Transaction;
import com.ekocaman.demo.repository.TransactionDao;
import com.ekocaman.demo.request.ImmutableTransactionRequest;
import com.ekocaman.demo.request.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class TransactionControllerTest {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void tearDown() throws Exception {
        transactionDao.clear();
    }

    //region SAVE TRANSACTION

    @Test
    public void saveTransactionSuccessfully() throws Exception {
        Long transactionId = 10L;
        String url = String.format("/transactionservice/transaction/%s", transactionId);

        TransactionRequest request = ImmutableTransactionRequest.builder()
                .amount(10D)
                .type("cars")
                .build();

        String response = mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(is("ok")))
                .andReturn().getResponse().getContentAsString();

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void saveTransactionAndOverwriteSuccessfully() throws Exception {
        Long transactionId = 10L;
        String url = String.format("/transactionservice/transaction/%s", transactionId);

        {
            TransactionRequest request = ImmutableTransactionRequest.builder()
                    .amount(10D)
                    .type("cars")
                    .build();

            String response = mvc.perform(put(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(is("ok")))
                    .andReturn().getResponse().getContentAsString();

            assertThat(response, is(notNullValue()));
        }

        {
            TransactionRequest request = ImmutableTransactionRequest.builder()
                    .amount(20D)
                    .type("bikes")
                    .build();

            String response = mvc.perform(put(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(is("ok")))
                    .andReturn().getResponse().getContentAsString();

            assertThat(response, is(notNullValue()));
        }

        Transaction transaction = transactionDao.findTransactionById(transactionId);

        assertThat(transaction.getAmount(), is(20D));
        assertThat(transaction.getType(), is("bikes"));
        assertThat(transaction.getParentId(), is(nullValue()));

        transactionDao.clear();
    }

    @Test
    public void saveTransactionWithoutAmountFail() throws Exception {
        Long transactionId = 10L;
        String url = String.format("/transactionservice/transaction/%s", transactionId);

        String payload = "{ \"type\": \"cars\" }";

        String response = mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void saveTransactionWithoutTypeFail() throws Exception {
        Long transactionId = 10L;
        String url = String.format("/transactionservice/transaction/%s", transactionId);

        String payload = "{ \"amount\": 5000 }";

        String response = mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void saveTransactionWithNonExistingParentIdSuccessfully() throws Exception {
        Long transactionId = 10L;
        String url = String.format("/transactionservice/transaction/%s", transactionId);

        TransactionRequest request = ImmutableTransactionRequest.builder()
                .amount(10D)
                .type("cars")
                .parentId(Long.MAX_VALUE)
                .build();

        String response = mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        assertThat(response, is(notNullValue()));
    }

    //endregion

    @Test
    public void getTransactionByTypeSuccessfully() throws Exception {
        for (long i = 1; i <= 10; i++) {
            Transaction transaction = Transaction.builder()
                    .transactionId(i)
                    .amount(10D)
                    .type("cars")
                    .build();

            transactionDao.saveTransaction(transaction);
        }

        String type = "cars";
        String url = String.format("/transactionservice/types/%s", type);

        String response = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn().getResponse().getContentAsString();

        assertThat(response, is(notNullValue()));

        transactionDao.clear();
    }

    @Test
    public void getTransactionByIdSuccessfully() throws Exception {
        for (long i = 1; i <= 10; i++) {
            Transaction transaction = Transaction.builder()
                    .transactionId(i)
                    .amount(i * 2D)
                    .type("cars")
                    .build();

            transactionDao.saveTransaction(transaction);
        }

        Long transactionId = 5L;
        String url = String.format("/transactionservice/transaction/%s", transactionId);

        String response = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(is(10D)))
                .andExpect(jsonPath("$.type").value(is("cars")))
                .andExpect(jsonPath("$.parent_id").doesNotExist())
                .andReturn().getResponse().getContentAsString();

        assertThat(response, is(notNullValue()));

        transactionDao.clear();
    }

    @Test
    public void getSumOfTransactionByParentIdSuccessfully() throws Exception {
        for (long i = 1; i <= 10; i++) {
            Transaction.TransactionBuilder builder = Transaction.builder()
                    .transactionId(i)
                    .amount(i * 2D)
                    .type("cars");

            if (i > 1) {
                builder.parentId(i - 1);
            }

            Transaction transaction = builder.build();

            transactionDao.saveTransaction(transaction);
        }

        Long parentId = 5L;
        String url = String.format("/transactionservice/sum/%s", parentId);

        String response = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(is(90D)))
                .andReturn().getResponse().getContentAsString();

        assertThat(response, is(notNullValue()));

        transactionDao.clear();
    }
}
