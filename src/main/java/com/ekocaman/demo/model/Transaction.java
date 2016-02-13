package com.ekocaman.demo.model;

import com.ekocaman.demo.request.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Transaction {
    private final Long transactionId;
    private final Double amount;
    private final String type;
    private Long parentId;

    public static Transaction withTransactionRequest(Long transactionId, TransactionRequest transactionRequest) {
        TransactionBuilder builder = Transaction.builder()
                .transactionId(transactionId)
                .amount(transactionRequest.getAmount())
                .type(transactionRequest.getType());

        transactionRequest.getParentId().ifPresent(builder::parentId);

        return builder.build();
    }
}
