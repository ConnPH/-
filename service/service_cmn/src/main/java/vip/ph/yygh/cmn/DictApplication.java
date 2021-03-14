package vip.ph.yygh.cmn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("vip.ph.yygh.cmn.mapper")
@EnableDiscoveryClient
public class DictApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class);
    }
}
