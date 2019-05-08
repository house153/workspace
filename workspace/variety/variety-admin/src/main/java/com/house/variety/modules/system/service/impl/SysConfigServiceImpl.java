package com.house.variety.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.modules.system.entity.SysConfig;
import com.house.variety.modules.system.mapper.SysConfigMapper;
import com.house.variety.modules.system.service.SysConfigService;
import com.house.variety.util.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统参数表 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    public IPage<SysConfig> pageList(Page<SysConfig> page, SysConfig sysConfig) {

        LambdaQueryWrapper<SysConfig> wrapper = new QueryWrapper<SysConfig>().lambda();

        if (StringUtils.isNotBlank(sysConfig.getConfigName())) {
            wrapper.like(SysConfig::getConfigName, sysConfig.getConfigName());
        }
        if (StringUtils.isNotBlank(sysConfig.getConfigKey())) {
            wrapper.like(SysConfig::getConfigKey, sysConfig.getConfigKey());
        }
        if (StringUtils.isNotBlank(sysConfig.getConfigType())) {
            wrapper.eq(SysConfig::getConfigType, sysConfig.getConfigType());
        }
        if (StringUtils.isNotNull(sysConfig.getParams())) {
            String begin = String.valueOf(sysConfig.getParams().get("beginTime"));
            if (StringUtils.isNotBlank(begin)) {
                wrapper.ge(SysConfig::getCreateTime, begin);
            }
            String end = String.valueOf(sysConfig.getParams().get("endTime"));
            if (StringUtils.isNotBlank(end)) {
                wrapper.le(SysConfig::getCreateTime, end);
            }
        }
        return baseMapper.selectPage(page, wrapper);
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public boolean checkConfigKeyUnique(SysConfig config) {
        if (StringUtils.isNotNull(config) && StringUtils.isNotBlank(config.getConfigKey())) {
            Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
            SysConfig info = baseMapper.selectOne(new QueryWrapper<SysConfig>().lambda().eq(SysConfig::getConfigKey, config.getConfigKey()));
            if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
                return false;
            }
        }else{
            return false;
        }
        return true;
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    @Cacheable(value = "sys", key = "'config_'+#configKey")
    public SysConfig getConfigByKey(String configKey){
        SysConfig config =  this.getOne(new QueryWrapper<SysConfig>().lambda().eq(SysConfig::getConfigKey,configKey));
        return config;
    }

    /**
     * 修改
     * @param sysConfig
     * @return
     */
    @CacheEvict(value = "sys", key = "'config_'+#sysConfig.configKey")
    @Override
    public boolean updateById(SysConfig sysConfig) {
        return super.updateById(sysConfig);
    }










}
