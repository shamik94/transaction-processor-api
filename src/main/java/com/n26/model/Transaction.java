package com.n26.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private BigDecimal amount;
    private LocalDateTime timestamp;
}
