package com.house.variety.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.house.variety.modules.auth.entity.SysDept;
import com.house.variety.modules.auth.entity.SysUser;
import com.house.variety.modules.auth.entity.SysUserPost;
import com.house.variety.modules.auth.entity.SysUserRole;
import com.house.variety.modules.auth.mapper.SysUserMapper;
import com.house.variety.modules.auth.service.SysDeptService;
import com.house.variety.modules.auth.service.SysUserPostService;
import com.house.variety.modules.auth.service.SysUserRoleService;
import com.house.variety.modules.auth.service.SysUserService;
import com.house.variety.modules.system.entity.SysConfig;
import com.house.variety.modules.system.service.SysConfigService;
import com.house.variety.exception.ServiceException;
import com.house.variety.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserPostService userPostService;


    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysConfigService configService;


    @Override
    public IPage<SysUser> pageList(Page<SysUser> page, SysUser user) {
//        FrameLambdaQueryWrapper<SysUser> wrapper = new FrameQueryWrapper<SysUser>().lambda();
//        if (StringUtils.isNotBlank(user.getLoginName())) {
//            wrapper.like(SysUser::getLoginName, user.getLoginName());
//        }
//        if (StringUtils.isNotBlank(user.getPhonenumber())) {
//            wrapper.like(SysUser::getPhonenumber, user.getPhonenumber());
//        }
//        if (StringUtils.isNotBlank(user.getStatus())) {
//            wrapper.eq(SysUser::getStatus, user.getStatus());
//        }
//        if(StringUtils.isNotNull(user.getDeptId())){
//            SysDept dept = deptService.getById(user.getDeptId());
//            if(null != dept){
//                wrapper.likeRight(SysUser::getDeptPaths,dept.getAncestors());
//            }
//        }
////        wrapper.eq(StringUtils.isNotNull(user.getDeptId()),SysUser::getDeptId,user.getDeptId());
//        if (StringUtils.isNotNull(user.getParams())) {
//            String begin = String.valueOf(user.getParams().get("beginTime"));
//            if (StringUtils.isNotBlank(begin)) {
//                wrapper.ge(SysUser::getCreateTime, begin);
//            }
//            String end = String.valueOf(user.getParams().get("endTime"));
//            if (StringUtils.isNotBlank(end)) {
//                wrapper.le(SysUser::getCreateTime, end);
//            }
//        }
//        wrapper.ne(SysUser::getUserId,1L);//不查询超级用户
//        wrapper.orderBy(StringUtils.isNotBlank(user.getOrderByColumn()),user.isAsc(),user.getOrderByColumn());

        if(StringUtils.isNotNull(user.getDeptId())){
            SysDept dept = deptService.getById(user.getDeptId());
            if(null != dept){
                user.setDeptPaths(dept.getAncestors());
            }
        }
        if (StringUtils.isNotBlank(user.getOrderByColumn())) {
            user.setOrderByColumn(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, user.getOrderByColumn()));
        }
        return baseMapper.pageList(page, user);
    }

    //获取系统配置最大用户创建数
    private Integer getMaxUserCount(){
        Integer maxUserCount = 10;
        try{
            //验证创建用户数量是否超限
            SysConfig config = configService.getConfigByKey("sys.role.maxCount");
            if(null != config){
                maxUserCount = Integer.valueOf(config.getConfigValue());
            }
        }catch(Exception ex){
            maxUserCount = 10;
            log.error("SysUserServiceImpl --> insertUser [sys.role.maxCount] 获取异常!");
        }
        return maxUserCount;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertUser(SysUser user) {
        //限制部门用户创建数量
        Integer maxUserCount = getMaxUserCount();
        //获取当前部门下用户数量
        Integer userCount = this.selectCountByDeptId(user.getDeptId());
        if(userCount >= maxUserCount){
            throw new ServiceException("8888", "超过部门用户创建数量！");
        }
        SysDept dept = deptService.getById(user.getDeptId());
        if(null != dept){
            user.setDeptPaths(dept.getAncestors());
        }
        // 新增用户信息
        boolean rows = save(user);
        // 新增用户岗位关联
//        saveUserPost(user);
        // 新增用户与角色管理
        saveUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleService.remove(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, userId));
        // 新增用户与角色管理
        saveUserRole(user);
        // 删除用户与岗位关联
//        userPostService.remove(new QueryWrapper<SysUserPost>().lambda().eq(SysUserPost::getUserId, userId));
        // 新增用户与岗位管理
//        saveUserPost(user);
        SysDept dept = deptService.getById(user.getDeptId());
        if(null != dept){
            user.setDeptPaths(dept.getAncestors());
        }
        return this.updateById(user);
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void saveUserPost(SysUser user) {
        if(!CollectionUtils.isEmpty(user.getPostIds())){
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<>();
            user.getPostIds().forEach(postId -> {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            });
            if (list.size() > 0) {
                userPostService.saveBatch(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void saveUserRole(SysUser user) {
        if(!CollectionUtils.isEmpty(user.getRoleIds())){
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<>();
            user.getRoleIds().forEach(roleId->{
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            });
            if (list.size() > 0) {
                userRoleService.saveBatch(list);
            }
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public boolean checkLoginNameUnique(String loginName) {
        int count = this.count(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getLoginName, loginName)
                .eq(SysUser::getDelFlag,"0"));
        if (count > 0) {
            return false;
        }
        return true;
    }

    /**
     * 校验用户手机号码是否唯一
     *
     * @param user 用户
     * @return
     */
    @Override
    public boolean checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.getOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getPhonenumber, user.getPhonenumber())
                .eq(SysUser::getDelFlag,"0"));
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户
     * @return
     */
    @Override
    public boolean checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.getOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getEmail, user.getEmail())
                .eq(SysUser::getDelFlag,"0"));
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return false;
        }
        return true;
    }

    @Override
    public SysUser selectUserByLoginName(SysUser user) {
        LambdaQueryWrapper<SysUser> qw = new QueryWrapper<SysUser>().lambda();
        qw.eq(SysUser::getDelFlag, "0");
        if (StringUtils.isNotEmpty(user.getLoginName())) {
            return this.getOne(qw.eq(SysUser::getLoginName, user.getLoginName()));
        }
        if (StringUtils.isNotEmpty(user.getPhonenumber())) {
            return this.getOne(qw.eq(SysUser::getPhonenumber, user.getPhonenumber()));
        }
        if (StringUtils.isNotEmpty(user.getEmail())) {
            return this.getOne(qw.eq(SysUser::getEmail, user.getEmail()));
        }
        return null;
    }

    @Override
    public Set<Long> selectRoleIdByUserId(Long userId){
       List<SysUserRole> roles = userRoleService.list(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId,userId));
       Set<Long> roleIds = new HashSet<>();
       if(!CollectionUtils.isEmpty(roles)){
           roleIds = roles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
       }
       return roleIds;
    }


    @Override
    public SysUser selectUserById(Long userId){
        return baseMapper.selectUserById(userId);
    }


    @Override
    public Integer selectCountByDeptId(Long deptId) {
        return baseMapper.selectCount(new QueryWrapper<SysUser>().lambda().eq(SysUser::getDeptId,deptId));
    }
}

