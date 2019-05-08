package com.house.variety.modules.auth.controller;


import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.auth.entity.SysDept;
import com.house.variety.modules.auth.enums.DeptType;
import com.house.variety.modules.auth.service.SysDeptService;
import com.house.variety.pojo.HslResponse;
import com.house.variety.seq.IdUtil;
import com.house.variety.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统部门表 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
@Controller
@RequestMapping("/auth/dept")
public class SysDeptController extends BaseController {
    private String prefix = "auth/dept";

    @Resource
    private SysDeptService deptService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept(ModelMap mmap) {
        mmap.put("deptId", getDeptId());
        mmap.put("parentId", getUserDeptInfo().getParentId());
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @GetMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept) {
        dept.setAncestors(getDeptPaths());
//        dept.setParentId(getDeptId());
//        dept.setType(DeptType.DEPT.getCode());
        return deptService.listByDept(dept);
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        mmap.put("dept", deptService.getById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public HslResponse addSave(SysDept dept) {
        dept.setDeptId(IdUtil.getId());
        dept.setType(DeptType.DEPT.getCode());
        boolean result = deptService.saveDept(dept);
        if (result) {
            return HslResponse.ok("success");
        } else {
            return HslResponse.failed("操作异常，请联系管理!");
        }
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public HslResponse editSave(SysDept dept) {
        boolean result = deptService.updateDept(dept);
        if (result) {
            return HslResponse.ok("success");
        } else {
            return HslResponse.failed("操作异常，请联系管理!");
        }
    }

    /**
     * 删除
     */
    @RequiresPermissions("system:dept:remove")
    @PostMapping("/remove/{deptId}")
    @ResponseBody
    public HslResponse remove(@PathVariable("deptId") Long deptId) {
        if (deptService.selectDeptCount(deptId)) {
            return HslResponse.failed("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)){
            return HslResponse.failed("部门存在用户,不允许删除");
        }
        boolean result = deptService.removeById(deptId);
        if (result) {
            return HslResponse.ok("success");
        } else {
            return HslResponse.failed("操作异常，请联系管理!");
        }
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public int checkDeptNameUnique(SysDept dept) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(dept)) {
            dept.setAncestors(getDeptPaths());
            uniqueFlag = deptService.checkDeptNameUnique(dept);
        }
        return uniqueFlag ? 0 : 1;
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("dept", deptService.getById(deptId));
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData() {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        SysDept sysDept = new SysDept();
        sysDept.setStatus("0");
        sysDept.setAncestors(getDeptPaths());
//        sysDept.setParentId(getDeptId());
        List<SysDept> deptList = deptService.listByDept(sysDept);

        for (SysDept dept : deptList) {
            Map<String, Object> deptMap = new HashMap<String, Object>();
            deptMap.put("id", dept.getDeptId());
            deptMap.put("pId", dept.getParentId());
            deptMap.put("name", dept.getDeptName());
            deptMap.put("title", dept.getDeptName());
            deptMap.put("ancestors", dept.getAncestors());
            trees.add(deptMap);
        }
        return trees;
    }
}

