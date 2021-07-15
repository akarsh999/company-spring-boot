package com.company.demo.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
	
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

	private static String currentUserName=null;
	
	public static String getCurrentUserName() {
		return currentUserName;
	}
	
	public String extractUsername(String token, String secret) {
		String name=extractClaim(token, secret, Claims::getSubject);
    	currentUserName=name;
    	log.debug("extracted username: "+name);
        return name;
    }

    public Date extractExpiration(String token, String secret) {
        return extractClaim(token, secret, Claims::getExpiration);
    }

    public <T> T extractClaim(String token,  String secret, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token, secret);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token, String secret) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encode(secret.getBytes())).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token,String secret) {
        return extractExpiration(token, secret).before(new Date());
    }

    public String generateToken(String username, String secret) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, secret);
    }

    private String createToken(Map<String, Object> claims, String subject,String secret) {
    	log.debug("subject is : "+subject);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(secret.getBytes())).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails, String secret) {
        final String username = extractUsername(token,secret);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token,secret));
    }
}
