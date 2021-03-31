package com.n26.response;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    @NotNull
    private String amount;
    @NotNull
    private String timestamp;
}
