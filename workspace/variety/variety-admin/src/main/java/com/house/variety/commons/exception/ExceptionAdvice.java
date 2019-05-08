/*
 * 文 件 名:  ExceptionAdvice.java
 * 版    权:  Copyright © 2015-2017, 湖南物联聚创信息科技有限公司
 * 描    述:  ExceptionAdvice.java
 * 版    本：   1.0
 * 创 建 人:  HuangChao
 * 创建时间: 2017年10月30日 下午1:33:38
 */
package com.house.variety.commons.exception;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.house.variety.commons.utils.ServletUtils;
import com.house.variety.exception.HslException;
import com.house.variety.pojo.HslResponse;
import com.house.variety.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 描述  统一异常解析<br/>
 * 创建人  HuangChao <br/>
 * 创建时间  2018/10/25 14:02
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {


    @Resource
    private MessageSource messageSource;

    /**
     * 描述  系统业务异常处理<br/>
     * 参数  [ex, request] <br/>
     * 返回值  java.lang.Object <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/25 14:03
     */
    @ExceptionHandler(HslException.class)
    public Object hslHandleException(HslException ex, HttpServletRequest request) {
        log.error("ExceptionAdvice -> hslHandleException exception:[{}]", ex);
        HslResponse result = new HslResponse();
        String code = ex.getCode();
        String message = null;
        if (StringUtils.isNotBlank(code)) {
            try {
                // 获取国际化资源
                message = messageSource.getMessage(code, null, getLocale());
            } catch (Exception e) {
                log.error("get code desc fail! errorCode：" + code);
                message = ex.getMessage();
            }
        }
        if (StringUtils.isEmpty(message)) {
            message = "发生未知的错误，请联系管理员！";
        }
        result.setCode(code);
        result.setMsg(message);
        if (!ServletUtils.isAjaxRequest(request)) {
            return view(result,ex);
        }
        return result;
    }


    /**
     * 描述  权限校验失败<br/>
     * 参数  [ex, request] <br/>
     * 返回值  java.lang.Object <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/25 14:03
     */
    @ExceptionHandler(AuthorizationException.class)
    public Object authorizationException(AuthorizationException ex, HttpServletRequest request) {
        log.error("ExceptionAdvice -> authorizationException exception:[{}]", ex);
        if (ServletUtils.isAjaxRequest(request)) {
            HslResponse result = new HslResponse();
            result.setCode("8888");
            result.setMsg("您没有访问权限！");
            return result;

        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error/unauth");
        return mv;
    }

    /**
     * 描述  默认异常拦截处理<br/>
     * 参数  [ex, request] <br/>
     * 返回值  java.lang.Object <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/25 14:03
     */
    @ExceptionHandler(Exception.class)
    public Object defaultErrorHandler(Exception ex, HttpServletRequest request) {
        log.error("ExceptionAdvice -> defaultErrorHandler exception:[{}]", ex);
        HslResponse result = new HslResponse();
        String code = ex.getMessage();
        String message = null;
        if (StringUtils.isNotBlank(code)) {
            try {
                // 获取国际化资源
                message = messageSource.getMessage(code, null, getLocale());
            } catch (Exception e) {
                log.error("get code desc fail! errorCode：" + code);
                //message= ex.getMessage();
                code = "8888";
            }
        }

        if (StringUtils.isEmpty(message)) {
            message = "发生未知的错误，请联系管理员！";
        }
        result.setCode(code);
        result.setMsg(message);
        if (!ServletUtils.isAjaxRequest(request)) {
            return view(result,ex);
        }
        return result;
    }


    /**
     * 描述  获取当前语言<br/>
     * 参数  [] <br/>
     * 返回值  Locale <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/25 14:03
     */
    private Locale getLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        //设置默认语言
        if (ObjectUtils.isNull(locale)) {
            locale = Locale.CHINESE;
        }
        return locale;
    }

    /**
     * 描述  返回视图<br/>
     * 参数  [result] <br/>
     * 返回值  ModelAndView <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/25 14:00
     */
    private ModelAndView view(HslResponse result,Exception ex) {
        ModelAndView mv = new ModelAndView();
//        mv.addObject("result", result);
        mv.addObject("ex", ex);
        mv.setViewName("/error/500");
        return mv;
    }
}
