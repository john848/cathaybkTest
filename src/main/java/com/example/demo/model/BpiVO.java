package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BpiVO {
    @JsonProperty("EUR")
    FiatMoneyVO EUR;
    @JsonProperty("GBP")
    FiatMoneyVO GBP;
    @JsonProperty("USD")
    FiatMoneyVO USD;
}
