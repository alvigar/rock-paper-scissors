package games.rockpaperscissors.services

import games.rockpaperscissors.dto.JwtResponse
import games.rockpaperscissors.dto.UserDTO
import games.rockpaperscissors.model.ERole
import games.rockpaperscissors.model.User
import games.rockpaperscissors.repository.RoleRepository
import games.rockpaperscissors.repository.UserRepository
import games.rockpaperscissors.security.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(private val userRepository: UserRepository,
                  private val roleRepository: RoleRepository,
                  private val authenticationManager: AuthenticationManager,
                  private val encoder: PasswordEncoder,
                  private val jwtUtils: JwtUtils) {

    fun signUp(userDTO: UserDTO): ResponseEntity<out Any> {
        if (userDTO.password != null) {
            try {
                val existsUser = userRepository.findUserByNickname(userDTO.user)
                if (!existsUser.isEmpty) {
                    return ResponseEntity.badRequest().body("Error: ${userDTO.user} already exists")
                }
                val userRole = roleRepository.findRoleByRoleName(ERole.USER)
                if (userRole.isEmpty) {
                    return ResponseEntity.internalServerError().body("Error creating user: Role USER not found")
                }
                val newUser = User(
                    nickname = userDTO.user,
                    userPassword = encoder.encode(userDTO.password),
                    enabled = true)
                newUser.roles.add(userRole.get())
                userRepository.save(newUser)
                return ResponseEntity.ok().body(userRepository.save(newUser))
            } catch (error: Error) {
                return ResponseEntity.internalServerError().body("Error creating user: ${error.message}")
            }
        } else {
            return ResponseEntity.badRequest().body("Password is required")
        }
    }

    fun signIn(userDTO: UserDTO): ResponseEntity<JwtResponse> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userDTO.user, userDTO.password))

        SecurityContextHolder.getContext().authentication = authentication
        val myJwt = jwtUtils.generateJwtToken(authentication)

        val user = authentication.principal as UserDetails
        val roles = user.authorities.map { item -> item.authority }
        return ResponseEntity.ok( JwtResponse(token = myJwt, username = user.username, roles = roles ) )
    }

}