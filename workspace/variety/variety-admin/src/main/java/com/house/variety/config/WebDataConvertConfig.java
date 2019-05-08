package com.house.variety.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/9/5 17:56
 * @描述 WVC数据转换处理
 */
@Configuration
public class WebDataConvertConfig extends WebMvcConfigurationSupport {
//    /**
//     * 首页地址
//     */
//    @Value("${shiro.user.indexUrl}")
//    private String indexUrl;


    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }

//
//    @Override
//    protected void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("forward:" + indexUrl);
//        super.addViewControllers(registry);
//    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**
     *描述  时间格式转换器,将Date类型统一转换为yyyy-MM-dd HH:mm:ss格式的字符串<br/>
     *参数  [] <br/>
     *返回值  MappingJackson2HttpMessageConverter <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/5 18:05
    */
    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();

        //日期格式转换
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        //Long类型转String类型
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);

        converter.setObjectMapper(mapper);
        return converter;
    }

}
