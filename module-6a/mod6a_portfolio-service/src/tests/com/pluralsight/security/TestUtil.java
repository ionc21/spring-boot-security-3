package com.pluralsight.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.util.IOUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class TestUtil {

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair kp = gen.generateKeyPair();
        System.out.println(kp.getPrivate());
        System.out.println ("-----BEGIN PRIVATE KEY-----");
        System.out.println (Base64.getMimeEncoder().encodeToString( kp.getPrivate().getEncoded()));
        System.out.println ("-----END PRIVATE KEY-----");
        System.out.println ("-----BEGIN PUBLIC KEY-----");
        System.out.println (Base64.getMimeEncoder().encodeToString( kp.getPublic().getEncoded()));
        System.out.println ("-----END PUBLIC KEY-----");
        System.out.println (kp.getPrivate().getFormat());
        return kp;
    }

    public static KeyPair loadKeyPairFromPemFiles() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyContent = IOUtils.readInputStreamToString(TestUtil.class.getResourceAsStream("/test-private.pem"));
        String publicKeyContent = IOUtils.readInputStreamToString(TestUtil.class.getResourceAsStream("/test-public.pem"));
        privateKeyContent = privateKeyContent.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(privateKeyContent)));
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(Base64.getMimeDecoder().decode(publicKeyContent)));
        return new KeyPair(publicKey,privateKey);
    }
    public static String getMockJwt(Date expiration, String audience) throws JOSEException, NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .jwtID("12345")
                .subject("davo")
                .audience(audience)
                .expirationTime(expiration).build();
        SignedJWT jwt = new SignedJWT(header,payload);
        jwt.sign(new RSASSASigner(loadKeyPairFromPemFiles().getPrivate()));
        return jwt.serialize();
    }

}
