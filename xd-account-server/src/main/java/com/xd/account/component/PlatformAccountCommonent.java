package com.xd.account.component;

import com.mongodb.client.result.UpdateResult;
import com.xd.account.code.AccountCodeMsg;
import com.xd.account.entity.*;
import com.xd.account.repository.AccountRepository;
import com.xd.account.request.BalanceInReq;
import com.xd.account.request.BalanceOutReq;
import com.xd.account.request.TransferReq;
import com.xd.core.enums.CoinType;
import com.xd.core.exception.BizException;
import com.xd.core.web.response.BasicCodeMsg;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * 平台账户，
 * 后续有其它账户直接通过策略分配
 *
 * @author xiaohei
 * @create 2019-12-09 下午3:59
 **/
@Component
public class PlatformAccountCommonent extends AbstractAccountComponent {
    @Resource
    private AccountRepository accountRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    private static final BigDecimal hu = new BigDecimal(100);

    @Override
    public boolean support(CoinType coinType) {
        return CoinType.PT_COIN == coinType;
    }

    /**
     * 入账,计算分成
     *
     * @author xiaohei
     * @create 2019/12/9
     **/
    @Override
    @Transactional(rollbackFor = BizException.class)
    public BigDecimal balanceIn(BalanceInReq req) {
        String id = getId(req.getCoinType().getCode(), req.getIncomeUid());
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.map(account -> {
            account.setBalance(account.getBalance().add(req.getAmount()));
            //保存通过乐观锁进行保证
            //分成计算
            BigDecimal anchorIncome;
            if (req.getIncomeSocietyShareRate() != null) {
                //公会总收入
                anchorIncome=incomeShareWithSociety(req);
            } else {
                anchorIncome=incomeShareWithoutSociety(req);
            }
            Update update=new Update();
            update.inc("balance",anchorIncome);
            mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)),update,Account.class);
            // 添加出入帐记录
            return account.getBalance();
        }).orElseThrow(() -> new BizException(BasicCodeMsg.PARAM_ERROR));
    }

    /**
     * 出账user
     *
     * @param req 用户ID
     * @author xiaohei
     * @create 2019/12/9
     **/
    @Override
    @Transactional(rollbackFor = BizException.class)
    public BigDecimal balanceOut(BalanceOutReq req) {
        String id = getId(req.getCoinType().getCode(), req.getOutcomeUid());
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.map(account -> {
            //先判断金额是否能够扣除费用
            if (account.getBalance().compareTo(req.getAmount()) < 0) {
                throw new BizException(AccountCodeMsg.BALANCE_NOT_ENOUGH);
            }
            BigDecimal balance = account.getBalance().subtract(req.getAmount());
            Update update = new Update();
            update.set("balance", balance);
            UpdateResult updateResult = mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id).and("balance").is(account.getBalance())), update, Account.class);
            //扣减失败，流程回滚
            if (updateResult.getMatchedCount() != 1) {
                throw new BizException(AccountCodeMsg.ACCOUNT_OUT_FAIL);
            }
            // 添加出入帐记录
            return balance;
        }).orElseThrow(() -> new BizException(BasicCodeMsg.PARAM_ERROR));
    }

    /**
     * 转账
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public Boolean trade(TransferReq req) {
        BalanceOutReq balanceOutReq = new BalanceOutReq();
        BeanUtils.copyProperties(req, balanceOutReq);
        BalanceInReq balanceInReq = new BalanceInReq();
        BeanUtils.copyProperties(req, balanceInReq);
        balanceIn(balanceInReq);
        balanceOut(balanceOutReq);
        return Boolean.TRUE;
    }


    /**
     * 和工会，平台进行收入分成
     *
     * @param req
     */
    private BigDecimal incomeShareWithSociety(BalanceInReq req) {
        Date date = new Date();
        String day = DateFormatUtils.format(date, "yyyy-MM-dd");
        //更新平台每天收入
        BigDecimal bossIncome = req.getIncomeAnchorShareRate().multiply(req.getAmount()).divide(hu, 2);
        updatePlatformDayIncome(req, day, bossIncome);
        //更新公会每天收入
        BigDecimal anchorImcome = updateSociety(req, day, bossIncome);
        //更新主播个人在公会每天的收入
        updateAnchorSocietyIncomeDay(req, day, anchorImcome);
        return anchorImcome;
    }

    /**
     * 主播和平台分成
     *
     * @param req
     */
    private BigDecimal incomeShareWithoutSociety(BalanceInReq req) {
        // 没有公会平台抽成30%
        BigDecimal bossIncome = req.getAmount().multiply(new BigDecimal(0.3));
        Date date = new Date();
        String day = DateFormatUtils.format(date, "yyyy-MM-dd");
        //更新平台每天收入
        updatePlatformDayIncome(req, day, bossIncome);
        BigDecimal anchorIncome = req.getAmount().subtract(bossIncome);
        updateAnchorSocietyIncomeDay(req, day, anchorIncome);
        return anchorIncome;
    }

    private String getId(String coinType, Integer uid) {
        return coinType + "_" + uid;
    }

    /**
     * 更新平台纬度每天收入
     *
     * @param req
     * @param day
     * @return
     */
    private void updatePlatformDayIncome(BalanceInReq req, String day, BigDecimal bossIncome) {
        Update update = new Update();
        update.inc("income", bossIncome.doubleValue());
        update.set("day",day);
        update.set("coinType",req.getCoinType().getCode());
        String platformId = StringUtils.joinWith("-", day, req.getCoinType().getCode());
        Query idQuery = new Query(Criteria.where("id").is(platformId));
        mongoTemplate.upsert(idQuery, update, PlatformIncomeDay.class);

    }

    /**
     * 更新公会纬度的每天收入,减去和公会长的分成，返回主播的实际收入
     *
     * @param req
     * @param day
     * @param bossIncome
     * @return
     */
    private BigDecimal updateSociety(BalanceInReq req, String day, BigDecimal bossIncome) {
        String societyId = StringUtils.joinWith("-", req.getCoinType().getCode(), day, String.valueOf(req.getIncomeSociatyId()));
        Query idQuery = new Query(Criteria.where("id").is(societyId));
        BigDecimal societyIncome = req.getAmount().subtract(bossIncome);
        BigDecimal societyLeaderIncome;
        BigDecimal anchorIncome;
        if (req.getIncomeAnchorShareRate() != null) {
            // 公会长收入(5)
            societyLeaderIncome = societyIncome.multiply(req.getIncomeAnchorShareRate()).divide(hu, 2, RoundingMode.HALF_UP);
            //本人不是公会长
            if (!Objects.equals(req.getIncomeSocietyLeaderUid(), req.getIncomeUid())) {
                Update update = new Update();
                update.inc("amount", societyLeaderIncome.doubleValue());
                // 将收入加入公会长账户
                mongoTemplate.updateFirst(new Query(Criteria.where("id").is(getId(req.getCoinType().getCode(), req.getIncomeSocietyLeaderUid()))), update, Account.class);
            }
            anchorIncome = societyIncome.subtract(societyLeaderIncome);
        } else {
            anchorIncome = societyIncome;
            societyLeaderIncome = BigDecimal.ZERO;

        }
        Update update = new Update();
        update.inc("totalRealIncome", societyIncome.doubleValue());
        update.inc("totalIncome", req.getAmount().doubleValue());
        update.inc("leaderIncome", societyLeaderIncome.doubleValue());
        update.inc("anchorIncome", anchorIncome.doubleValue());
        update.set("societyId",req.getIncomeSociatyId());
        update.set("day",day);
        update.set("coinType",req.getCoinType().getCode());
        mongoTemplate.upsert(idQuery, update, SocietyIncomeDay.class);
        return anchorIncome;
    }

    /**
     * 更新主播在公会纬度的每天收入
     *
     * @param req
     * @param day
     * @param anchorImcome
     */
    private void updateAnchorSocietyIncomeDay(BalanceInReq req, String day, BigDecimal anchorImcome) {
        String anchorSocietyId = StringUtils.joinWith("-", req.getCoinType().getCode(), day, String.valueOf(req.getIncomeSociatyId()), String.valueOf(req.getIncomeUid()));
        Query idQuery = new Query(Criteria.where("id").is(anchorSocietyId));
        Update update = new Update();
        update.inc("income", anchorImcome.doubleValue());
        update.set("societyId",req.getIncomeSociatyId());
        update.set("day",day);
        update.set("coinType",req.getCoinType().getCode());
        update.set("anchorId",req.getIncomeUid());
        mongoTemplate.upsert(idQuery, update, AnchorSocietyIncomeDay.class);
    }


}
