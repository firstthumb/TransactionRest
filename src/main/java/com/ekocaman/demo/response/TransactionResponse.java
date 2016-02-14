package com.ekocaman.demo.response;

import com.ekocaman.demo.model.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@JsonSerialize(as = ImmutableTransactionResponse.class)
@JsonDeserialize(as = ImmutableTransactionResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class TransactionResponse {

    @JsonProperty("amount")
    public abstract Double getAmount();

    @JsonProperty("type")
    public abstract String getType();

    @Nullable
    @JsonProperty("parent_id")
    public abstract Long getParentId();

    public static ImmutableTransactionResponse withTransaction(Transaction transaction) {
        return ImmutableTransactionResponse.builder()
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .parentId(transaction.getParentId())
                .build();
    }
}
