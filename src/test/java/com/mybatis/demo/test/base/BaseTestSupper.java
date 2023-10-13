package com.mybatis.demo.test.base;


import com.ck.mybatis.demo.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * springboot 2.4.2
 * 使用Junit5     使用junit-jupiter-api
 *
 * @author: Mr.DengYuanFang
 * @program: MySpringBoot
 * @Description:
 * @Version: 1.0
 * @createTime: 2019-02-12 11:00
 **/
@SpringBootTest(classes = Application.class)
//环境变量
@ActiveProfiles("local")
public class BaseTestSupper {

}


