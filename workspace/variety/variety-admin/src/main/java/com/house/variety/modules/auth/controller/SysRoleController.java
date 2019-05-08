package com.house.variety.modules.auth.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.auth.entity.SysRole;
import com.house.variety.modules.auth.service.SysRoleService;
import com.house.variety.modules.auth.service.SysUserRoleService;
import com.house.variety.annotation.DyField;
import com.house.variety.annotation.Log;
import com.house.variety.enums.BusinessType;
import com.house.variety.enums.DyType;
import com.house.variety.pojo.HslResponse;
import com.house.variety.seq.IdUtil;
import com.house.variety.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Controller
@RequestMapping("/auth/role")
public class SysRoleController extends BaseController {

    private String prefix = "auth/role";

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysUserRoleService userRoleService;

    @RequiresPermissions("system:role:view")
    @GetMapping()
    public String menu() {
        return prefix + "/role";
    }


    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public HslResponse<IPage<SysRole>> list(SysRole role) {
//        role.setDeptId(getDeptId());
        role.setDeptPaths(getDeptPaths());
        return HslResponse.ok(roleService.pageList(getPage(), role));
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }


    @RequiresPermissions("system:role:add")
    @PostMapping("save")
    @ResponseBody
    @DyField(type = DyType.ADD)
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    public HslResponse save(SysRole role) {
        role.setRoleId(IdUtil.getId());
        boolean result = roleService.saveRole(role);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/edit";
    }

    @RequiresPermissions("system:role:edit")
    @PostMapping("/edit")
    @ResponseBody
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    public HslResponse editSave(SysRole role) {
        boolean result = roleService.updateRole(role);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    @RequiresPermissions("system:role:remove")
    @PostMapping("/remove")
    @ResponseBody
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    public HslResponse remove(String ids) {
        List<String> removeIds = Arrays.asList(ids.split("\\,"));
        Integer count = userRoleService.selectCountByRole(removeIds);
        if (count > 0) {
            return HslResponse.failed("所选角色存在使用用户,不允许删除");
        }
        boolean result = roleService.removeByIds(removeIds);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public int checkRoleNameUnique(SysRole role) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(role)) {
            role.setDeptPaths(getDeptPaths());
            uniqueFlag = roleService.checkRoleNameUnique(role);
        }
        return uniqueFlag ? 0 : 1;
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public int checkRoleKeyUnique(SysRole role) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(role)) {
            role.setDeptPaths(getDeptPaths());
            uniqueFlag = roleService.checkRoleKeyUnique(role);
        }
        return uniqueFlag ? 0 : 1;
    }
}

