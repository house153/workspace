package com.house.variety.modules.auth.controller;

import com.google.code.kaptcha.Producer;
import com.house.variety.commons.controller.BaseController;
import com.house.variety.modules.auth.entity.SysUser;
import com.house.variety.modules.auth.service.SysUserService;
import com.house.variety.pojo.HslResponse;
import com.house.variety.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/10/16 14:58
 * @描述 登录控制器
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private SysUserService sysUserService;

    @GetMapping()
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // 如果是Ajax请求，返回Json字符串。
//        if (ServletUtils.isAjaxRequest(request)) {
//            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
//        }
        return "login";
    }

    @PostMapping()
    @ResponseBody
    public HslResponse login(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return HslResponse.ok("sucess");
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return HslResponse.failed(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "/error/unauth";
    }


    /**
     * 是否需要短信验证
     */
    @PostMapping("/smsCheck")
    @ResponseBody
    public boolean smsCheck(SysUser user) {
        if (StringUtils.isNotNull(user)) {
            user = sysUserService.selectUserByLoginName(user);
            if(StringUtils.isNotNull(user)){
                return user.getSmsCheck() == 1;
            }
        }
        return false;
    }
}
