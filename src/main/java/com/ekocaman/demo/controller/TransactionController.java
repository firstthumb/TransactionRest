package com.ekocaman.demo.controller;

import com.ekocaman.demo.model.Transaction;
import com.ekocaman.demo.request.TransactionRequest;
import com.ekocaman.demo.response.ImmutableStatusResponse;
import com.ekocaman.demo.response.ImmutableSumResponse;
import com.ekocaman.demo.response.StatusResponse;
import com.ekocaman.demo.response.SumResponse;
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

        // TODO: Implement

        return null;
    }

    @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.GET)
    public TransactionRequest getTransactionById(@PathVariable("transactionId") Long transactionId) {

        LOG.info("Get transaction by Id request got with Transaction Id : {}", transactionId);

        // TODO: Implement

        return null;
    }

    @RequestMapping(value = "/types/{type}", method = RequestMethod.GET)
    public List<Long> getTransactionIdsByTransactionType(@PathVariable("type") String type) {

        LOG.info("Get transaction by type request got with Type : {}", type);

        // TODO: Implement

        return null;
    }

    @RequestMapping(value = "/sum/{parentId}", method = RequestMethod.GET)
    public SumResponse getSumOfTransactionAmountsByParentId(@PathVariable("parentId") Long parentId) {

        LOG.info("Get sum of amounts request got with Parent Id : {}", parentId);

        // TODO: Implement

        return null;
    }
}
