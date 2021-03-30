package com.n26.mapper;

import com.n26.model.Statistics;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StatisticsMapper {

    public Statistics map (BigDecimal max, BigDecimal min, BigDecimal sum, BigDecimal avg, Long count) {
        return Statistics.builder()
                .count(count)
                .avg(avg)
                .sum(sum)
                .max(max)
                .min(min)
                .build();
    }

    public Statistics map () {
        return Statistics.builder().build();
    }
}
