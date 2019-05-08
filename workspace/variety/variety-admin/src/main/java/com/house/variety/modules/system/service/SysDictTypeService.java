package com.house.variety.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.system.entity.SysDictType;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysDictTypeService extends IService<SysDictType> {


    /**
     *描述  分页查询<br/>
     *参数  [page\sysDictType] <br/>
     *返回值  SysDictType <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/6 9:23
     */
    IPage<SysDictType> pageList(Page<SysDictType> page, SysDictType sysDictType);


    /**
     *描述  校验字典类型称是否唯一<br/>
     *参数  [dictType] <br/>
     *返回值  boolean <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/6 13:58
    */
    boolean checkDictTypeUnique(SysDictType dictType);
}
