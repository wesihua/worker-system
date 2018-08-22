package com.wei.boot.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.Result;
import com.wei.boot.util.JedisUtil;
import com.wei.boot.util.JsonUtil;

import redis.clients.jedis.Jedis;

public class RequesHandlerInterceptor implements HandlerInterceptor {

	public static final Logger log = LoggerFactory.getLogger(RequesHandlerInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		//测试情况，直接跳过拦截
		return true;
		/**
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		
		Jedis jedis = JedisUtil.getJedis();
		String path = request.getRequestURL().toString();
		String servletPath = request.getServletPath();
		log.info("拦截到请求URL： [ "+path+" ]，开始验证权限...");
		if("/".equals(servletPath) || "/error".equals(servletPath)) {
			return true;
		}
		// 首先确认token是否传递
		String token = getToken(request);
		if(StringUtils.isEmpty(token)) {
			log.error("请求 [ "+path+" ] 验证失败， 非法请求");
			String responseStr = JsonUtil.bean2Json(Result.fail(GlobalConstant.ILLEGAL_REQUEST, "非法请求"));
			response.getWriter().write(responseStr);
			return false;
		}
		// 再确认token是否已过期,直接跳到登录界面【用户重新登录、退出登录等，这时会删除redis中的token】
		// 解析出userId
		String userId = token.substring(token.lastIndexOf("_"));
		if(!jedis.exists(userId)) {
			log.error("请求 [ "+path+" ]验证失败， token已过期");
			String responseStr = JsonUtil.bean2Json(Result.fail(GlobalConstant.TOKEN_EXPIRED, "token已过期，请重新登录"));
			response.getWriter().write(responseStr);
			//response.sendRedirect("/");
			return false;
		}
		// token验证通过，将token有效时间重置
		jedis.set(userId, "0", "XX", "EX", 30*60);
		log.info("请求 [ "+path+" ] 验证通过！");
		return true;
		
		**/
	}

}
