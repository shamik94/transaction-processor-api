package com.n26.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
public class Statistics {

    BigDecimal sum;
    BigDecimal avg;
    BigDecimal max;
    BigDecimal min;
    Long count;
}
