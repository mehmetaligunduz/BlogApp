package com.scalefocus.blogapp.service;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.scalefocus.blogapp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
    
}
