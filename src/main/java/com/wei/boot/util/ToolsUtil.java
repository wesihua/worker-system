package com.wei.boot.util;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.wei.boot.contant.GlobalConstant;

import redis.clients.jedis.Jedis;

public class ToolsUtil {

	/**
     * 函数功能说明 ： 获得文件名的后缀名. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param fileName
     * @参数： @return
     * @return String
     * @throws
     */
    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取去掉横线的长度为32的UUID串.
     * 
     * @author WuShuicheng.
     * @return uuid.
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取带横线的长度为36的UUID串.
     * 
     * @author WuShuicheng.
     * @return uuid.
     */
    public static String get36UUID() {
        return UUID.randomUUID().toString();
    }
    
    /**
	 * 获取前端传递过来的token
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request) {
		String token = null;
		// 先从cookie中获取
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length == 0) {
			for(Cookie cookie : cookies) {
				if(GlobalConstant.TOKEN_NAME.equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}
		// 再从header中获取
		if(null == token) {
			token = request.getHeader(GlobalConstant.TOKEN_NAME);
		}
		return token;
	}
	
	/**
	 * 获取登录的userId
	 * @param request
	 * @return
	 */
	public static int getUserId(HttpServletRequest request) {
		Jedis jedis = JedisUtil.getJedis();
		String token = getToken(request);
		return Integer.parseInt(jedis.get(token));
	}
}
