package com.xd.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * account 启动入口类
 *
 * @author xiaohei
 * @create 2019-12-12 上午9:26
 **/
@SpringBootApplication
@ComponentScan(value={"com.xd.*"})
@EntityScan("com.xd.account.entity")
@EnableMongoRepositories(basePackages={"com.xd.account.repository"})
@EnableDiscoveryClient
@EnableFeignClients
public class AccountApplication {

    public static void main(String[] args){
        SpringApplication.run(AccountApplication.class,args);
    }
}
