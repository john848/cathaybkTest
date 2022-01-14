package com.example.demo.service;

import com.example.demo.entity.FiatMoneyInfo;
import com.example.demo.model.CurrentPriceVO;
import com.example.demo.model.RateInfoVO;

public interface FiatNameService {

    FiatMoneyInfo create(FiatMoneyInfo fiatMoneyInfo);

    void delete(String enName);

    FiatMoneyInfo update(FiatMoneyInfo fiatMoneyInfo);

    String getFiatCnName(String enName);
    
    CurrentPriceVO getOrigingCoinDesk() throws Exception;

    Iterable<FiatMoneyInfo> getAllFiatCnName();

    RateInfoVO getNewCoinDesk() throws Exception;
}
