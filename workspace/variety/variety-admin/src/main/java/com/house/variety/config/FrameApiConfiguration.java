/*
 * 文 件 名:  SwaggerConfiguration.java
 * 版    权:  Copyright © 2015-2017, 湖南物联聚创信息科技有限公司
 * 描    述:  SwaggerConfiguration.java
 * 版    本：   1.0
 * 创 建 人:  HuangChao
 * 创建时间: 2017年8月18日 下午4:56:24
 */
package com.house.variety.config;

import com.hsl.pile.swaggerbootstrapui.configuration.SwaggerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author HuangChao 2017年8月18日 下午4:56:24
 * @ClassName: SwaggerConfiguration
 * @Description: swagger配置项
 */
@Configuration
@EnableSwagger2
@Slf4j
public class FrameApiConfiguration extends SwaggerConfiguration {

    @Bean
    public Docket frameApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        return new Docket(DocumentationType.SWAGGER_2).groupName("基础平台").enable(enable).select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage + ".frame")).apis(RequestHandlerSelectors.any()).build()
                .apiInfo(apiInfo());
    }

}
