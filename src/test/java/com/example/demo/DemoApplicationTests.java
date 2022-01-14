package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.FiatType;
import com.example.demo.entity.FiatMoneyInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by nasheng.yun on 2017/12/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
@AutoConfigureMockMvc
public class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetAllName() throws Exception {// 1.測試呼叫查詢幣別對應表資料 API,並顯示其內容。(無資料)
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/fiat/name"))
                .andExpect(status().isOk());
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testPostName() throws Exception {//2.測試呼叫新增幣別對應表資料 API。
        for (FiatType value : FiatType.values()) {
            FiatMoneyInfo fiatMoneyInfo = new FiatMoneyInfo(value.name(), value.getCnName());
            mvc.perform(MockMvcRequestBuilders.post("/fiat/name")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JSONObject.toJSONString(fiatMoneyInfo)))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void testPutName() throws Exception {//3.測試呼叫更新幣別對應表資料 API,並顯示其內容。(service層有做檢核，無對應資料會拋錯)
        FiatMoneyInfo fiatMoneyInfo = new FiatMoneyInfo(FiatType.USD.name(), FiatType.USD.getCnName());
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/fiat/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(fiatMoneyInfo)))
                .andExpect(status().isOk());
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testDeleteName() throws Exception {//4.測試呼叫刪除幣別對應表資料 API。
        mvc.perform(MockMvcRequestBuilders.delete(String.format("/fiat/name/%s", FiatType.USD.name()))).andExpect(status().isOk());
    }

    @Test
    public void testOriginCoinDesk() throws Exception {//5.測試呼叫 coindesk API,並顯示其內容。
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/fiat/originCoinDesk"))
                .andExpect(status().isOk());
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testNewCoinDesk() throws Exception {//6.測試呼叫資料轉換的 API,並顯示其內容。
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/fiat/newCoinDesk"))
                .andExpect(status().isOk());
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testAll() throws Exception {//測試全部
        testPostName();//新增
        testPutName();//修改
        testDeleteName();//刪除
        testGetAllName();//取得全部
        testOriginCoinDesk();//取得轉換前資料
        testNewCoinDesk();//取得轉換後資料
    }
}