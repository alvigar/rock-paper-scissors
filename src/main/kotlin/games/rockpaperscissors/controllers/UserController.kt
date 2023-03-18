package games.rockpaperscissors.controllers

import games.rockpaperscissors.dto.JwtResponse
import games.rockpaperscissors.dto.UserDTO
import games.rockpaperscissors.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/auth")
class UserController(private val authenticationManager: AuthenticationManager) {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/signup")
    fun signUp(@RequestBody userDTO: UserDTO): String = userService.signUp(userDTO)

    @PostMapping("/signin")
    fun signIn(@RequestBody userDTO: UserDTO): ResponseEntity<JwtResponse> = userService.signIn(userDTO)
}