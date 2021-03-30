package com.n26.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @NotNull
    private BigDecimal amount;
    @NotNull
    private ZonedDateTime timestamp;
}
