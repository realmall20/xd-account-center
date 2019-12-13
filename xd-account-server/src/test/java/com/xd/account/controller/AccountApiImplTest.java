package com.xd.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xd.account.AccountApplication;
import com.xd.account.entity.Account;
import com.xd.account.repository.AccountRepository;
import com.xd.account.request.BalanceInReq;
import com.xd.core.enums.CoinType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class)
@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application-integrationtest.yaml")
public class AccountApiImplTest {
    public static final int INCOME_SOCIATY_ID = 123;
    public static final int INCOME_UID = 123;
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private AccountRepository employeeRepository;
    @Test
    public void testInsert(){
        for(int i=0;i<100;i++){
            Account account=new Account();
            account.setUid(100+i);
            account.setLock(BigDecimal.ZERO);
            account.setFreeze(BigDecimal.ZERO);
            account.setBalance(BigDecimal.ZERO);
            account.setCoinType(CoinType.PT_COIN.getCode());
            account.setId(account.getCoinType()+"_"+account.getUid());
            employeeRepository.save(account);
        }
    }
    /**
     * 测试入账
     * @throws Exception
     */
    @Test
    public void testBalanceWithoutSocity() throws Exception{
        BalanceInReq req=new BalanceInReq();
        req.setAmount(BigDecimal.valueOf(1000L));
        req.setIncomeUid(INCOME_UID);
        req.setReason("测试数据入账");
        mvc.perform(post("/account/balance/in").content(objectMapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    /**
     * 测试有公会信息的入账
     * @throws Exception
     */
    @Test
    public void testBalanceWithSocity() throws Exception{
        BalanceInReq req=new BalanceInReq();
        req.setAmount(BigDecimal.valueOf(1000L));
        req.setIncomeUid(INCOME_UID);
        req.setReason("测试数据入账");
        req.setIncomeSociatyId(INCOME_SOCIATY_ID);
        req.setIncomeAnchorShareRate(BigDecimal.valueOf(30));
        req.setIncomeSocietyShareRate(BigDecimal.valueOf(50));
        mvc.perform(post("/account/balance/in").content(objectMapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    void transfer() {
    }
}