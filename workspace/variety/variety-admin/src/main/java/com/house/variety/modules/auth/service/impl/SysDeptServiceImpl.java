package com.house.variety.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.modules.auth.entity.SysDept;
import com.house.variety.modules.auth.enums.DeptType;
import com.house.variety.modules.auth.mapper.SysDeptMapper;
import com.house.variety.modules.auth.service.SysDeptService;
import com.house.variety.modules.auth.service.SysUserService;
import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统部门表 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Resource
    private SysUserService sysUserService;

    @Override
    public List<SysDept> listByDept(SysDept sysDept) {
        LambdaQueryWrapper<SysDept> wrapper = new QueryWrapper<SysDept>().lambda();
        if (StringUtils.isNotBlank(sysDept.getDeptName())) {
            wrapper.eq(SysDept::getDeptName, sysDept.getDeptName());
        }
        if (StringUtils.isNotBlank(sysDept.getStatus())) {
            wrapper.in(SysDept::getStatus, Arrays.asList(sysDept.getStatus()));
        }else {
            wrapper.in(SysDept::getStatus, Arrays.asList("0","1"));
        }
        if (StringUtils.isNotNull(sysDept.getDeptId())) {
            wrapper.eq(SysDept::getDeptId, sysDept.getDeptId());
        }
        if (StringUtils.isNotNull(sysDept.getParentId())) {
            wrapper.eq(SysDept::getParentId, sysDept.getParentId());
        }
        if (StringUtils.isNotBlank(sysDept.getAncestors())) {
            wrapper.likeRight(SysDept::getAncestors, sysDept.getAncestors());
        }
        if (StringUtils.isNotBlank(sysDept.getStatus())) {
            wrapper.eq(SysDept::getStatus, sysDept.getStatus());
        }
        if(StringUtils.isNotNull(sysDept.getType())){
            wrapper.eq(SysDept::getType, sysDept.getType());
        }
        wrapper.orderByAsc(SysDept::getOrderNum);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public SysDept selectDeptById(Long deptId) {
        return baseMapper.selectDeptById(deptId);
    }


    public boolean saveDept(SysDept dept){
        SysDept parent = this.getOne(new QueryWrapper<SysDept>().lambda().eq(SysDept::getDeptId,dept.getParentId()));
        dept.setAncestors(parent.getAncestors() + "," + dept.getDeptId());
        deptExtend(dept);
        return this.save(dept);
    }

    /**
     *描述  部门扩展<br/>
     *参数  [dept] <br/>
     *返回值  SysDept <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/12/22 10:11
     */
    private SysDept deptExtend(SysDept dept){
        //1、判断部门类型是否为：部门、运营商
        if(dept.getType().intValue() != DeptType.DEPT.getCode().intValue()){
            return dept;
        }
        //2、如为部门则需获取归属运营商数据
        SysDept parentDept=  selectDeptById(dept.getParentId());
        if(null == parentDept){
            //异常处理
        }
        String opDeptId = StringUtils.isNotBlank(parentDept.getExtendA()) ? parentDept.getExtendA() : String.valueOf(parentDept.getDeptId());
        String opDeptPaths = StringUtils.isNotBlank(parentDept.getExtendB())?parentDept.getExtendB() : parentDept.getAncestors();
        //3、
        dept.setExtendA(opDeptId);
        dept.setExtendB(opDeptPaths);
        return dept;
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public boolean updateDept(SysDept dept){
        SysDept parent = this.getOne(new QueryWrapper<SysDept>().lambda().eq(SysDept::getDeptId,dept.getParentId()));
        String ancestors = parent.getAncestors() + "," + dept.getDeptId();
        dept.setAncestors(ancestors);
        //updateDeptChildren(dept.getDeptId(), ancestors);
        deptExtend(dept);
        baseMapper.updateById(dept);
        return true;
    }


    /**
     * 修改子元素关系
     *
     * @param deptId 部门ID
     * @param ancestors 元素列表
     */
    private void updateDeptChildren(Long deptId, String ancestors) {
        List<SysDept> childrens = this.list(new QueryWrapper<SysDept>().lambda().eq(SysDept::getParentId,deptId));
        for (SysDept children : childrens)
        {
            children.setAncestors(ancestors + "," + deptId);
        }
        if (childrens.size() > 0)
        {
            // deptMapper.updateDeptChildren(childrens);
        }
    }

    @Override
    public boolean checkDeptNameUnique(SysDept dept) {
        if (StringUtils.isNotNull(dept) && StringUtils.isNotBlank(dept.getDeptName())) {
            Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
            SysDept info = baseMapper.selectOne(new QueryWrapper<SysDept>().lambda().eq(SysDept::getDeptName, dept.getDeptName())
                    .likeRight(StringUtils.isNotBlank(dept.getAncestors()),SysDept::getAncestors,dept.getAncestors()));
            if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean selectDeptCount(Long parentId) {
        List<SysDept> depts = baseMapper.selectList(new QueryWrapper<SysDept>().lambda().eq(SysDept::getParentId, parentId));
        if (null != depts && !depts.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        // 查询用户表是否存在deptId的用户
        Integer count = sysUserService.selectCountByDeptId(deptId);
        return count > 0 ;
    }
}
