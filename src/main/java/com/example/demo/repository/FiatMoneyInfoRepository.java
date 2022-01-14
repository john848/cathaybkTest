package com.example.demo.repository;

import com.example.demo.entity.FiatMoneyInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FiatMoneyInfoRepository extends PagingAndSortingRepository<FiatMoneyInfo, String> {
}
