package games.rockpaperscissors.services

import games.rockpaperscissors.dto.JwtResponse
import games.rockpaperscissors.dto.UserDTO
import games.rockpaperscissors.entity.ERole
import games.rockpaperscissors.entity.User
import games.rockpaperscissors.repository.UserRepository
import games.rockpaperscissors.security.JwtUtils
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(private val userRepository: UserRepository,
                  private val authenticationManager: AuthenticationManager,
                  private val jwtUtils: JwtUtils) {

    fun signUp(userDTO: UserDTO): String {
        if (userDTO.password != null) {
            try {
                val existsUser = userRepository.findUserByNickname(userDTO.user)
                if (!existsUser.isEmpty) {
                    throw ResponseStatusException(HttpStatusCode.valueOf(400), "User ${userDTO.user} already exists")
                }
                val newUser = User(
                    nickname = userDTO.user,
                    userPassword = userDTO.password,
                    enabled = true,
                    roles = buildSet { ERole.USER })
                userRepository.save(newUser)
                return "${userDTO.user} created successfully"
            } catch (error: Error) {
                throw ResponseStatusException(HttpStatusCode.valueOf(500), "Error creating user: ${error.message}")
            }
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(400), "Password is required")
        }
    }

    fun signIn(userDTO: UserDTO): ResponseEntity<JwtResponse> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userDTO.user, userDTO.password))

        SecurityContextHolder.getContext().authentication = authentication
        val myJwt = jwtUtils.generateJwtToken(authentication)

        val user = authentication.principal as User
        val roles = user.authorities.map { item -> item.authority }
        return ResponseEntity.ok(user.id?.let { JwtResponse(token = myJwt, id = it, username = user.nickname, roles = roles ) })
    }
}