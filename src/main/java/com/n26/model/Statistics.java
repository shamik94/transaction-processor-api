package com.n26.model;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Statistics {

    String sum;
    String avg;
    String max;
    String min;
    Long count;
}
