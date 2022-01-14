package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fiat_money_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiatMoneyInfo {

    @Id
    @NotNull
    @Column(name = "fiat_money_name_en", nullable = false)
    String fiatMoneyNameEn;

    @Column(name = "fiat_money_name_cn", nullable = false)
    @NotNull
    String fiatMoneyNameCn;

}
