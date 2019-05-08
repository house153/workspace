package com.house.variety.modules.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.system.entity.SysLogininfor;
import com.house.variety.modules.system.entity.SysOperLog;
import com.house.variety.modules.system.service.SysLogininforService;
import com.house.variety.annotation.Log;
import com.house.variety.enums.BusinessType;
import com.house.variety.pojo.HslResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 系统访问记录 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Controller
@RequestMapping("/sys/logininfor")
public class SysLogininforController extends BaseController {

    private String prefix = "sys/logininfor";

    @Resource
    private SysLogininforService logininforService;

    @RequiresPermissions("system:logininfor:view")
    @GetMapping()
    public String logininfor() {
        return prefix + "/logininfor";
    }

    @RequiresPermissions("system:logininfor:list")
    @PostMapping("/list")
    @ResponseBody
    public HslResponse<IPage<SysOperLog>> list(SysLogininfor logininfor) {
        return HslResponse.ok(logininforService.pageList(getPage(), logininfor));
    }


    @RequiresPermissions("system:logininfor:remove")
    @Log(title = "登陆日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public HslResponse remove(String ids) {
        boolean result = logininforService.removeByIds(Arrays.asList(ids.split("\\,")));
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }
}

