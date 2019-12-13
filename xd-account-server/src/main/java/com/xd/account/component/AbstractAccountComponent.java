package com.xd.account.component;

import com.xd.account.request.BalanceInReq;
import com.xd.account.request.BalanceOutReq;
import com.xd.account.request.TransferReq;
import com.xd.core.enums.CoinType;
import com.xd.core.exception.BizException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 账户相关
 *
 * @author xiaohei
 * @create 2019-12-11 上午11:27
 **/
public abstract class AbstractAccountComponent implements AccountComponent {

}
