package com.house.variety.modules.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.auth.entity.SysPost;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
public interface SysPostService extends IService<SysPost> {

    /**
     * 描述  分页查询<br/>
     * 参数  [page, sysPost] <br/>
     * 返回值  IPage<SysPost> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 22:59
     */
    IPage<SysPost> pageList(Page<SysPost> page, SysPost sysPost);

    /**
     * 描述  检查岗位名称是否唯一<br/>
     * 参数  [post] <br/>
     * 返回值  boolean <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 22:57
     */
    boolean checkPostNameUnique(SysPost post);

    /**
     * 描述  检查岗位编码是否唯一<br/>
     * 参数  [post] <br/>
     * 返回值  boolean <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 22:57
     */
    boolean checkPostCodeUnique(SysPost post);

    /**
     *描述  根据用户ID查询岗位<br/>
     *参数  [userId] <br/>
     *返回值  List<SysPost> <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/12 11:35
    */
    List<SysPost> selectPostByUserId(Long userId);
}
