package com.house.variety.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.system.entity.SysConfig;

/**
 * <p>
 * 系统参数表 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 描述  分页查询<br/>
     * 参数  [page, sysConfig] <br/>
     * 返回值  IPage<SysConfig> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 15:35
     */
    IPage<SysConfig> pageList(Page<SysConfig> page, SysConfig sysConfig);

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    boolean checkConfigKeyUnique(SysConfig config);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
     SysConfig getConfigByKey(String configKey);


}
