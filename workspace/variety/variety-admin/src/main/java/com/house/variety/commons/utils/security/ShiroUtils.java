package com.house.variety.commons.utils.security;

import com.house.variety.commons.shiro.realm.UserRealm;
import com.house.variety.modules.auth.entity.SysUser;
import com.house.variety.util.BeanConvertUtils;
import com.house.variety.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * 描述  Shiro 工具类<br/>
 * 参数   <br/>
 * 返回值   <br/>
 * 创建人  HuangChao <br/>
 * 创建时间  2018/9/14 11:15
 */
public class ShiroUtils {

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout() {
        getSubjct().logout();
    }

    public static SysUser getUser() {
        SysUser user = null;
        Object obj = getSubjct().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new SysUser();
            BeanConvertUtils.copyProperties(user, obj);
        }
        return user;
    }

    public static void setUser(SysUser user) {
        Subject subject = getSubjct();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    public static Long getUserId() {
        return getUser().getUserId().longValue();
    }

    public static String getLoginName() {
        return getUser().getLoginName();
    }

    public static String getIp() {
        return getSubjct().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubjct().getSession().getId());
    }
}
