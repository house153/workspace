package com.house.variety.modules.auth.controller;


import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.auth.entity.SysMenu;
import com.house.variety.modules.auth.service.SysMenuService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Controller
@RequestMapping("/auth/menu")
public class SysMenuController extends BaseController {

    private String prefix = "auth/menu";

    @Resource
    private SysMenuService menuService;

    @RequiresPermissions("system:menu:view")
    @GetMapping()
    public String menu() {
        return prefix + "/menu";
    }

    @RequiresPermissions("system:menu:list")
    @GetMapping("/list")
    @ResponseBody
    public List<SysMenu> list(SysMenu menu) {
        return menuService.list(menu);
    }

    /**
     * 修改菜单
     */
    @GetMapping("/edit/{menuId}")
    public String toEdit(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("menu", menuService.selectMenuById(menuId));
        return prefix + "/edit";
    }

    /**
     * 删除菜单
     */
    @RequiresPermissions("system:menu:remove")
    @PostMapping("/remove/{menuId}")
    @ResponseBody
    @Log(title = "菜单管理",businessType = BusinessType.DELETE)
    public HslResponse remove(@PathVariable("menuId") Long menuId) {
        if (menuService.selectMenusByParentId(menuId).size() > 0) {
            return HslResponse.failed("存在子菜单,不允许删除");
        }
//        if (menuService.selectCountRoleMenuByMenuId(menuId) > 0) {
//            return error(1, "菜单已分配,不允许删除");
//            HslResponse.failed(1, "菜单已分配,不允许删除");
//        }
        boolean result = menuService.removeById(menuId);
        if (result) {
            return HslResponse.ok("success");
        } else {
            return HslResponse.failed("操作异常，请联系管理!");
        }
    }

    /**
     * 新增
     */
    @GetMapping("/add/{parentId}")
    public String toAdd(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        SysMenu menu = null;
        if (0L != parentId) {
            menu = menuService.selectMenuById(parentId);
        } else {
            menu = new SysMenu();
            menu.setMenuId(0L);
            menu.setMenuName("主目录");
        }
        mmap.put("menu", menu);
        return prefix + "/add";
    }

    /**
     * 修改保存菜单
     */
    @RequiresPermissions("system:menu:edit")
    @PostMapping("/edit")
    @ResponseBody
    @DyField(type = DyType.EDIT)
    @Log(title = "菜单管理",businessType = BusinessType.UPDATE)
    public HslResponse editSave(SysMenu menu) {
        boolean result = menuService.update(menu);
        if (result) {
            return HslResponse.ok("success");
        } else {
            return HslResponse.failed("操作异常，请联系管理!");
        }
    }

    /**
     * 新增保存菜单
     */
    @RequiresPermissions("system:menu:add")
    @PostMapping("/add")
    @ResponseBody
    @DyField(type = DyType.ADD)
    @Log(title = "角色管理",businessType = BusinessType.INSERT)
    public HslResponse addSave(SysMenu menu) {
        menu.setMenuId(IdUtil.getId());
        boolean result = menuService.save(menu);
        return HslResponse.ok(result);
    }

    /**
     * 选择菜单图标
     */
    @GetMapping("/icon")
    public String icon() {
        return prefix + "/icon";
    }


    /**
     * 加载角色菜单列表树
     */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Map<String, Object>> roleMenuTreeData(Long roleId) {
        List<String> roleMenuList = null;
        List<SysMenu> menus = new ArrayList<>();
        // 根据当前登录用户角色查询权限范围
        if (isAdmin()) {
            menus = menuService.selectAll();
        } else {
            menus = menuService.selectMenusByRoleIds(getUserId());
        }
        List<Map<String, Object>> tree = null;
        if (StringUtils.isNotNull(roleId)) {
            roleMenuList = menuService.selectMenusByRoleId(roleId);
            tree = getTrees(menus, true, roleMenuList, false);
        } else {
            tree = getTrees(menus, false, null, false);
        }
        return tree;
    }

    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Map<String, Object>> menuTreeData() {
        List<SysMenu> menus = menuService.selectMenus();
        List<Map<String, Object>> tree = getTrees(menus, false, null, false);
        return tree;
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree/{menuId}")
    public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("menu", menuService.selectMenuById(menuId));
        return prefix + "/tree";
    }

    /**
     * 校验菜单名称
     */
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public int checkMenuNameUnique(SysMenu menu) {
        boolean uniqueFlag = true;
        if (StringUtils.isNotNull(menu)) {
            uniqueFlag = menuService.checkMenuNameUnique(menu);
        }
        return uniqueFlag ? 0 : 1;
    }

    /**
     * 校验菜单名称
     */
    @PostMapping("/checkPermsUnique")
    @ResponseBody
    public int checkPermsUnique(SysMenu menu) {
        boolean uniqueFlag = true;
        if (StringUtils.isNotNull(menu)) {
            uniqueFlag = menuService.checkPermsUnique(menu);
        }
        return uniqueFlag ? 0 : 1;
    }


    /**
     * 对象转菜单树
     *
     * @param menuList     菜单列表
     * @param isCheck      是否需要选中
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag    是否需要显示权限标识
     * @return
     */
    private List<Map<String, Object>> getTrees(List<SysMenu> menuList, boolean isCheck, List<String> roleMenuList,
                                               boolean permsFlag) {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (SysMenu menu : menuList) {
            Map<String, Object> deptMap = new HashMap<String, Object>();
            deptMap.put("id", menu.getMenuId());
            deptMap.put("pId", menu.getParentId());
            deptMap.put("name", transMenuName(menu, roleMenuList, permsFlag));
            deptMap.put("title", menu.getMenuName());
            if (isCheck) {
                deptMap.put("checked", roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            } else {
                deptMap.put("checked", false);
            }
            trees.add(deptMap);
            //TODO 添加操作权限
//            deptMap = new HashMap<String, Object>();
//            deptMap.put("id", menu.getMenuId()+"01");
//            deptMap.put("pId", menu.getMenuId());
//            deptMap.put("name", "操作权限");
//            deptMap.put("title", "操作权限");
//            trees.add(deptMap);
        }
        return trees;
    }

    private String transMenuName(SysMenu menu, List<String> roleMenuList, boolean permsFlag) {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }
}

