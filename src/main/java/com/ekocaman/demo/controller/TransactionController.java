package com.ekocaman.demo.controller;

import com.ekocaman.demo.model.Transaction;
import com.ekocaman.demo.request.TransactionRequest;
import com.ekocaman.demo.response.*;
import com.ekocaman.demo.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactionservice")
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.PUT)
    public StatusResponse saveTransaction(
            @PathVariable Long transactionId,
            @RequestBody TransactionRequest transactionRequest) {

        LOG.info("Save transaction request got with Transaction Id : {} and Transaction Request : {}", transactionId, transactionRequest);

        Transaction transaction = Transaction.withTransactionRequest(transactionId, transactionRequest);
        boolean isSuccess = transactionService.saveTransaction(transaction);

        return ImmutableStatusResponse.builder().status(isSuccess ? "ok" : "error").build();
    }

    @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.GET)
    public TransactionResponse getTransactionById(@PathVariable("transactionId") Long transactionId) {

        LOG.info("Get transaction by Id request got with Transaction Id : {}", transactionId);

        Transaction transaction = transactionService.findByTransactionId(transactionId);

        return ImmutableTransactionResponse.withTransaction(transaction);
    }

    @RequestMapping(value = "/types/{type}", method = RequestMethod.GET)
    public List<Long> getTransactionIdsByTransactionType(@PathVariable("type") String type) {

        LOG.info("Get transaction by type request got with Type : {}", type);

        return transactionService.findTransactionIdsByTransactionType(type);
    }

    @RequestMapping(value = "/sum/{parentId}", method = RequestMethod.GET)
    public SumResponse getSumOfTransactionAmountsByParentId(@PathVariable("parentId") Long parentId) {

        LOG.info("Get sum of amounts request got with Parent Id : {}", parentId);

        double sum = transactionService.findSumOfTransactionAmountsByParentId(parentId);

        return ImmutableSumResponse.builder()
                .sum(sum)
                .build();
    }
}
