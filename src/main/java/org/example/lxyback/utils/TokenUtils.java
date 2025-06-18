package org.example.lxyback.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/*
生成Token
return
 */
public class TokenUtils {
    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId)//将userId存到Token里作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//2小时候token过期
                .sign(Algorithm.HMAC256(sign));//将password作为token密钥
    }
}
