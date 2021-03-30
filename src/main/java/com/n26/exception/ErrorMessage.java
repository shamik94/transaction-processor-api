package com.n26.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorMessage {

    @JsonProperty
    String message;
    
    @JsonProperty
    Integer code;
}
