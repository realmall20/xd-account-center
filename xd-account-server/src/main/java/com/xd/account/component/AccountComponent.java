package com.xd.account.component;

import com.xd.account.request.BalanceInReq;
import com.xd.account.request.BalanceOutReq;
import com.xd.account.request.TransferReq;
import com.xd.core.enums.CoinType;

import java.math.BigDecimal;

/**
 * 账户相关的操作模块
 *
 * @author xiaohei
 * @create 2019-12-11 下午3:25
 **/
public interface AccountComponent {
    /**
     * 资金支持该币种
     * @param coinType
     * @return
     */
    boolean support(CoinType coinType);
    /**
     * 资金出账
     * @param req
     * @return
     */
    BigDecimal balanceOut(BalanceOutReq req);

    /**
     * 资金转账
     * @param req
     * @return
     */
    Boolean trade(TransferReq req);

    /**
     * 资金入账
     * @param req
     * @return
     */
    BigDecimal balanceIn(BalanceInReq req);
}
