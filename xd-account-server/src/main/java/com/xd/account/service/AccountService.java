package com.xd.account.service;

import com.xd.account.request.BalanceInReq;
import com.xd.account.request.BalanceOutReq;
import com.xd.account.request.TransferReq;

import java.math.BigDecimal;

/**
 * 账户相关的操作
 *
 * @author xiaohei
 * @create 2019-12-11 上午11:25
 **/
public interface AccountService {
    BigDecimal balanceOut(BalanceOutReq req);

    BigDecimal balanceIn(BalanceInReq req);

    Boolean transter(TransferReq req);
}
