package com.house.variety.modules.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.system.entity.SysDictType;
import com.house.variety.modules.system.service.SysDictDataService;
import com.house.variety.modules.system.service.SysDictTypeService;
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
 * 字典类型表 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Controller
@RequestMapping("/sys/dict")
public class SysDictTypeController  extends BaseController {
    private String prefix = "sys/dict/type";

    @Resource
    private SysDictTypeService dictTypeService;


    @Resource
    private SysDictDataService dictDataService;

    @GetMapping()
    public String dictType() {
        return prefix + "/type";
    }

    @PostMapping("/list")
    @ResponseBody
    public HslResponse<IPage<SysDictType>> list(SysDictType dictType) {
        return HslResponse.ok(dictTypeService.pageList(getPage(), dictType));
    }


    /**
     * 新增字典类型
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存字典类型
     */
    @PostMapping("/add")
    @ResponseBody
    @DyField(type = DyType.ADD)
    public HslResponse addSave(SysDictType dict) {
        dict.setDictId(IdUtil.getId());
        boolean result = dictTypeService.save(dict);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.getById(dictId));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典类型
     */
    @PostMapping("/edit")
    @ResponseBody
    @DyField(type = DyType.EDIT)
    public HslResponse editSave(SysDictType dict) {
        return HslResponse.ok(dictTypeService.updateById(dict));
    }

    @PostMapping("/remove")
    @ResponseBody
    public HslResponse remove(String ids) {
        boolean result = dictTypeService.removeByIds(Arrays.asList(ids.split("\\,")));
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 查询字典详细
     */
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.getById(dictId));
        //根据字段类型ID查询字典数据
        mmap.put("dictList", dictTypeService.list(null));
        return "sys/dict/data/data";
    }

    /**
     * 校验字典类型
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public int checkDictTypeUnique(SysDictType dictType) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(dictType)) {
            uniqueFlag = dictTypeService.checkDictTypeUnique(dictType);
        }
        return uniqueFlag ? 0 : 1;
    }
}

