package com.xd.account.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户表
 * @author kerns
 * @since 2019-11-26
 */
@Data
@Document(collection = "account")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id ;

    private String coinType;

    private Integer uid;

    private BigDecimal Balance;

    private BigDecimal freeze;

    private BigDecimal lock;

    @Version
    protected Long version;
}
