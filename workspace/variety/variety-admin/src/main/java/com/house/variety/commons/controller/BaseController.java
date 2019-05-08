package com.house.variety.commons.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.variety.commons.utils.security.ShiroUtils;
import com.house.variety.config.FtpConfig;
import com.house.variety.modules.auth.entity.SysDept;
import com.house.variety.modules.auth.entity.SysUser;
import com.house.variety.modules.auth.enums.DeptType;
import com.house.variety.pojo.FileInfo;
import com.house.variety.seq.IdUtil;
import com.house.variety.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/8/23 17:31
 * @描述 公共的控件器、通用数据处理
 */
@Slf4j
public class BaseController {

    @Autowired
    private HttpServletRequest reuqest;

    @Autowired
    protected FtpConfig ftpConfig;


    protected  final String HTML_HEAD="<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";

    /**
     * 管理员用户ID
     */
    protected final Long ADMIN_USER_ID = 1L;

    /**
     * 管理员角色ID
     */
    protected final Long ADMIN_ROLE_ID = 1L;

    /**
     * 平台角色ID
     */
    protected final Long PLATFORM_ROLE_ID = 2L;

    /**
     * 描述  文件上传<br/>
     * 参数  [file] <br/>
     * 返回值  String 文件完整路径<br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/27 16:23
     */
    protected String uploadFile(MultipartFile file) {
        try {
            return uploadFile(new FileInfo(file.getOriginalFilename(), file.getSize(), file.getInputStream()));
        } catch (IOException e) {
            log.error("BaseController--->uploadFile upload failure {}", e);
            return null;
        }
    }


    /**
     * 描述  文件上传<br/>
     * 参数  [file] <br/>
     * 返回值  String 文件完整路径<br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/27 16:23
     */
    protected String uploadFile(FileInfo file) {
        if (null == file) {
            return null;
        }
        try {
            // 获取文件名
            String originalFilename = file.getFileName();
            String targetFileName = IdUtil.getUUID() + "." + FilenameUtils.getExtension(originalFilename);
            String filePath = FtpUtil.datePath();
            log.debug("originalFilename = {},targetFileName = {}, filePath = {}", originalFilename, targetFileName, filePath);

            boolean success = FtpUtil.uploadFile(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath(), filePath, targetFileName, file.getInputStream());
            if (success) {
                return ftpConfig.getHttpAccessPath() + filePath + "/" + targetFileName;
            } else {
                log.error("BaseController--->upload failure");
                return null;
            }
        } catch (Exception e) {
            log.error("BaseController--->upload failure {}", e);
            return null;
        }
    }

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /**
     * 描述  获取分页参数<br/>
     * 参数  [] <br/>
     * 返回值  Page <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/21 15:40
     */
    protected Page getPage() {
        int pageSize = 10;
        int pageNum = 0;
        try {
            pageSize = Integer.valueOf(reuqest.getParameter("pageSize"));
            pageNum = Integer.valueOf(reuqest.getParameter("pageNum"));
        } catch (Exception ex) {

        }
        return new Page(pageNum, pageSize);
    }


    protected Long getUserId() {
        SysUser user = getUserInfo();
        if (null != user) {
            return user.getUserId();
        }
        return null;
    }

    protected SysUser getUserInfo() {
        return ShiroUtils.getUser();
    }

    protected Set<Long> getUserRoleIds() {
        return getUserInfo().getRoleIds();
    }

    /**
     * 描述  获取用户部门信息<br/>
     * 参数  [] <br/>
     * 返回值  java.lang.Long <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/20 16:02
     */
    protected SysDept getUserDeptInfo() {
        SysUser user = getUserInfo();
        if (null != user) {
            return user.getDept();
        }
        return null;
    }

    /**
     * 描述  获取用户数据权限标识<br/>
     * 参数  [] <br/>
     * 返回值  java.lang.String <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/20 16:01
     */
    protected String getDeptPaths() {
        SysUser user = getUserInfo();
        if (null != user) {
            if(user.getDept().getType().intValue() == DeptType.DEPT.getCode().intValue()){
                return user.getDept().getExtendB();
            }else {
                return user.getDept().getAncestors();
            }
        }
        return null;
    }

    /**
     * 描述  获取用户部门ID<br/>
     * 参数  [] <br/>
     * 返回值  java.lang.String <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/20 16:01
     */
    protected Long getDeptId() {
        SysUser user = getUserInfo();
        if (null != user) {
            if(user.getDept().getType().intValue() == DeptType.DEPT.getCode().intValue()){
                return Long.valueOf(user.getDept().getExtendA());
            }else {
                return user.getDept().getDeptId();
            }
        }
        return null;
    }

    public void setUser(SysUser user) {
        ShiroUtils.setUser(user);
    }


    protected String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

    /**
     *描述  判断是否为Admin<br/>
     *参数  [] <br/>
     *返回值  boolean <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/11/12 17:22
    */
    protected boolean isAdmin() {
        return getUserId() != null && ADMIN_USER_ID == getUserId();
    }

    /**
     *描述  判断是否为平台<br/>
     *参数  [] <br/>
     *返回值  boolean <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/11/12 17:22
    */
    protected  boolean isPlatForm(){
        return getUserRoleIds().contains(PLATFORM_ROLE_ID);
    }

    protected HttpServletRequest getReuqest() {
        return reuqest;
    }
}
