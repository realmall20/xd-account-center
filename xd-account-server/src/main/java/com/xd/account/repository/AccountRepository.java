package com.xd.account.repository;

import com.xd.account.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    /**
     * 悲观锁获取用户的资产信息
     * @param uid 用户ID
     * @return
     */
    @Query("from Account where uid=:uid")
    Account findByIdWithPessimisticLock(Integer uid);
    /**
     * @param plaCoinBalance    新的币种余额
     * @param uid               用户ID
     * @param oldPlaCoinBalance 老的币种余额
     * @return 更新的行数
     * @author xiaohei
     * @create 2019/12/9
     */
    @Query(
            value = "update com.xd.account.entity.User set plaCoinBalance=:plaCoinBalance where uid=:uid and plaCoinBalance=:oldPlaCoinBalance"
    )
    int updateBalance(BigDecimal plaCoinBalance, Integer uid, BigDecimal oldPlaCoinBalance);





}
