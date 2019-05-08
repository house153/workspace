package com.house.variety.modules.auth.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.auth.entity.SysDept;
import com.house.variety.modules.auth.entity.SysUser;
import com.house.variety.modules.auth.service.SysPostService;
import com.house.variety.modules.auth.service.SysRoleService;
import com.house.variety.modules.auth.service.SysUserService;
import com.house.variety.annotation.Log;
import com.house.variety.enums.BusinessType;
import com.house.variety.pojo.HslResponse;
import com.house.variety.seq.IdUtil;
import com.house.variety.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Controller
@RequestMapping("/auth/user")
public class SysUserController extends BaseController {

    private String prefix = "auth/user";

    @Resource
    private SysUserService userService;

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysPostService postService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user() {
        return prefix + "/user";
    }


    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public HslResponse<IPage<SysUser>> list(SysUser user) {
        if(StringUtils.isNull(user.getDeptId())){
            user.setDeptId(getDeptId());
        }
        //'删除标志（0代表存在 2代表删除）'
        user.setDelFlag("0");
        return  HslResponse.ok(userService.pageList(getPage(), user));
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        SysDept dept = new SysDept();
//        dept.setAncestors(getDeptPaths());
        dept.setDeptId(getDeptId());
        mmap.put("roles", roleService.selectRoleByDeptId(dept));
//        mmap.put("roles", roleService.list(null));
//        mmap.put("posts", postService.list(null));
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @PostMapping("/add")
    @ResponseBody
    @Log(title = "用户管理",businessType = BusinessType.INSERT)
    public HslResponse addSave(SysUser user) {
        user.setUserId(IdUtil.getId());
        user.setCreateBy(getUserId());
        user.setCreateTime(new Date());
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        user.setSalt(hex);
        user.setPassword(encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        boolean result = userService.insertUser(user);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }


    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user",  userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRoleByUserId(getDeptId(),userId));
//        mmap.put("roles", roleService.selectRoleByUserId(userId));
//        mmap.put("posts", postService.selectPostByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @PostMapping("/edit")
    @ResponseBody
    @Log(title = "用户管理",businessType = BusinessType.UPDATE)
    public HslResponse editSave(SysUser user) {
        user.setUpdateBy(getUserId());
        user.setUpdateTime(new Date());
        if(user.getUserId().longValue() == getUserId().longValue()){
            return HslResponse.failed("不允许修改本身");
        }
        if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId())) {
            return HslResponse.failed("不允许修改超级管理员用户");
        }
        boolean result = userService.updateUser(user);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }


    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.getById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @PostMapping("/resetPwd")
    @ResponseBody
    @Log(title = "用户管理",businessType = BusinessType.UPDATE)
    public HslResponse resetPwd(SysUser user) {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        user.setSalt(hex);
        user.setPassword(encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        boolean result = userService.updateById(user);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    @RequiresPermissions("system:user:remove")
    @PostMapping("/remove")
    @ResponseBody
    @Log(title = "用户管理",businessType = BusinessType.DELETE)
    public HslResponse remove(String ids) {
        List<String> idList = Arrays.asList(ids.split("\\,"));
        if(idList.contains(String.valueOf(getUserId()))){
            return HslResponse.failed("不允许删除本用户");
        }
        boolean result = userService.removeByIds(idList);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public int checkLoginNameUnique(SysUser user) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(user)) {
            uniqueFlag = userService.checkLoginNameUnique(user.getLoginName());
        }
        return uniqueFlag ? 0 : 1;
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public int checkPhoneUnique(SysUser user) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(user)) {
            uniqueFlag = userService.checkPhoneUnique(user);
        }
        return uniqueFlag ? 0 : 1;
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public int checkEmailUnique(SysUser user) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(user)) {
            uniqueFlag = userService.checkEmailUnique(user);
        }
        return uniqueFlag ? 0 : 1;
    }

}

