package com.example.demo.controller;

import com.example.demo.entity.FiatMoneyInfo;
import com.example.demo.model.*;
import com.example.demo.service.FiatNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/fiat")
public class FiatController {

    private final FiatNameService fiatNameService;

    @Autowired
    public FiatController(FiatNameService fiatNameService) {
        this.fiatNameService = fiatNameService;
    }

    @GetMapping("/name/{enName}")
    @ResponseBody
    public String getName(@PathVariable("enName") String enName) {
        return fiatNameService.getFiatCnName(enName);
    }

    @GetMapping("/name")
    @ResponseBody
    public Iterable<FiatMoneyInfo> getAllName() {
        return fiatNameService.getAllFiatCnName();
    }

    @PostMapping("/name")
    @ResponseBody
    public FiatMoneyInfo postName(@RequestBody @Valid FiatMoneyInfo fiatMoneyInfo) {
        return fiatNameService.create(fiatMoneyInfo);
    }

    @PutMapping("/name")
    @ResponseBody
    public FiatMoneyInfo putName(@RequestBody @Valid FiatMoneyInfo fiatMoneyInfo) {
        return fiatNameService.update(fiatMoneyInfo);
    }

    @DeleteMapping("/name/{enName}")
    @ResponseBody
    public String deleteName(@PathVariable("enName") String enName) {
        fiatNameService.delete(enName);
        return "success";
    }

    @GetMapping("/newCoinDesk")
    @ResponseBody
    public RateInfoVO demo() throws Exception {
        return fiatNameService.getNewCoinDesk();
    }

    @GetMapping("originCoinDesk")
    @ResponseBody
    public CurrentPriceVO originCoinDesk() throws Exception {
        return fiatNameService.getOrigingCoinDesk();
    }
}
