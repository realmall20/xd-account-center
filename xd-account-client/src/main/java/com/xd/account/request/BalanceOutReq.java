package com.xd.account.request;

import com.xd.core.enums.CoinType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金转出
 *
 * @author xiaohei
 * @create 2019-12-11 下午1:54
 **/
@Data
public class BalanceOutReq {
    private Integer outcomeUid;
    private BigDecimal amount;
    private String reason;
    private CoinType coinType;
}
