package com.n26.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorMessage {

    @JsonProperty
    String message;
    @JsonProperty("errors")
    List<ErrorDetail> errorDetails;

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "message='" + message + '\'' +
                ", errorDetails=" + errorDetails +
                '}';
    }
}
