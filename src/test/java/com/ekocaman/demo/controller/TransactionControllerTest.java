package com.ekocaman.demo.controller;

import com.ekocaman.demo.config.AppConfig;
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
}
