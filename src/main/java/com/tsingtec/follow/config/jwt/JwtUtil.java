package com.tsingtec.follow.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tsingtec.follow.entity.mini.MaUser;
import com.tsingtec.follow.exception.BusinessException;
import com.tsingtec.follow.exception.code.BaseExceptionType;
import com.tsingtec.follow.vo.resp.mini.TokenRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("JwtUtil")
public class JwtUtil {
 
    @Autowired
    private JwtProperties jwtProperties;


   public TokenRespVO getToken(MaUser maUser) {
        log.info("身份信息token");
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());

        Long now = System.currentTimeMillis();
        Date exp = new Date(now + jwtProperties.getExpireTime());
        // 头部信息
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(header)// 设置头部信息 Header
                .withClaim("id",maUser.getId())
                .withExpiresAt(exp)
                .sign(algorithm);//签名 Signature
        String reToken = JWT.create()
                .withHeader(header)// 设置头部信息 Header
                .withClaim("id",maUser.getId())
                .withExpiresAt(new Date(now + jwtProperties.getRefreshExpireTime()))//设置 载荷 签名过期的时间
                .sign(algorithm);//签名 Signature
        return new TokenRespVO(token,now + jwtProperties.getExpireTime(),reToken,now+jwtProperties.getRefreshExpireTime());
    }

    public boolean verify(String token){
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public Integer getClaim(String token, String claim) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
        try{
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims().get(claim).asInt();
        }catch (IllegalArgumentException e) {
            log.error(e.toString());
            throw new BusinessException(BaseExceptionType.TOKEN_ERROR,"token认证失败1");
        }catch (JWTVerificationException e) {
            log.error(e.toString());
            throw new BusinessException(BaseExceptionType.TOKEN_ERROR,"token认证失败2");
        }catch (Exception e){
            log.error(e.toString());
            log.error(e.getMessage());
            throw new BusinessException(BaseExceptionType.TOKEN_ERROR,"token认证失败3");
        }
    }
}