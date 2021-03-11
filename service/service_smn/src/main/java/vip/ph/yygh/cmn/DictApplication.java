package vip.ph.yygh.cmn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("vip.ph.yygh.cmn.mapper")
public class DictApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class);
    }
}
