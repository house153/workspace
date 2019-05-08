package com.house.variety.modules.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.system.entity.SysDictData;
import com.house.variety.modules.system.service.SysDictDataService;
import com.house.variety.annotation.DyField;
import com.house.variety.enums.DyType;
import com.house.variety.pojo.HslResponse;
import com.house.variety.seq.IdUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Controller
@RequestMapping("/sys/dict/data")
public class SysDictDataController extends BaseController {
    private String prefix = "sys/dict/data";


    @Resource
    private SysDictDataService dictDataService;

    @GetMapping()
    public String dictType() {
        return prefix + "/data";
    }

    @PostMapping("/list")
    @ResponseBody
    public HslResponse<IPage<SysDictData>> list(SysDictData dictData) {
        return HslResponse.ok(dictDataService.pageList(getPage(), dictData));
    }

    /**
     * 新增字典数据
     */
    @GetMapping("/add/{dictType}")
    public String add(@PathVariable("dictType") String dictType, ModelMap mmap)
    {
        mmap.put("dictType", dictType);
        return prefix + "/add";
    }

    /**
     * 新增保存字典数据
     */
    @PostMapping("/add")
    @ResponseBody
    @DyField(type = DyType.ADD)
    public HslResponse addSave(SysDictData dict) {
        dict.setDictCode(IdUtil.getId());
        boolean result = dictDataService.save(dict);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 修改字典数据
     */
    @GetMapping("/edit/{dictCode}")
    public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
        mmap.put("dict", dictDataService.getById(dictCode));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典数据
     */
    @PostMapping("/edit")
    @ResponseBody
    @DyField(type = DyType.EDIT)
    public HslResponse editSave(SysDictData dict) {
        return HslResponse.ok(dictDataService.updateById(dict));
    }

    @PostMapping("/remove")
    @ResponseBody
    public HslResponse remove(String ids) {
        boolean result = dictDataService.removeByIds(Arrays.asList(ids.split("\\,")));
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

}

