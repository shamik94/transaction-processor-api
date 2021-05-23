package com.transactionprocessor.controller;

import com.transactionprocessor.exception.ErrorDetail;
import com.transactionprocessor.exception.ErrorMessage;
import com.transactionprocessor.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Collections;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorMessage statusCode400(HttpServletRequest req, MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        return populateErrorMessage(req, ex);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected ErrorMessage statusCode422(HttpServletRequest req, UnprocessableEntityException ex) {
        log.error(ex.getMessage(), ex);
        return populateErrorMessage(req, ex);
    }

    private ErrorMessage populateErrorMessage(HttpServletRequest req, Exception ex) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setResource(htmlEscapeHelper(req.getRequestURI()));
        errorDetail.setField(htmlEscapeHelper(req.getQueryString()));
        errorDetail.setReason(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setErrorDetails(Collections.singletonList(errorDetail));
        return errorMessage;
    }

    private String htmlEscapeHelper(String input) {
        return (input == null ? null : UriUtils.encode(input, Charset.defaultCharset()));
    }
}
