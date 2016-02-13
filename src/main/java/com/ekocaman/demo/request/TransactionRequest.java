package com.ekocaman.demo.request;

import com.ekocaman.demo.model.Transaction;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableTransactionRequest.class)
@JsonDeserialize(as = ImmutableTransactionRequest.class)
public abstract class TransactionRequest {

    public abstract Double getAmount();

    public abstract String getType();

    public abstract Optional<Long> getParentId();

    public static ImmutableTransactionRequest withTransaction(Transaction transaction) {
        return ImmutableTransactionRequest.builder()
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .parentId(Optional.ofNullable(transaction.getParentId()))
                .build();
    }
}
