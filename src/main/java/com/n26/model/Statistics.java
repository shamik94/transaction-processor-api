package com.n26.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Statistics {

    BigDecimal sum;
    BigDecimal avg;
    BigDecimal max;
    BigDecimal min;
    Long count;
}
