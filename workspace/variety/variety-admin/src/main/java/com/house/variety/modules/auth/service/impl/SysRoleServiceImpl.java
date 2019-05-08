package com.house.variety.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.house.variety.modules.auth.entity.SysDept;
import com.house.variety.modules.auth.entity.SysRole;
import com.house.variety.modules.auth.entity.SysRoleMenu;
import com.house.variety.modules.auth.mapper.SysRoleMapper;
import com.house.variety.modules.auth.service.SysRoleMenuService;
import com.house.variety.modules.auth.service.SysRoleService;
import com.house.variety.modules.system.entity.SysConfig;
import com.house.variety.modules.system.service.SysConfigService;
import com.house.variety.constants.BaseConstants;
import com.house.variety.exception.ServiceException;
import com.house.variety.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysConfigService configService;


    @Override
    public IPage<SysRole> pageList(Page<SysRole> page, SysRole role) {
//        FrameLambdaQueryWrapper<SysRole> wrapper = new FrameQueryWrapper<SysRole>().lambda();
//        if (StringUtils.isNotBlank(role.getRoleName())) {
//            wrapper.like(SysRole::getRoleName, role.getRoleName());
//        }
//        if (StringUtils.isNotBlank(role.getRoleKey())) {
//            wrapper.like(SysRole::getRoleKey, role.getRoleKey());
//        }
//        if (StringUtils.isNotBlank(role.getStatus())) {
//            wrapper.eq(SysRole::getStatus, role.getStatus());
//        }
//        if(StringUtils.isNotNull(role.getDeptId())){
//            wrapper.eq(SysRole::getDeptId,role.getDeptId());
//        }
//        if (StringUtils.isNotBlank(role.getDeptPaths())) {
//            wrapper.likeRight(SysRole::getDeptPaths, role.getDeptPaths());
//        }
//        if (StringUtils.isNotNull(role.getParams())) {
//            String begin = String.valueOf(role.getParams().get("beginTime"));
//            if (StringUtils.isNotBlank(begin)) {
//                wrapper.ge(SysRole::getCreateTime, begin);
//            }
//            String end = String.valueOf(role.getParams().get("endTime"));
//            if (StringUtils.isNotBlank(end)) {
//                wrapper.le(SysRole::getCreateTime, end);
//            }
//        }
//        wrapper.ne(SysRole::getRoleId,1);//不查询系统角色
//        wrapper.orderBy(StringUtils.isNotBlank(role.getOrderByColumn()),role.isAsc(),role.getOrderByColumn());
        if (StringUtils.isNotBlank(role.getOrderByColumn())) {
            role.setOrderByColumn(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, role.getOrderByColumn()));
        }
//        return this.page(page, wrapper);
        return baseMapper.pageList(page,role);
    }


    @Override
    public List<SysRole> selectRoleByDeptId(SysDept dept) {
        return baseMapper.selectList(new QueryWrapper<SysRole>().lambda()
                .eq(StringUtils.isNotNull(dept.getDeptId()),SysRole::getDeptId,dept.getDeptId())
                .eq(StringUtils.isNotBlank(dept.getStatus()),SysRole::getStatus,dept.getStatus())
        .likeRight(StringUtils.isNotBlank(dept.getAncestors()),SysRole::getDeptPaths,dept.getAncestors()));
    }

    @Override
    public List<SysRole> selectRoleByUserId(Long deptId,Long userId) {
        List<SysRole> userRoles = baseMapper.selectRolesByUserId(userId);
        List<SysRole> roles = baseMapper.selectList(new QueryWrapper<SysRole>().lambda().eq(SysRole::getDeptId,deptId));
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public List<SysRole> selectRoleByUserId(Long userId) {
        List<SysRole> userRoles = baseMapper.selectRolesByUserId(userId);
        List<SysRole> roles = this.list(null);
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId) {
         List<SysRole> perms = null;
        if(StringUtils.isNull(userId)){
            perms = baseMapper.selectList(null);
        }else {
            perms = baseMapper.selectRolesByUserId(userId);
        }
        Set<String> permsSet = new HashSet<>();
        if(!CollectionUtils.isEmpty(perms)) {
            for (SysRole role : perms) {
                if(null != role){
                    permsSet.addAll(Arrays.asList(role.getRoleKey().trim().split(",")));
                }
            }
        }
        return permsSet;
    }


    //获取系统配置最大角色创建数
    private Integer getMaxRoleCount(){
        Integer maxRoleCount = 10;
        try{
            //验证创建用户数量是否超限
            SysConfig config = configService.getConfigByKey("sys.user.maxCount");
            if(null != config){
                maxRoleCount = Integer.valueOf(config.getConfigValue());
            }
        }catch(Exception ex){
            maxRoleCount = 10;
            log.error("SysUserServiceImpl --> insertUser [sys.role.maxCount] 获取异常!");
        }
        return maxRoleCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(SysRole role) {
        Integer maxRoleCount = getMaxRoleCount();
        //获取当前部门下角色数量
        Integer roleCount = this.selectCountByDeptId(role.getDeptId());
        if(roleCount >= maxRoleCount){
            throw new ServiceException("8888", "超过部门角色创建数量！");
        }
        boolean result = this.save(role);
        if (!result) {
            throw new ServiceException(BaseConstants.ErrorCode.SYSTEM_FAIL);
        }
        return insertRoleMenu(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(SysRole role) {
        boolean result = this.updateById(role);
        if (!result) {
            throw new ServiceException(BaseConstants.ErrorCode.SYSTEM_FAIL);
        }
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().lambda().eq(SysRoleMenu::getRoleId,role.getRoleId()));
        return insertRoleMenu(role);
    }

    /**
     * 新增角色资源信息
     *
     * @param role 角色对象
     */
    private boolean insertRoleMenu(SysRole role) {
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();

        //TODO 权限控制
//        for (SysRoleMenu menu: role.getMenus()) {
//            SysRoleMenu rm = new SysRoleMenu();
//            rm.setRoleId(role.getRoleId());
//            rm.setMenuId(menu.getMenuId());
//            list.add(rm);
//        }
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            return sysRoleMenuService.saveBatch(list);
        }
        return false;
    }

    public boolean checkRoleNameUnique(SysRole role) {
        if (StringUtils.isNotNull(role) && StringUtils.isNotBlank(role.getRoleName())) {
            Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
            SysRole info = baseMapper.selectOne(new QueryWrapper<SysRole>().lambda()
                    .eq(SysRole::getRoleName, role.getRoleName())
                    .likeRight(StringUtils.isNotBlank(role.getDeptPaths()),SysRole::getDeptPaths,role.getDeptPaths()));
            if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean checkRoleKeyUnique(SysRole role) {
        if (StringUtils.isNotNull(role) && StringUtils.isNotBlank(role.getRoleKey())) {
            Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
            SysRole info = baseMapper.selectOne(new QueryWrapper<SysRole>().lambda()
                    .eq(SysRole::getRoleKey, role.getRoleKey())
                    .likeRight(StringUtils.isNotBlank(role.getDeptPaths()),SysRole::getDeptPaths,role.getDeptPaths()));
            if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    @Override
    public Integer selectCountByDeptId(Long deptId) {
        return baseMapper.selectCount(new QueryWrapper<SysRole>().lambda().eq(SysRole::getDeptId,deptId));
    }
}
