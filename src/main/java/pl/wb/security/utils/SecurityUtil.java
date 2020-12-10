package pl.wb.security.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import pl.wb.security.jpa.User;
import pl.wb.security.service.QueryService;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.sasl.AuthenticationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SecurityUtil {

    @Inject
    private QueryService queryService;

    public static final String HASHED_PASSWORD_KEY = "hashedPassword";
    public static final String SALT_KEY = "salt";
    public static final String BEARER = "Bearer";

    private SecretKey securityKey;

    @PostConstruct
    private void init() {
        securityKey = generateKey();
    }

    public Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    public boolean passwordsMatch(String dbStoredHashedPassword, String saltText, String passwordFromTheUser) {
        ByteSource salt = ByteSource.Util.bytes(Hex.decode(saltText));
        String hashedPassword = hashAndSaltPassword(passwordFromTheUser, salt);

        return hashedPassword.equals(dbStoredHashedPassword);
    }

    public Map<String, String> hashPassword(String clearTextPassword) {
        ByteSource salt = getSalt();

        Map<String, String> credentialMap = new HashMap<>();
        credentialMap.put(HASHED_PASSWORD_KEY, hashAndSaltPassword(clearTextPassword, salt));
        credentialMap.put(SALT_KEY, salt.toHex());
        return credentialMap;
    }

    public boolean authenticateUser(String email, String password) throws AuthenticationException {
        User user = queryService.findUserByEmail(email);
        if (user == null) {
            throw new AuthenticationException("User not found!");
        }
        return passwordsMatch(user.getPassword(), user.getSalt(), password);
    }


    public SecretKey getSecurityKey() {
        return securityKey;
    }

    private String hashAndSaltPassword(String passwordFromTheUser, ByteSource salt) {

        return new Sha512Hash(passwordFromTheUser, salt, 2000000).toHex();
    }

    private ByteSource getSalt() {
        return new SecureRandomNumberGenerator().nextBytes();
    }

    private SecretKey generateKey() {
        return MacProvider.generateKey(SignatureAlgorithm.HS512);
    }
}