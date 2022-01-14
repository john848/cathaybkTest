package com.example.demo.model;

import lombok.Data;

@Data
public class CurrentPriceVO {
    String chartName;
    BpiVO bpi;
    TimeVO time;
    String disclaimer;
}
