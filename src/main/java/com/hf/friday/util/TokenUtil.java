package com.hf.friday.util;

import com.alibaba.fastjson.JSON;
import com.hf.friday.base.Constants;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

/**
 * 生成token,用于手机app登录验证
 * @Author CoolWind
 * @Date 2020/4/29 19:40
 */
public class TokenUtil {
    private static final byte[] SECRET = "GL62M7RDMSIPCI-EGEN.3SSD(OPTIONAL".getBytes();
    /**
     * 过期时间一分重
     */
    private static final long EXPIRE_TIME = 1000 * 60 * 1;

    /**
     * 生成Token
     * @param id
     * @return
     */
    public static String buildJWT(Integer id) {
        try {
            /**
             * 1.创建一个32-byte的密匙
             */
            MACSigner macSigner = new MACSigner(SECRET);
            /**
             * 2. 建立payload 载体
             */
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("comic-app")
                    .issuer("CoolWind")
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .claim("ACCOUNT",id)
                    .build();
            /**
             * 3. 建立签名
             */
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(macSigner);

            /**
             * 4. 生成token
             */
            String token = signedJWT.serialize();
            return token;
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 校验token
     * @param token
     * @return
     */
    public static Integer verifyToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);
            //校验是否有效
            if (!jwt.verify(verifier)) {
                return Constants.TOKEN_INVALID;
            }

            //校验超时
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)) {
                return Constants.TOKEN_EXPIRE;

            }

            //获取载体中的数据
            Object account = jwt.getJWTClaimsSet().getClaim("ACCOUNT");
            //是否有openUid
            if (Objects.isNull(account)){
                return Constants.TOKEN_INVALID;
            }
            return Integer.parseInt(account.toString());
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        return Constants.TOKEN_INVALID;
    }

}

