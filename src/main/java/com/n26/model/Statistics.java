package com.n26.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Statistics {

    BigDecimal sum;
    BigDecimal avg;
    BigDecimal max;
    BigDecimal min;
    Long count;
}
