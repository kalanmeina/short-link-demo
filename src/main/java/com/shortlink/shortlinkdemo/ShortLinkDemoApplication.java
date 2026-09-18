package com.shortlink.shortlinkdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
@MapperScan("com.shortlink.shortlinkdemo.mapper")
@SpringBootApplication
public class ShortLinkDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkDemoApplication.class, args);
    }

}
