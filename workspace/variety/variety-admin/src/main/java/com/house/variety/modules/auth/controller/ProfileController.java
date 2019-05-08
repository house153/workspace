package com.house.variety.modules.auth.controller;

import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.auth.entity.SysUser;
import com.house.variety.modules.auth.service.SysDeptService;
import com.house.variety.modules.auth.service.SysRoleService;
import com.house.variety.modules.auth.service.SysUserService;
import com.house.variety.modules.system.service.SysDictDataService;

import com.house.variety.enums.BusinessType;
import com.house.variety.pojo.HslResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/10/18 9:16
 * @描述 个人信息控制器
 */
@Slf4j
@Controller
@RequestMapping("/auth/user/profile")
public class ProfileController extends BaseController {

    private String prefix = "auth/user/profile";

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysDictDataService sysDictDataService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysDeptService sysDeptService;


    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap) {
        SysUser user = getUserInfo();
        user.setSex(sysDictDataService.getLabel("sys_user_sex", user.getSex()));
        mmap.put("user", user);
        mmap.put("roleGroup", sysRoleService.selectRoleKeys(user.getUserId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        SysUser user = getUserInfo();
        String encrypt = new Md5Hash(user.getLoginName() + password + user.getSalt()).toHex().toString();
        if (user.getPassword().equals(encrypt)) {
            return true;
        }
        return false;
    }

    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", sysUserService.getById(userId));
        return prefix + "/resetPwd";
    }

    @PostMapping("/resetPwd")
    @ResponseBody
    public HslResponse resetPwd(SysUser user) {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        user.setSalt(hex);
        user.setPassword(encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        boolean result = sysUserService.updateById(user);
        if (result) {
            setUserInfo(user);
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        SysUser user = sysUserService.getById(userId);
        user.setDept(sysDeptService.getById(user.getDeptId()));
        mmap.put("user", user);
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar/{userId}")
    public String avatar(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", sysUserService.getById(userId));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */

    @PostMapping("/update")
    @ResponseBody
    public HslResponse update(SysUser user) {
        if (sysUserService.updateById(user)) {
            setUserInfo(user);
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 保存头像
     */

    @PostMapping("/updateAvatar")
    @ResponseBody
    public HslResponse updateAvatar(SysUser user, @RequestParam("avatarfile") MultipartFile file) {
        if (!file.isEmpty()) {
            String avatar = uploadFile(file);
            user.setAvatar(avatar);
            if (sysUserService.updateById(user)) {
                setUserInfo(user);
                return HslResponse.ok("success");
            }
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    //更新用户会话信息
    private void setUserInfo(SysUser user){
        user = sysUserService.getById(user.getUserId());
        user.setDept(sysDeptService.getById(user.getDeptId()));
        setUser(user);
    }
}
