package com.ekocaman.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableTransactionResponse.class)
@JsonDeserialize(as = ImmutableTransactionResponse.class)
public interface TransactionResponse {

    @JsonProperty("amount")
    public Double getAmount();

    @JsonProperty("type")
    public String getType();

    @JsonProperty("parent_id")
    public Optional<Long> getParentId();
}
