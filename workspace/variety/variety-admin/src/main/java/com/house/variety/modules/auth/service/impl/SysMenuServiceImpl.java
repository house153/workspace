package com.house.variety.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.commons.service.FrameLambdaQueryWrapper;
import com.house.variety.commons.service.FrameQueryWrapper;
import com.house.variety.modules.auth.entity.SysMenu;
import com.house.variety.modules.auth.mapper.SysMenuMapper;
import com.house.variety.modules.auth.service.SysMenuService;
import com.house.variety.modules.auth.service.SysUserRoleService;
import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysUserRoleService userRoleService;

    @Override
    public List<SysMenu> selectMenusByUserId(Long userId) {
        if(StringUtils.isNull(userId)){
          return baseMapper.selectList(new QueryWrapper<SysMenu>().lambda().eq(SysMenu::getVisible,0).in(SysMenu::getMenuType,"M","C").orderBy(true,true,SysMenu::getOrderNum));
        }
        return baseMapper.selectMenusByUserId(userId);
    }

    @Override
    public List<String> selectMenusByRoleId(Long roleId) {
        return baseMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<SysMenu> selectMenusByRoleIds(Long userId) {
        return baseMapper.selectMenuAllByUserId(userId);
    }

    @Override
    public List<SysMenu> list(SysMenu menu) {
        FrameLambdaQueryWrapper<SysMenu> wrapper = new FrameQueryWrapper<SysMenu>().lambda();
        if (StringUtils.isNotBlank(menu.getVisible())) {
            wrapper.eq(SysMenu::getVisible, menu.getVisible());
        }
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            wrapper.like(SysMenu::getMenuName, menu.getMenuName());
        }
        wrapper.orderBy(StringUtils.isNotBlank(menu.getOrderByColumn()), menu.isAsc(), menu.getOrderByColumn());
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SysMenu> selectMenus() {
        List<SysMenu> menus = this.list(new QueryWrapper<SysMenu>().lambda().in(SysMenu::getMenuType, "M", "C").eq(SysMenu::getVisible, "0").orderByAsc(SysMenu::getOrderNum));
        return menus;
    }

    @Override
    public List<SysMenu> selectAll() {
        List<SysMenu> menus = this.list(new QueryWrapper<SysMenu>().lambda().eq(SysMenu::getVisible, "0"));
        return menus;
    }


    @Override
    public SysMenu selectMenuById(Long menuId) {
        return baseMapper.selectOne(new QueryWrapper<SysMenu>().lambda().eq(SysMenu::getMenuId, menuId));
    }


    @Override
    public List<SysMenu> selectMenusByParentId(Long parentId) {
        List<SysMenu> menus = this.list(new QueryWrapper<SysMenu>().lambda().in(SysMenu::getParentId, parentId));
        return menus;
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public boolean checkMenuNameUnique(SysMenu menu) {
        if (StringUtils.isNotNull(menu) && StringUtils.isNotBlank(menu.getMenuName())) {
            Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
            SysMenu info = baseMapper.selectOne(new QueryWrapper<SysMenu>().lambda().eq(SysMenu::getMenuName, menu.getMenuName()));
            if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 校验权限标识是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public boolean checkPermsUnique(SysMenu menu) {
        if (StringUtils.isNotNull(menu) && StringUtils.isNotBlank(menu.getPerms())) {
            Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
            SysMenu info = baseMapper.selectOne(new QueryWrapper<SysMenu>().lambda().eq(SysMenu::getPerms, menu.getPerms()));
            if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = new ArrayList<>();
        if(StringUtils.isNull(userId)){
           List<SysMenu> menus = baseMapper.selectList(new QueryWrapper<SysMenu>().lambda().select(SysMenu::getPerms));
           if(!CollectionUtils.isEmpty(menus)){
               perms = menus.stream().map(SysMenu::getPerms).collect(Collectors.toList());
           }
        }else{
            perms = baseMapper.selectPermsByUserId(userId);
        }
        Set<String> permsSet = new HashSet<>();
        perms.forEach(perm -> {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        });
        return permsSet;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysMenu menu) {
        SysMenu info = baseMapper.selectOne(new QueryWrapper<SysMenu>().lambda().eq(SysMenu::getMenuId, menu.getMenuId()));
        if (StringUtils.isNotNull(info) && !info.getVisible().equalsIgnoreCase(menu.getVisible())) {
            //查询菜单是否有下属
            updateChild(menu);
        }
        return this.updateById(menu);
    }

    private void updateChild(SysMenu menu) {
        List<SysMenu> menus = this.list(new QueryWrapper<SysMenu>().lambda().eq(SysMenu::getParentId, menu.getMenuId()));
        if (!CollectionUtils.isEmpty(menus)) {
            menus.forEach(m -> {
                updateChild(m);
                m.setVisible("1");
            });
            this.updateBatchById(menus);
        }
    }
}
