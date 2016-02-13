package com.ekocaman.demo.response;

import com.ekocaman.demo.model.Transaction;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableTransactionResponse.class)
@JsonDeserialize(as = ImmutableTransactionResponse.class)
public abstract class TransactionResponse {

    public abstract Double getAmount();

    public abstract String getType();

    public abstract Optional<Long> getParentId();

    public static ImmutableTransactionResponse withTransaction(Transaction transaction) {
        return ImmutableTransactionResponse.builder()
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .parentId(Optional.ofNullable(transaction.getParentId()))
                .build();
    }
}
