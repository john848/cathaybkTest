package com.example.demo.domain;

import lombok.Getter;

@Getter
public enum FiatType {
    EUR("歐元"), GBP("英鎊"), USD("美金");

    final String cnName;

    FiatType(String cnName) {
        this.cnName = cnName;
    }
}
