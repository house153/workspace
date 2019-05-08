package com.house.variety.modules.auth.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.auth.entity.SysPost;
import com.house.variety.modules.auth.service.SysPostService;
import com.house.variety.annotation.DyField;
import com.house.variety.enums.DyType;
import com.house.variety.pojo.HslResponse;
import com.house.variety.seq.IdUtil;
import com.house.variety.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
@Controller
@RequestMapping("/auth/post")
public class SysPostController extends BaseController {
    private String prefix = "auth/post";

    @Resource
    private SysPostService postService;

    @RequiresPermissions("system:post:view")
    @GetMapping()
    public String post() {
        return prefix + "/post";
    }

    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public HslResponse<IPage<SysPost>> list(SysPost post) {
        return HslResponse.ok(postService.pageList(getPage(), post));
    }


    @RequiresPermissions("system:post:remove")
    @PostMapping("/remove")
    @ResponseBody
    public HslResponse remove(String ids) {
        boolean result = postService.removeByIds(Arrays.asList(ids.split("\\,")));
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 新增岗位
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:post:add")
    @PostMapping("/add")
    @ResponseBody
    @DyField(type = DyType.ADD)
    public HslResponse addSave(SysPost post) {
        post.setPostId(IdUtil.getId());
        boolean result = postService.save(post);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 修改岗位
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
        mmap.put("post", postService.getById(postId));
        return prefix + "/edit";
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:post:edit")
    @PostMapping("/edit")
    @ResponseBody
    @DyField(type = DyType.EDIT)
    public HslResponse editSave(SysPost post) {
        boolean result = postService.updateById(post);
        if (result) {
            return HslResponse.ok("success");
        }
        return HslResponse.failed("操作异常，请联系管理!");
    }

    /**
     * 校验岗位名称
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public int checkPostNameUnique(SysPost post) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(post)) {
            uniqueFlag = postService.checkPostNameUnique(post);
        }
        return uniqueFlag ? 0 : 1;
    }

    /**
     * 校验岗位编码
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public int checkPostCodeUnique(SysPost post) {
        boolean uniqueFlag = false;
        if (StringUtils.isNotNull(post)) {
            uniqueFlag = postService.checkPostCodeUnique(post);
        }
        return uniqueFlag ? 0 : 1;
    }

}

