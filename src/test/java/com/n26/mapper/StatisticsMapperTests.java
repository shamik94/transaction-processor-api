package com.n26.mapper;

import com.n26.response.StatisticsResponse;
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
        StatisticsResponse statisticsResponse = statisticsMapper.map();
        long count = statisticsResponse.getCount();
        assertEquals(0, count);
        assertEquals("0.00", statisticsResponse.getMax());
        assertEquals("0.00", statisticsResponse.getMin());
        assertEquals("0.00", statisticsResponse.getAvg());
        assertEquals("0.00", statisticsResponse.getSum());
    }

    @Test
    public void TC002_mapAllArgs() {
        StatisticsResponse statisticsResponse = statisticsMapper.map(new BigDecimal(20),
                new BigDecimal(10),
                new BigDecimal(100),
                new BigDecimal(15),
                7L);
        long count = statisticsResponse.getCount();
        assertEquals(7, count);
        assertEquals("20.00", statisticsResponse.getMax());
        assertEquals("10.00", statisticsResponse.getMin());
        assertEquals("15.00", statisticsResponse.getAvg());
        assertEquals("100.00", statisticsResponse.getSum());
    }
}
