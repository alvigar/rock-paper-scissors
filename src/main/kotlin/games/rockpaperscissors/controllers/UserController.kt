package games.rockpaperscissors.controllers

import games.rockpaperscissors.dto.PasswordDTO
import games.rockpaperscissors.dto.RoleDTO
import games.rockpaperscissors.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = ["http://localhost:8000"], maxAge = 3600, allowCredentials="true")
class UserController(private val userService: UserService) {

    @GetMapping()
    fun listUsers(): ResponseEntity<out Any> = userService.findAll()

    @PutMapping("/{user}/enable")
    fun enableUser(@PathVariable user: String): ResponseEntity<out Any> = userService.enable(user)

    @PutMapping("/{user}/disable")
    fun disableUser(@PathVariable user: String): ResponseEntity<out Any> = userService.disable(user)

    @PostMapping("/{user}/roles")
    fun modifyRoles(@PathVariable user: String, @RequestBody roles: RoleDTO): ResponseEntity<out Any> = userService.modifyRoles(user, roles.roles)

    @DeleteMapping("/{user}")
    fun delete(@PathVariable user: String): ResponseEntity<out Any> = userService.delete(user)

    @PostMapping("/{user}/password")
    fun modifyPassword(@PathVariable user: String, @RequestBody passwordDTO: PasswordDTO): ResponseEntity<out Any> = userService.modifyPassword(user, passwordDTO.password)
}