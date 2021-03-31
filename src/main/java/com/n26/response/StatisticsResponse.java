package com.n26.response;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class StatisticsResponse {

    String sum;
    String avg;
    String max;
    String min;
    Long count;
}
