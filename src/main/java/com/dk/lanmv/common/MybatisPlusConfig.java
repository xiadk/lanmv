package com.dk.lanmv.common;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus分页配置
 * @author Wayne.M
 * 2019年2月16日
 */
@Slf4j
@Configuration
@MapperScan("com.dk.lanmv.mapper.*")
public class MybatisPlusConfig {
	@Bean
    public PaginationInterceptor paginationInterceptor() {
		//log.info("【MybatisPlus分页配置加载】");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
