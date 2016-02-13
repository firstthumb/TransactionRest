package com.ekocaman.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSumResponse.class)
@JsonDeserialize(as = ImmutableSumResponse.class)
public interface SumResponse {

    @JsonProperty("sum")
    public double getSum();
}
