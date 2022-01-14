package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateInfoVO {

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    Date updateTime;

    List<RateDetailVO> rateDetails;

}
