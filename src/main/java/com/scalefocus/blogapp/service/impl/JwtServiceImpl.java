package com.scalefocus.blogapp.service.impl;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jwt.JWTParser;
import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${auth.secret-key}")
    private String secretKey;

    @Value("${auth.issuer}")
    private String issuer;


    @Override
    public String generateToken(UserEntity user) {

        JwtClaimsSet claimsSet = JwtClaimsSet
                .builder()
                .issuer(issuer)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(1800))
                .subject(user.getUsername())
                .claim("id", user.getId())
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

    public String extractUsername(String token) throws ParseException {

        return JWTParser
                .parse(token)
                .getJWTClaimsSet()
                .getSubject();
    }

    public String extractUserId(String token) throws ParseException {

        return JWTParser
                .parse(token)
                .getJWTClaimsSet()
                .getClaim("id")
                .toString();
    }

    public boolean isTokenExpired(String token) throws ParseException {
        return JWTParser
                .parse(token)
                .getJWTClaimsSet()
                .getExpirationTime()
                .before(new Date());
    }

    public boolean isTokenValid(String token) throws ParseException {

        return Boolean.FALSE.equals(isTokenExpired(token));

    }

}
