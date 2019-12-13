package com.xd.account.repository;

import com.xd.account.AccountApplication;
import com.xd.account.entity.Account;
import com.xd.core.enums.CoinType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class)
class AccountRepositoryTest {
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
            account.setId(account.getCoinType()+"_"+account.getId());
            employeeRepository.save(account);
        }
    }
}