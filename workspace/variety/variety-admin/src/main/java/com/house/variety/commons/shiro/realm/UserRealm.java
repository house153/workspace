package com.house.variety.commons.shiro.realm;

import com.house.variety.commons.constant.Constants;
import com.house.variety.commons.constant.ShiroConstants;
import com.house.variety.commons.constant.UserConstants;
import com.house.variety.commons.exception.security.*;
import com.house.variety.commons.manager.AsyncManager;
import com.house.variety.commons.service.DubboSupport;
import com.house.variety.commons.shiro.service.PasswordService;
import com.house.variety.commons.utils.ServletUtils;
import com.house.variety.commons.utils.SpringUtils;
import com.house.variety.commons.utils.security.ShiroUtils;
import com.house.variety.modules.auth.entity.SysDept;
import com.house.variety.modules.auth.entity.SysUser;
import com.house.variety.modules.auth.enums.UserStatus;
import com.house.variety.modules.auth.service.SysDeptService;
import com.house.variety.modules.auth.service.SysUserService;
import com.house.variety.modules.system.entity.SysLogininfor;
import com.house.variety.seq.IdUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.TimerTask;


/**
 * 描述  自定义Realm 处理登录 权限<br/>
 * 参数   <br/>
 * 返回值   <br/>
 * 创建人  HuangChao <br/>
 * 创建时间  2018/9/14 11:15
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private PasswordService passwordService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SysUser user = ShiroUtils.getUser();
        Long userId = user.getUserId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        DubboSupport dubboSupport = SpringUtils.getBean("dubboSupport");
        if (user.isAdmin()) {
            userId = null;
        }
        // 角色加入AuthorizationInfo认证对象
        info.setRoles(dubboSupport.roleService.selectRoleKeys(userId));
//        // 权限加入AuthorizationInfo认证对象
        info.setStringPermissions(dubboSupport.menuService.selectPermsByUserId(userId));
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        SysUser user = null;
        try {
            user = login(username, password);
        } catch (CaptchaException | UserDeptNotFoundException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        }  catch (Exception e) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException("对用户["+ username +"]进行登录验证,验证未通过,请联系管理员!");
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }


    /**
     * 登录
     */
    public SysUser login(String username, String password) {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new UserPasswordNotMatchException();
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new UserPasswordNotMatchException();
        }

        DubboSupport dubboSupport = SpringUtils.getBean("dubboSupport");
        SysUserService userService = dubboSupport.userService;
        SysUser user = new SysUser();
        user.setLoginName(username);
        // 查询用户信息
        user = userService.selectUserByLoginName(user);
        if (user == null && maybeMobilePhoneNumber(username)) {
            user = new SysUser();
            user.setPhonenumber(username);
            user = userService.selectUserByLoginName(user);
        }
        if (user == null && maybeEmail(username)) {
            user = new SysUser();
            user.setEmail(username);
            user = userService.selectUserByLoginName(user);
        }
        if (user == null || UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            AsyncManager.me().execute(recordLogininfor(username, Constants.LOGIN_FAIL, "登录失败", "用户不存在/密码错误"));
            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);

        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            AsyncManager.me().execute(recordLogininfor(username, Constants.LOGIN_FAIL, "登录失败", "用户已封禁，原因：" + user.getRemark()));
            throw new UserBlockedException("用户已锁定" + user.getRemark());
        }

        //记录登录信息
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(new Date());
        userService.updateById(user);

        //查询用户部门信息
        SysDeptService deptService = dubboSupport.deptService;
        SysDept userDept = deptService.getById(user.getDeptId());
        if(null == userDept || !userDept.getStatus().equalsIgnoreCase("0")){
            throw new UserDeptNotFoundException();
        }
        user.setDept(userDept);
        //查询用户角色列表
        user.setRoleIds(userService.selectRoleIdByUserId(user.getUserId()));

        //记录登录日志
        AsyncManager.me().execute(recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功"));
        return user;
    }

    /**
     * 描述  记录登录日志<br/>
     * 参数  [username, status, message, args] <br/>
     * 返回值  java.util.TimerTask <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/24 14:09
     */
    private TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        return new TimerTask() {
            @Override
            public void run() {
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setInfoId(IdUtil.getId());
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ShiroUtils.getIp());
//                logininfor.setLoginLocation(AddressUtils.getRealAddressByIP(ShiroUtils.getIp()));
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
                    logininfor.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(Constants.FAIL);
                }
                logininfor.setLoginTime(new Date());
                // 插入数据
                DubboSupport dubboSupport = SpringUtils.getBean("dubboSupport");
                dubboSupport.logininforService.save(logininfor);
            }
        };
    }

    private boolean maybeEmail(String username) {
        if (!username.matches(UserConstants.EMAIL_PATTERN)) {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username) {
        if (!username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN)) {
            return false;
        }
        return true;
    }
}
