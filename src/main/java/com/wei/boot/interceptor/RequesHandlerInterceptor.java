package com.wei.boot.interceptor;

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
import com.wei.boot.util.ToolsUtil;

import redis.clients.jedis.Jedis;

public class RequesHandlerInterceptor implements HandlerInterceptor {

	public static final Logger log = LoggerFactory.getLogger(RequesHandlerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		//测试情况，直接跳过拦截
		//return true;
		
		///**
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		
		Jedis jedis = JedisUtil.getJedis();
		String path = request.getRequestURL().toString();
		String servletPath = request.getServletPath();
		log.info("拦截到请求URL： [ "+path+" ]，开始验证权限...");
		if("/".equals(servletPath) || "/logout".equals(servletPath) || "/error".equals(servletPath)
				|| "/account/login".equals(servletPath) || "/home.html".equals(servletPath)) {
			return true;
		}
		// 首先确认token是否传递
		String token = ToolsUtil.getToken(request);
		if(StringUtils.isEmpty(token)) {
			log.error("请求 [ "+path+" ] 验证失败， 非法请求");
			String responseStr = JsonUtil.bean2Json(Result.fail(GlobalConstant.ILLEGAL_REQUEST, "非法请求"));
			response.getWriter().write(responseStr);
			return false;
		}
		// 再确认token是否已过期或者是假token,过期则直接跳到登录界面
		if(!jedis.exists(token)) {
			log.error("请求 [ "+path+" ]验证失败， token已过期");
			String responseStr = JsonUtil.bean2Json(Result.fail(GlobalConstant.TOKEN_EXPIRED, "token已过期，请重新登录"));
			response.getWriter().write(responseStr);
			return false;
		}
		// 开始验证token是否正确,不正确说明用户重新登录了，这时将原来的请求下线
		String userId = jedis.get(token);
		String storedToken = jedis.get(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+userId);
		if(!token.equals(storedToken)) {
			log.error("请求 [ "+path+" ]验证失败， token已过期");
			String responseStr = JsonUtil.bean2Json(Result.fail(GlobalConstant.TOKEN_EXPIRED, "token已过期，请重新登录"));
			response.getWriter().write(responseStr);
			return false;
		}
		// token验证通过，将token有效时间重置
		jedis.set(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+userId, token, "NX", "EX", 30*60);// 30分钟有效期，用来存放token
		jedis.set(token, userId, "NX", "EX", 30*60);// 30分钟有效期，用来存放userId
		jedis.close();
		log.info("请求 [ "+path+" ] 验证通过！");
		return true;
		
		//**/
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
}
