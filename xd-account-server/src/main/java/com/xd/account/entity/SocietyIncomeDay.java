package com.xd.account.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * 公会收入
 *
 * @author xiaohei
 * @create 2019-12-12 下午5:28
 **/
@Data
@Document(collection = "society_income_day")
public class SocietyIncomeDay {
    @Id
    private String id ;
    /**
     * 社会ID
     */
    private Integer societyId;
    /**
     * 时间
     */
    private String day;
    /**
     * 币种类型
     */
    private String  coinType;
    /**
     * 公会收入
     */
    private BigDecimal totalIncome;
    /**
     * 实际公会收入
     */
    private BigDecimal totalRealIncome;
    /**
     * 主播收入
     */
    private BigDecimal anchorIncome;
    /**
     * 公会长收入
     */
    private BigDecimal leaderIncome;

    @Version
    protected Long version;
}
