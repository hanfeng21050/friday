package com.hf.friday.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hf.friday.base.ApplicationContextHolder;
import com.hf.friday.base.ResponseCode;
import com.hf.friday.cache.RedisCache;
import com.hf.friday.util.RequestWrapper;
import com.hf.friday.util.TokenUtil;
import com.hf.friday.vo.PageTableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 漫画app请求的拦截器
 * @Author CoolWind
 * @Date 2020/4/29 18:06
 */
public class ComicInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        //开启验证token
        Map map = JSON.parseObject(body);
        String token = (String) map.get("token");
        //验证token的合法性
        //返回正数表示用户id,负数表示非法
        if(token != null)
        {
            response.setCharacterEncoding("UTF-8");
            Integer res = TokenUtil.verifyToken(token);
            if(res == -1)
            {
                //token非法
                PrintWriter out = null ;
                try{
                    JSONObject json = new JSONObject();
                    json.put("code",ResponseCode.TOKEN_INVALID.getCode().toString());
                    json.put("message",ResponseCode.TOKEN_INVALID.getMessage());
                    out = response.getWriter();
                    out.append(json.toString());
                    return false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(500);
                    return false;
                }
            }else if(res == -2)
            {
                //token过期
                PrintWriter out = null ;
                try{
                    JSONObject json = new JSONObject();
                    json.put("code",ResponseCode.TOKEN_EXPIRE.getCode().toString());
                    json.put("message",ResponseCode.TOKEN_EXPIRE.getMessage());
                    out = response.getWriter();
                    out.append(json.toString());
                    return false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(500);
                    return false;
                }
            }else {
                //token校验成功
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
