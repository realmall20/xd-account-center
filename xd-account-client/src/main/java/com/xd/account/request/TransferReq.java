package com.xd.account.request;

import com.xd.core.enums.CoinType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 转账请求
 *
 * @author xiaohei
 * @create 2019-12-11 下午2:19
 **/
@Data
@Builder
public class TransferReq {
    @ApiModelProperty(value = "入账的用户ID")
    @NotNull
    private Integer incomeUid;
    @ApiModelProperty(value = "社会ID，没有可以不传")
    private Integer incomeSocietyId;
    @ApiModelProperty(value = "主播分成，不是主播可以不传")
    private Integer  incomeAnchorShareRate;
    @ApiModelProperty(value = "平台和工会分成，没有可以不传")
    private Integer incomeSocietyShareRate;
    @ApiModelProperty(value = "入账的金额")
    @NotNull
    private BigDecimal amount;
    @ApiModelProperty(value = "币种类型")
    private CoinType coinType=CoinType.PT_COIN;
    @ApiModelProperty(value = "入账的理由")
    private String reason;
    @ApiModelProperty(value="出账用户ID")
    private Integer outcomeUid;
}
