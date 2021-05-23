package com.transactionprocessor.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetail {

    @JsonProperty
    String resource;
    @JsonProperty
    String field;
    @JsonProperty
    String reason;

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "resource='" + resource + '\'' +
                ", field='" + field + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
