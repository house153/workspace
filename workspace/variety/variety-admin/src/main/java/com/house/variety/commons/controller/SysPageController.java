package com.house.variety.commons.controller;

import com.house.variety.commons.utils.TreeUtils;
import com.house.variety.modules.auth.entity.SysMenu;
import com.house.variety.modules.auth.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/8/23 17:32
 * @描述 系统页面视图
 */
@Slf4j
@Controller
public class SysPageController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("{module}/{url}.html")
    public String page(@PathVariable("module") String module, @PathVariable("url") String url){
        return   module + "/" + url+".html" ;
    }

    @RequestMapping(value = {"/","/index.html"})
    public String index(ModelMap mmap){
        // 根据用户权限来获取用户ID
        List<SysMenu> menus = sysMenuService.selectMenusByUserId(isAdmin() ? null : getUserId());
        menus = TreeUtils.getChildPerms(menus, 0);
        mmap.put("menus", menus);
        mmap.put("user", getUserInfo());
        return "index";
    }
}
