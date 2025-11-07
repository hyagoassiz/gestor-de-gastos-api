package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String CHAVE_SECRETA = "umaChaveSuperSeguraComMaisDe32Caracteres123!";
    private static final long EXPIRACAO = 1000 * 60 * 60;

    private final SecretKey secretKey;

    public JwtService() {
        this.secretKey = new SecretKeySpec(
                CHAVE_SECRETA.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256");
    }

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("nome", usuario.getNome())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
