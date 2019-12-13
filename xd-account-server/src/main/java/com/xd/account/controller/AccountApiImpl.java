package com.xd.account.controller;

import com.xd.account.api.AccountApi;
import com.xd.account.request.BalanceInReq;
import com.xd.account.request.BalanceOutReq;
import com.xd.account.request.TransferReq;
import com.xd.account.service.AccountService;
import com.xd.core.web.response.Result;
import com.xd.core.web.response.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 账户相关的api
 *
 * @author xiaohei
 * @create 2019-12-11 下午2:43
 **/
@RestController
@RequestMapping(value = "/account")
@Api(value = "内部账户", tags = {"内部账户相关"})
public class AccountApiImpl implements AccountApi {
    @Autowired
    private AccountService accountService;
    @Override
    @ApiOperation(value = "资金转出")
    @PostMapping(value = "/balance/out")
    public Result<BigDecimal> balanceOut(@RequestBody BalanceOutReq req) {
        return ResultUtils.success(accountService.balanceOut(req));
    }
    @Override
    @ApiOperation(value = "资金转出")
    @PostMapping(value = "/balance/in")
    public Result<BigDecimal> balanceIn(@RequestBody BalanceInReq req) {
        return ResultUtils.success(accountService.balanceIn(req));
    }

    @Override
    @ApiOperation(value = "转账")
    @PostMapping(value = "/balance/transfer")
    public Result<Boolean> transfer(@RequestBody TransferReq req){
       return ResultUtils.success(accountService.transter(req));
    }


}
