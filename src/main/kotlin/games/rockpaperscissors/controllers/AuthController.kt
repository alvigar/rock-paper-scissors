package games.rockpaperscissors.controllers

import games.rockpaperscissors.dto.JwtResponse
import games.rockpaperscissors.dto.UserDTO
import games.rockpaperscissors.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["http://localhost:8000"], maxAge = 3600, allowCredentials="true")
class AuthController(private val authService: AuthService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody userDTO: UserDTO): ResponseEntity<out Any> = authService.signUp(userDTO)

    @PostMapping("/signin")
    fun signIn(@RequestBody userDTO: UserDTO): ResponseEntity<JwtResponse> = authService.signIn(userDTO)
}