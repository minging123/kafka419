package com.sunyard.gateway.filters;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sunyard.common.base.ResultData;
import com.sunyard.common.base.RetCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * @Author: shikang
 * @Description:
 * @Date: Created in 2018/3/7 17:34
 */
@Slf4j
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getResponseStatusCode() != HttpStatus.OK.value();
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ResultData result = filterError(ctx.getResponseStatusCode(),ctx.getRequest().getRequestURL().toString());
        if(result != null){
            log.error("{} 请求出错,已转换为通用返回结构",ctx.getRequest().getRequestURL());
            ctx.getResponse().setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
            ctx.setResponseBody(result.toJSON());
//        ctx.setResponseStatusCode(HttpStatus.OK.value());
        }
        return null;
    }

    private ResultData filterError(int httpCode,String url){
        ResultData result = null;
        if(httpCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            result = ResultData.failed(RetCode.EXCEPTION);
        }else if(httpCode == HttpStatus.BAD_REQUEST.value()){
            if(url.contains("uaa/oauth")){
                result = ResultData.failed(RetCode.TOKEN_OVERTIME);
            }else{
                result = ResultData.failed(RetCode.FAILED);
            }
        }else if(httpCode == HttpStatus.UNAUTHORIZED.value()){
            result = ResultData.failed(RetCode.TOKEN_OVERTIME);
        }else if(httpCode == HttpStatus.FORBIDDEN.value()){
            result = ResultData.failed(RetCode.ACCESS_DENIED);
        }else if(httpCode == HttpStatus.NOT_FOUND.value()){
            result = ResultData.failed(RetCode.FAILED,"请求地址不存在");
        }else if (httpCode == HttpStatus.METHOD_NOT_ALLOWED.value()){
            result = ResultData.failed(RetCode.DATA_INVALID,"请求方式错误");
        }else {
            //TODO 作为前后端分离设计的应用,就不应该以HttpStatus为返回值,内容都应该包含在返回体中
//            result = ResultData.failed(RetCode.FAILED);
        }
        return result;
    }
}
