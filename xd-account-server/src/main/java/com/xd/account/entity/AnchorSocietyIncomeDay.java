package com.xd.account.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * 主播在公会每天的收入
 *
 * @author xiaohei
 * @create 2019-12-12 下午7:45
 **/
@Data
@Document(collection = "anchor_society_income_day")
public class AnchorSocietyIncomeDay {
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
     * 主播ID
     */
    private Integer anchorId;
    /**
     * 金额
     */
    private BigDecimal income;
}
