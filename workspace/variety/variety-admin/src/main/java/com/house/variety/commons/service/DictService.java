package com.house.variety.commons.service;

import com.house.variety.modules.system.entity.SysDictData;
import com.house.variety.modules.system.service.SysDictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述  html调用 thymeleaf 实现字典读取
 * 创建人  HuangChao <br/>
 * 创建时间  2018/8/27 17:35
 */
@Service("dict")
public class DictService {
    @Resource
    private SysDictDataService dictDataService;

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<SysDictData> getType(String dictType) {
        return dictDataService.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @param status 字典状态
     * @return 参数键值
     */
    public List<SysDictData> getType(String dictType,String status) {
        return dictDataService.selectDictDataByType(dictType,status);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue) {
        return dictDataService.getLabel(dictType, dictValue);
    }
}
