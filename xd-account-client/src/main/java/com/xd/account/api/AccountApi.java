package com.xd.account.api;

import com.xd.account.request.BalanceInReq;
import com.xd.account.request.BalanceOutReq;
import com.xd.account.request.TransferReq;
import com.xd.core.web.response.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

/**
 * 资金相关的业务，后期可以放到支付系统里面去
 *
 * @author xiaohei
 * @create 2019-12-11 上午10:52
 **/
@FeignClient(value = "xd-account-server")
public interface AccountApi {
    @PostMapping("/account/out")
    Result<BigDecimal> balanceOut(@RequestBody BalanceOutReq req);

    Result<BigDecimal> balanceIn(BalanceInReq req);

    @ApiOperation(value = "转账")
    @PostMapping(value = "/balance/transfer")
    Result<Boolean> transfer(TransferReq req);
}
