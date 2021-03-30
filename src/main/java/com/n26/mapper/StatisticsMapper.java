package com.n26.mapper;

import com.n26.model.Statistics;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component
public class StatisticsMapper {

    public Statistics map (BigDecimal max, BigDecimal min, BigDecimal sum, BigDecimal avg, Long count) {
        return Statistics.builder()
                .count(count)
                .avg(formatBigDecimal(avg))
                .sum(formatBigDecimal(sum))
                .max(formatBigDecimal(max))
                .min(formatBigDecimal(min))
                .build();
    }

    public Statistics map () {
        final long ZERO = 0L;
        String zero = formatBigDecimal(new BigDecimal(ZERO));
        return Statistics.builder()
                .count(ZERO)
                .min(zero)
                .max(zero)
                .sum(zero)
                .avg(zero)
                .build();
    }

    private String formatBigDecimal(BigDecimal bigDecimal) {
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        final String COMMA = ",";
        final String BLANK = "";
        return df.format(bigDecimal).replaceAll(COMMA, BLANK);
    }
}
