package com.every.every_server.service.jwt;

import com.every.every_server.constant.DateConstant;
import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class JwtServiceImpl implements JwtService{
    @Autowired
    private MemberRepo memberRepo;

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     *
     * @param idx 회원 고유번호
     * @return x-access-token
     */
    @Override
    public String createToken(Integer idx) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + DateConstant.MILLISECONDS_IN_A_HOUR * 12);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<String, Object>();

        headerMap.put("typ","JWT");
        headerMap.put("alg","HS256");

        Map<String, Object> map= new HashMap<String, Object>();

        map.put("idx", idx);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    @Override
    public Integer validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(token).getBody(); // 정상 수행된다면 해당 토큰은 정상토큰

            Optional<Member> member = memberRepo.findById((Integer) claims.get("idx"));

            // 회원 없음
            if (!member.isPresent()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "회원 없음.");
            }

            return member.get().getIdx();
        } catch (ExpiredJwtException e) {
            throw new HttpClientErrorException(HttpStatus.GONE, "토큰 만료.");
        } catch (SignatureException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.");
        } catch (MalformedJwtException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.");
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "토큰 없음.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
