package com.sunyard.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sunyard.common.base.ResultData;
import com.sunyard.common.base.RetCode;
import com.sunyard.common.util.CurrentUserUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;


@Slf4j
public class AccessFilter extends ZuulFilter {
	
	private static final String[] LIST = new String[] {
		"/sys/login/login",
		"/sys/login/getImgCode",
		"/sys/login/getUserInfo",
		"/sys/login/getMenu",
		"/uaa/oauth/user"
	};
	
	@Value("${filterStart.status}")
	private boolean status;

    @Override
    public String filterType() {
        //前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //优先级，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，true代表需要过滤
        return status;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String path = request.getRequestURL().toString();
        log.info("send {} request to {}", request.getMethod(), path);
        for (int i = 0; i < LIST.length; i++) {
        	String passPath = LIST[i];
        	if(passPath.equals(StringUtils.substringAfter(path, "18023"))){
        		ctx.setSendZuulResponse(true);
        		return null;
        	}
		}
        String userName = CurrentUserUtil.getCurrentUserDTO().getUserId();
        if("admin".equals(userName)){
        	ctx.setSendZuulResponse(true);
    		return null;
        }


        //TODO 这里可以做按钮级权限拦截,如果只做菜单级,可忽略
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (Iterator<? extends GrantedAuthority> it = authorities.iterator(); it.hasNext(); ) {
            GrantedAuthority auths = it.next();
            if(auths.getAuthority().equals(request.getRequestURL().toString())){
                ctx.setSendZuulResponse(true);
                return null;
            }
        }
        ctx.setSendZuulResponse(false);
        ctx.getResponse().setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        ctx.setResponseBody(ResultData.failed(RetCode.ACCESS_DENIED).toJSON());
        return null;
    }

}
