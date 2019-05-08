package com.house.variety.modules.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.system.entity.SysConfig;
import com.house.variety.modules.system.service.SysConfigService;
import com.house.variety.annotation.DyField;
import com.house.variety.enums.DyType;
import com.house.variety.pojo.HslResponse;
import com.house.variety.seq.IdUtil;
import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 系统参数表 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Controller
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {
    private String prefix = "sys/config";

    @Resource
    private SysConfigService configService;

    @GetMapping()
    public String config() {
        return prefix + "/config";
    }

    /**
     * 查询参数配置列表
     */
    @PostMapping("/list")
    @ResponseBody
    public HslResponse<IPage<SysConfig>> list(SysConfig config) {
        return HslResponse.ok(configService.pageList(getPage(), config));
    }


    /**
     * 新增参数配置
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存参数配置
     */
    @PostMapping("/add")
    @ResponseBody
    @DyField(type = DyType.ADD)
    public HslResponse addSave(SysConfig config) {
        config.setConfigId(IdUtil.getId());
        boolean result = configService.save(config);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 修改参数配置
     */
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") Long configId, ModelMap mmap) {
        mmap.put("config", configService.getById(configId));
        return prefix + "/edit";
    }

    /**
     * 修改保存参数配置
     */
    @PostMapping("/edit")
    @ResponseBody
    @DyField(type = DyType.EDIT)
    public HslResponse editSave(SysConfig config) {
        boolean result = configService.updateById(config);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 删除参数配置
     */
    @PostMapping("/remove")
    @ResponseBody
    public HslResponse remove(String ids) {
        boolean result = configService.removeByIds(Arrays.asList(ids.split("\\,")));
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 校验参数键名
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public int checkConfigKeyUnique(SysConfig config) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(config)) {
            uniqueFlag = configService.checkConfigKeyUnique(config);
        }
        return uniqueFlag ? 0 : 1;
    }


}

