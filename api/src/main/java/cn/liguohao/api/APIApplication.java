package cn.liguohao.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @ClassName: APIApplication
 * @Description: 应用入口
 * @author: li-guohao
 * @date: 2020-7-18 0:59:40
 */
@SpringBootApplication
@ComponentScan(basePackages="cn.liguohao.api.*")//指定要扫描的包
@EnableJpaRepositories(basePackages="cn.liguohao.api.*")
@EntityScan(basePackages="cn.liguohao.api.*")
public class APIApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(APIApplication.class, args);
	}
}
