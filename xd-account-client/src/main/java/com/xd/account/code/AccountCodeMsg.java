package com.xd.account.code;

import com.xd.core.web.response.CodeMsg;

/**
 * 账户中心返回的错误码信息
 *
 * @author xiaohei
 * @create 2019-12-11 上午11:58
 **/
public enum  AccountCodeMsg implements CodeMsg {
    BALANCE_NOT_ENOUGH("3004", "餘額不足"),
    //--------新增
    ACCOUNT_OUT_FAIL("14015","账户出账失败");
    ;
    private String code;
    private String msg;

    private AccountCodeMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
