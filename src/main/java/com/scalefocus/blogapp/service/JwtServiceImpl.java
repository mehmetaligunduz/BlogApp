package com.scalefocus.blogapp.service;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.scalefocus.blogapp.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${auth.secret-key}")
    private String secretKey;

    @Value("${auth.issuer}")
    private String issuer;

    @Override
    public String generateToken(UserEntity user) {

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(1800))
                .subject(user.getUsername())
                .build();

        final NimbusJwtEncoder nimbusJwtEncoder =
                new NimbusJwtEncoder(
                        new ImmutableSecret<>(secretKey.getBytes()));

        final JwtEncoderParameters parameters =
                JwtEncoderParameters
                        .from(JwsHeader.with(MacAlgorithm.HS256)
                                        .build(),
                                claimsSet);

        return nimbusJwtEncoder
                .encode(parameters)
                .getTokenValue();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
