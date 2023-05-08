package com.example.kunuz.util;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exps.AppBadRequestException;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.exps.MethodNotAllowedException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24 * 10; // 10-day
    private static final int emailTokenLiveTime = 1000 * 120; // 2-minutes
    private static final String secretKey = "dasda143mazgi";

    public static String encode(Integer profileId, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Kunuz test portali");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getBody();
        Integer id = (Integer) claims.get("id");
        String role = (String) claims.get("role");
        ProfileRole profileRole = ProfileRole.valueOf(role);
        return new JwtDTO(id, profileRole);
    }


    public static String decodeEmailVerification(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            return (String) claims.get("email");
        } catch (JwtException e) {
            e.printStackTrace();
        }
        throw new MethodNotAllowedException("Jwt exception");
    }

    public static String encode(String text) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);
        jwtBuilder.claim("email", text);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (emailTokenLiveTime)));
        jwtBuilder.setIssuer("Kunuz test portali");
        return jwtBuilder.compact();
    }

    public static JwtDTO getJwtDTO(String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        return JwtUtil.decode(jwt);
    }

    public static boolean checkToOwner(String authorization, Integer userId) {
        if (getJwtDTO(authorization).getId() != userId) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return true;
    }

    public static void checkToAdminOrOwner(String authorization) {
        JwtDTO jwtDTO = getJwtDTO(authorization);
        if (!(jwtDTO.getRole().equals(ProfileRole.ADMIN) || checkToOwner(authorization, jwtDTO.getId()))) {
            throw new MethodNotAllowedException("Method not allowed");
        }
    }

    public static JwtDTO getJwtDTO(String authorization, ProfileRole... roleList) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        boolean roleFound = false;
        for (ProfileRole role : roleList) {
            if (jwtDTO.getRole().equals(role)) {
                roleFound = true;
                break;
            }
        }
        if (!roleFound) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return jwtDTO;
    }

    public static int checkForRequiredRoleAndGetPrtId(HttpServletRequest request, ProfileRole... roleList) {
        ProfileRole jwtRole = (ProfileRole) request.getAttribute("role");
        if (jwtRole==null){
            throw new AppBadRequestException("Jwt exception");
        }
        boolean roleFound = false;
        for (ProfileRole role : roleList) {
            if (jwtRole.equals(role)) {
                roleFound = true;
                break;
            }
        }
        if (!roleFound) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        int prtId = (Integer) request.getAttribute("id");
        return prtId;
    }

    public static JwtDTO checkToOwnerRequest(HttpServletRequest request, ProfileDTO dto) {
        ProfileRole jwtRole = (ProfileRole) request.getAttribute("role");
        Integer id = (Integer) request.getAttribute("id");
        if (jwtRole==null){
            throw new AppBadRequestException("Jwt exception");
        }

        if (id != dto.getId()) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return new JwtDTO(id, jwtRole);
    }

    public static JwtDTO getJwtDTORequest(HttpServletRequest request) {
        ProfileRole jwtRole = (ProfileRole) request.getAttribute("role");
        Integer id = (Integer) request.getAttribute("id");
        if (jwtRole==null){
            throw new AppBadRequestException("Jwt exception");
        }
        return new JwtDTO(id, jwtRole);
    }
}
