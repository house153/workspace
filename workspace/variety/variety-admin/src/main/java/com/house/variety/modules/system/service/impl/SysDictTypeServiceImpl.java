package com.house.variety.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.modules.system.entity.SysDictType;
import com.house.variety.modules.system.mapper.SysDictTypeMapper;
import com.house.variety.modules.system.service.SysDictTypeService;
import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Override
    public IPage<SysDictType> pageList(Page<SysDictType> page, SysDictType sysDictType) {

        LambdaQueryWrapper<SysDictType> wrapper = new QueryWrapper<SysDictType>().lambda();

        if (StringUtils.isNotBlank(sysDictType.getDictName())) {
            wrapper.like(SysDictType::getDictName, sysDictType.getDictName());
        }
        if (StringUtils.isNotBlank(sysDictType.getDictType())) {
            wrapper.like(SysDictType::getDictType, sysDictType.getDictType());
        }
        if (StringUtils.isNotBlank(sysDictType.getStatus())) {
            wrapper.eq(SysDictType::getStatus, sysDictType.getStatus());
        }
        if (StringUtils.isNotNull(sysDictType.getParams())) {
            String begin = String.valueOf(sysDictType.getParams().get("beginTime"));
            if (StringUtils.isNotBlank(begin)) {
                wrapper.ge(SysDictType::getCreateTime, begin);
            }
            String end = String.valueOf(sysDictType.getParams().get("endTime"));
            if (StringUtils.isNotBlank(end)) {
                wrapper.le(SysDictType::getCreateTime, end);
            }
        }
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean checkDictTypeUnique(SysDictType dictType) {
        if (StringUtils.isNotNull(dictType) && StringUtils.isNotBlank(dictType.getDictType())) {
            Long dictId = StringUtils.isNull(dictType.getDictId()) ? -1L : dictType.getDictId();
            SysDictType info = baseMapper.selectOne(new QueryWrapper<SysDictType>().lambda().eq(SysDictType::getDictType, dictType.getDictType()));
            if (StringUtils.isNotNull(info) && info.getDictId().longValue() != dictId.longValue()) {
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
}
