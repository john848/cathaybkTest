package com.example.demo.service.impl;

import com.example.demo.entity.FiatMoneyInfo;
import com.example.demo.model.*;
import com.example.demo.repository.FiatMoneyInfoRepository;
import com.example.demo.service.FiatNameService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FiatMoneyInfoServiceImpl implements FiatNameService {

    private final FiatMoneyInfoRepository fiatMoneyInfoRepository;

    private final String url = "https://api.coindesk.com/v1/bpi/currentprice.json";


    public FiatMoneyInfoServiceImpl(FiatMoneyInfoRepository fiatMoneyInfoRepository) {
        this.fiatMoneyInfoRepository = fiatMoneyInfoRepository;
    }

    @Override
    public FiatMoneyInfo create(FiatMoneyInfo fiatMoneyInfo) {
        return fiatMoneyInfoRepository.save(fiatMoneyInfo);
    }

    @Override
    public void delete(String enName) {
        fiatMoneyInfoRepository.deleteById(enName);
    }

    @Override
    public FiatMoneyInfo update(FiatMoneyInfo fiatMoneyInfo) {
        Optional<FiatMoneyInfo> fiatMoneyInfoOptional = fiatMoneyInfoRepository.findById(fiatMoneyInfo.getFiatMoneyNameEn());
        if (StringUtils.isNotBlank(getFiatCnName(fiatMoneyInfo.getFiatMoneyNameEn()))) {
            return fiatMoneyInfoRepository.save(fiatMoneyInfo);
        } else {//id不存在時拋出錯誤
            throw new RuntimeException("fiat money name is not exist");
        }
    }

    @Override
    public String getFiatCnName(String enName) {
        Optional<FiatMoneyInfo> fiatMoneyInfoOptional = fiatMoneyInfoRepository.findById(enName);
        if (fiatMoneyInfoOptional.isPresent()) {
            return fiatMoneyInfoOptional.get().getFiatMoneyNameCn();
        } else {
            return null;
        }
    }

    @Override
    public CurrentPriceVO getOrigingCoinDesk() throws Exception {
        HttpGet get = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(get);
        String response = EntityUtils.toString(httpResponse.getEntity());
        return new Gson().fromJson(response, CurrentPriceVO.class);
    }

    @Override
    public Iterable<FiatMoneyInfo> getAllFiatCnName() {
        return fiatMoneyInfoRepository.findAll();
    }

    @Override
    public RateInfoVO getNewCoinDesk() throws Exception {
        List<RateDetailVO> rateDetails = new ArrayList<>();
        CurrentPriceVO currentPriceVO = getOrigingCoinDesk();
        Class<BpiVO> bpiVOClass = BpiVO.class;
        Field[] fields = bpiVOClass.getDeclaredFields();
        for (Field field : fields) {//使用反射處理方便日後擴充幣別
            field.setAccessible(true);
            Object fieldObject = field.get(currentPriceVO.getBpi());
            if (fieldObject instanceof FiatMoneyVO) {
                FiatMoneyVO fiatMoneyVO = (FiatMoneyVO) fieldObject;
                String fiatMoneyNameEn = field.getName();
                String fiatMoneyNameCn = getFiatCnName(fiatMoneyNameEn);
                Float rate = fiatMoneyVO.getRate_float();
                rateDetails.add(new RateDetailVO(fiatMoneyNameEn, fiatMoneyNameCn, rate));
            }
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date updateTime = df.parse(currentPriceVO.getTime().getUpdatedISO());
        return new RateInfoVO(updateTime, rateDetails);
    }
}
