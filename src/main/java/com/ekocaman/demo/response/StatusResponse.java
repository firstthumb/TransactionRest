package com.ekocaman.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableStatusResponse.class)
@JsonDeserialize(as = ImmutableStatusResponse.class)
public interface StatusResponse {

    @JsonProperty("status")
    public String getStatus();
}
