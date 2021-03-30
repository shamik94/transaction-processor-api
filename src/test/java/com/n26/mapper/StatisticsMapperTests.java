package com.n26.mapper;

import com.n26.model.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class StatisticsMapperTests {

    @InjectMocks
    StatisticsMapper statisticsMapper;

    @Test
    public void TC001_mapNoArgs() {
        Statistics statistics = statisticsMapper.map();
        long count = statistics.getCount();
        assertEquals(0, count);
        assertEquals("0.00", statistics.getMax());
        assertEquals("0.00", statistics.getMin());
        assertEquals("0.00", statistics.getAvg());
        assertEquals("0.00", statistics.getSum());
    }

    @Test
    public void TC002_mapAllArgs() {
        Statistics statistics = statisticsMapper.map(new BigDecimal(20),
                new BigDecimal(10),
                new BigDecimal(100),
                new BigDecimal(15),
                7L);
        long count = statistics.getCount();
        assertEquals(7, count);
        assertEquals("20.00", statistics.getMax());
        assertEquals("10.00", statistics.getMin());
        assertEquals("15.00", statistics.getAvg());
        assertEquals("100.00", statistics.getSum());
    }
}
