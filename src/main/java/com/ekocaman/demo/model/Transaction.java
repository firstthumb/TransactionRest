package com.ekocaman.demo.model;

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
}
