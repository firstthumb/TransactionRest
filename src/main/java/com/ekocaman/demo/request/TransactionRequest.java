package com.ekocaman.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableTransactionRequest.class)
@JsonDeserialize(as = ImmutableTransactionRequest.class)
public abstract class TransactionRequest {

    @JsonProperty("amount")
    public abstract Double getAmount();

    @JsonProperty("type")
    public abstract String getType();

    @JsonProperty("parent_id")
    public abstract Optional<Long> getParentId();
}
