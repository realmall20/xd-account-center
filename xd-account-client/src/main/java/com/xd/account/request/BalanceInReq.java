package com.xd.account.request;

import com.xd.core.enums.CoinType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
/**
 * 资金转入
 *
 * @author xiaohei
 * @create 2019-12-11 下午1:55
 **/
@Data
public class BalanceInReq {
    @ApiModelProperty(value = "入账的用户ID")
    @NotNull
    private Integer incomeUid;
    @ApiModelProperty(value = "社会ID，没有可以不传")
    private Integer incomeSociatyId;
    @ApiModelProperty(value = "公会长用户ID，没有可以不传")
    private Integer incomeSocietyLeaderUid;
    @ApiModelProperty(value = "主播分成，不是主播可以不传")
    private BigDecimal  incomeAnchorShareRate;
    @ApiModelProperty(value = "平台和工会分成，没有可以不传")
    private BigDecimal incomeSocietyShareRate;
    @ApiModelProperty(value = "入账的金额")
    @NotNull
    private BigDecimal amount;
    @ApiModelProperty(value = "币种类型")
    private CoinType coinType=CoinType.PT_COIN;
    @ApiModelProperty(value = "入账的理由")
    private String reason;
}
