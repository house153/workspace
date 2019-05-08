package com.house.variety.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.commons.service.FrameLambdaQueryWrapper;
import com.house.variety.commons.service.FrameQueryWrapper;
import com.house.variety.modules.system.entity.SysOperLog;
import com.house.variety.modules.system.mapper.SysOperLogMapper;
import com.house.variety.modules.system.service.SysOperLogService;
import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    @Override
    public IPage<SysOperLog> pageList(Page<SysOperLog> page, SysOperLog sysOperLog) {
        FrameLambdaQueryWrapper<SysOperLog> wrapper = new FrameQueryWrapper<SysOperLog>().lambda();
        wrapper.like(StringUtils.isNotBlank(sysOperLog.getTitle()), SysOperLog::getTitle, sysOperLog.getTitle())
                .like(StringUtils.isNotBlank(sysOperLog.getMethod()), SysOperLog::getMethod, sysOperLog.getMethod())
                .like(StringUtils.isNotBlank(sysOperLog.getOperName()), SysOperLog::getOperName, sysOperLog.getOperName())
                .like(StringUtils.isNotBlank(sysOperLog.getDeptName()), SysOperLog::getDeptName, sysOperLog.getDeptName())
                .eq(StringUtils.isNotNull(sysOperLog.getBusinessType()), SysOperLog::getBusinessType, sysOperLog.getBusinessType())
                .eq(StringUtils.isNotNull(sysOperLog.getOperatorType()), SysOperLog::getOperatorType, sysOperLog.getOperatorType())
                .eq(StringUtils.isNotNull(sysOperLog.getStatus()), SysOperLog::getStatus, sysOperLog.getStatus());

        if (StringUtils.isNotNull(sysOperLog.getParams())) {
            String begin = String.valueOf(sysOperLog.getParams().get("beginTime"));
            if(StringUtils.isNotBlank(begin)) {
                begin+=" 00:00:00";
                wrapper.ge(SysOperLog::getOperTime, begin);
            }
            String end = String.valueOf(sysOperLog.getParams().get("endTime"));
            if(StringUtils.isNotBlank(end)) {
                end+=" 23:59:59";
                wrapper.le(SysOperLog::getOperTime, end);
            }
        }
        wrapper.orderBy(StringUtils.isNotBlank(sysOperLog.getOrderByColumn()),sysOperLog.isAsc(),sysOperLog.getOrderByColumn());
        return baseMapper.selectPage(page, wrapper);
    }
}
