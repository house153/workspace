package com.house.variety.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.commons.service.FrameLambdaQueryWrapper;
import com.house.variety.commons.service.FrameQueryWrapper;
import com.house.variety.modules.system.entity.SysLogininfor;
import com.house.variety.modules.system.mapper.SysLogininforMapper;
import com.house.variety.modules.system.service.SysLogininforService;

import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements SysLogininforService {

    @Override
    public IPage<SysLogininfor> pageList(Page<SysLogininfor> page, SysLogininfor logininfor) {
        FrameLambdaQueryWrapper<SysLogininfor> wrapper = new FrameQueryWrapper<SysLogininfor>().lambda();
        wrapper.like(StringUtils.isNotBlank(logininfor.getLoginName()), SysLogininfor::getLoginName, logininfor.getLoginName())
                .like(StringUtils.isNotBlank(logininfor.getIpaddr()), SysLogininfor::getIpaddr, logininfor.getIpaddr())
                .like(StringUtils.isNotBlank(logininfor.getBrowser()), SysLogininfor::getBrowser, logininfor.getBrowser())
                .eq(StringUtils.isNotBlank(logininfor.getStatus()), SysLogininfor::getStatus, logininfor.getStatus());
        if (StringUtils.isNotNull(logininfor.getParams())) {
            String begin = String.valueOf(logininfor.getParams().get("beginTime"));
            if(StringUtils.isNotBlank(begin)) {
                begin+=" 00:00:00";
                wrapper.ge(SysLogininfor::getLoginTime, begin);
            }
            String end = String.valueOf(logininfor.getParams().get("endTime"));
            if(StringUtils.isNotBlank(end)) {
                end+=" 23:59:59";
                wrapper.le(SysLogininfor::getLoginTime, end);
            }
        }
        return baseMapper.selectPage(page, wrapper);
    }
}
