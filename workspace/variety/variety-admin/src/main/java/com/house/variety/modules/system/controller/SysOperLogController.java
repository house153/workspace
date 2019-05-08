package com.house.variety.modules.system.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.system.entity.SysOperLog;
import com.house.variety.modules.system.service.SysOperLogService;
import com.house.variety.pojo.HslResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Controller
@RequestMapping("/sys/operlog")
public class SysOperLogController extends BaseController {
    private String prefix = "sys/operlog";

    @Resource
    private SysOperLogService sysOperLogService;

    @RequiresPermissions("system:operlog:view")
    @GetMapping()
    public String operlog() {
        return prefix + "/operlog";
    }


    @RequiresPermissions("system:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public HslResponse<IPage<SysOperLog>> list(SysOperLog operLog) {
        return HslResponse.ok(sysOperLogService.pageList(getPage(), operLog));
    }

    @RequiresPermissions("system:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public HslResponse remove(String ids) {
        boolean result = sysOperLogService.removeByIds(Arrays.asList(ids.split("\\,")));
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    @RequiresPermissions("system:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") Long deptId, ModelMap mmap) {
        mmap.put("operLog", sysOperLogService.getById(deptId));
        return prefix + "/detail";
    }
}

