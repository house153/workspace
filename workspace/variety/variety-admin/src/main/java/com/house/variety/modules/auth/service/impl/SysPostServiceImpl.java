package com.house.variety.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.modules.auth.entity.SysPost;
import com.house.variety.modules.auth.mapper.SysPostMapper;
import com.house.variety.modules.auth.service.SysPostService;
import com.house.variety.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {


    @Override
    public IPage<SysPost> pageList(Page<SysPost> page, SysPost sysPost) {

        LambdaQueryWrapper<SysPost> wrapper = new QueryWrapper<SysPost>().lambda();

        if (StringUtils.isNotBlank(sysPost.getPostName())) {
            wrapper.like(SysPost::getPostName, sysPost.getPostName());
        }
        if (StringUtils.isNotBlank(sysPost.getPostCode())) {
            wrapper.like(SysPost::getPostCode, sysPost.getPostCode());
        }
        if (StringUtils.isNotBlank(sysPost.getStatus())) {
            wrapper.eq(SysPost::getStatus, sysPost.getStatus());
        }

        wrapper.orderByAsc(SysPost::getPostSort);
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean checkPostNameUnique(SysPost post) {
        if (StringUtils.isNotNull(post) && StringUtils.isNotBlank(post.getPostName())) {
            Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
            SysPost info = baseMapper.selectOne(new QueryWrapper<SysPost>().lambda().eq(SysPost::getPostName, post.getPostName()));
            if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPostCodeUnique(SysPost post) {
        if (StringUtils.isNotNull(post) && StringUtils.isNotBlank(post.getPostCode())) {
            Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
            SysPost info = baseMapper.selectOne(new QueryWrapper<SysPost>().lambda().eq(SysPost::getPostCode, post.getPostCode()));
            if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public List<SysPost> selectPostByUserId(Long userId) {
        List<SysPost> userPosts = baseMapper.selectPostByUserId(userId);
        List<SysPost> posts = this.list(null);
        for (SysPost post : posts) {
            for (SysPost userRole : userPosts) {
                if (post.getPostId().longValue() == userRole.getPostId().longValue()) {
                    post.setFlag(true);
                    break;
                }
            }
        }
        return posts;
    }
}
