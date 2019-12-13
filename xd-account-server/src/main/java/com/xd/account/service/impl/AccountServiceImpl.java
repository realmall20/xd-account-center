package com.xd.account.service.impl;

import com.xd.account.component.AbstractAccountComponent;
import com.xd.account.request.BalanceInReq;
import com.xd.account.request.BalanceOutReq;
import com.xd.account.request.TransferReq;
import com.xd.account.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 账号服务
 * @author xiaohei
 * @create 2019-12-11 下午1:46
 **/
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private List<AbstractAccountComponent> accountComponentList;
    @Override
    public BigDecimal balanceOut(BalanceOutReq req){
        for(AbstractAccountComponent proxy: accountComponentList){
            if(proxy.support(req.getCoinType())){
                return proxy.balanceOut(req);
            }
        }
        throw  new IllegalStateException("默认币种不存在");
    }

    @Override
    public BigDecimal balanceIn(BalanceInReq req){
        for(AbstractAccountComponent proxy: accountComponentList){
            if(proxy.support(req.getCoinType())){
                return proxy.balanceIn(req);
            }
        }
        throw  new IllegalStateException("默认币种不存在");
    }


    @Override
    public Boolean transter(TransferReq req){
        for(AbstractAccountComponent proxy: accountComponentList){
            if(proxy.support(req.getCoinType())){
                return proxy.trade(req);
            }
        }
        throw  new IllegalStateException("默认币种不存在");
    }


}
