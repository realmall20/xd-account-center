package com.xd.account.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * 平台收入
 *
 * @author xiaohei
 * @create 2019-12-12 下午5:08
 **/
@Data
@Document(collection = "platform_income_day")
public class PlatformIncomeDay {

    @Id
    private String id ;

    private String day;

    private String  coinType;

    private BigDecimal income;

    @Version
    protected Long version;
}
