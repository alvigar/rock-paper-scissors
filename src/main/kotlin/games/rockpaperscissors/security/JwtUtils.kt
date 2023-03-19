package games.rockpaperscissors.security

import games.rockpaperscissors.entity.User
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SignatureException
import java.util.*


@Component
class JwtUtils {
    @Value("\${app.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${app.jwtExpirationMs}")
    private val jwtExpirationMs = 0
    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetails
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        val key: Key = Keys.hmacShaKeyFor(keyBytes)
        return Jwts.builder().setSubject(userPrincipal.username).setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs)).signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUserNameFromJwtToken(token: String?): String {
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        val key: Key = Keys.hmacShaKeyFor(keyBytes)
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            val keyBytes = Decoders.BASE64.decode(jwtSecret)
            val key: Key = Keys.hmacShaKeyFor(keyBytes)
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtUtils::class.java)
    }
}