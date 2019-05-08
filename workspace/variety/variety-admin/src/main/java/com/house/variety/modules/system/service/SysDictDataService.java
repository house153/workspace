package com.house.variety.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.system.entity.SysDictData;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 描述  根据字典类型查询字典数据<br/>
     * 参数  dictType 字典类型<br/>
     * 返回值  字典数据集合信息 <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/8/28 9:42
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 描述  根据字典类型查询字典数据<br/>
     * 参数  [dictType:字典类型, status:状态] <br/>
     * 返回值  List<SysDictData> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/26 14:46
     */
    List<SysDictData> selectDictDataByType(String dictType, String status);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String getLabel(String dictType, String dictValue);


    /**
     * 描述  分页查询<br/>
     * 参数  [page、dictData] <br/>
     * 返回值  SysDictData> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 9:23
     */
    IPage<SysDictData> pageList(Page<SysDictData> page, SysDictData dictData);
}
