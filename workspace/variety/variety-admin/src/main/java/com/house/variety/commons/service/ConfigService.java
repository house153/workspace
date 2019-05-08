package com.house.variety.commons.service;

import com.house.variety.modules.system.entity.SysConfig;
import com.house.variety.modules.system.service.SysConfigService;
import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *描述  html调用 thymeleaf 实现参数管理
 *创建人  HuangChao <br/>
 *创建时间  2018/8/27 17:35
*/
@Service("config")
public class ConfigService
{
    @Resource
    private SysConfigService configService;

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数名称
     * @return 参数键值
     */
    public String getKey(String configKey)
    {
        SysConfig config =  configService.getConfigByKey(configKey);
        return StringUtils.isNotNull(config) ? config.getConfigValue() : "";
    }

}
