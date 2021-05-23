package com.transactionprocessor.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private BigDecimal amount;
    private ZonedDateTime timestamp;
}
