package com.house.variety.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.modules.system.entity.SysDictData;
import com.house.variety.modules.system.mapper.SysDictDataMapper;
import com.house.variety.modules.system.service.SysDictDataService;
import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    /**
     * 描述  根据字典类型查询字典数据<br/>
     * 参数  dictType 字典类型<br/>
     * 返回值  字典数据集合信息 <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/8/28 9:42
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        return baseMapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, dictType));
    }

    /**
     *描述  根据字典类型查询字典数据<br/>
     *参数  [dictType:字典类型, status:状态] <br/>
     *返回值  List<SysDictData> <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/26 14:46
    */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType,String status) {
        return baseMapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, dictType).eq(SysDictData::getStatus,status));
    }


    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String getLabel(String dictType, String dictValue) {
        Wrapper<SysDictData> wrapper = new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, dictType).eq(SysDictData::getDictValue, dictValue);
        SysDictData data = baseMapper.selectOne(wrapper);
        if (null == data) {
            return null;
        }
        return data.getDictLabel();
    }

    @Override
    public IPage<SysDictData> pageList(Page<SysDictData> page, SysDictData sysDictData) {

        LambdaQueryWrapper<SysDictData> wrapper = new QueryWrapper<SysDictData>().lambda();

        if (StringUtils.isNotBlank(sysDictData.getDictType())) {
            wrapper.eq(SysDictData::getDictType, sysDictData.getDictType());
        }
        if (StringUtils.isNotBlank(sysDictData.getDictLabel())) {
            wrapper.like(SysDictData::getDictLabel, sysDictData.getDictLabel());
        }
        if (StringUtils.isNotBlank(sysDictData.getStatus())) {
            wrapper.eq(SysDictData::getStatus, sysDictData.getStatus());
        }
        return baseMapper.selectPage(page, wrapper);
    }
}
