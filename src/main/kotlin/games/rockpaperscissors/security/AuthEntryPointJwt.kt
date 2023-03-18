package games.rockpaperscissors.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthEntryPointJwt : AuthenticationEntryPoint{

    val logger: Logger = LoggerFactory.getLogger("AuthEntryPointJwt")
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        if (authException != null) {
            logger.error("Unathorized error: ${authException.message}")
            response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
        }
    }
}