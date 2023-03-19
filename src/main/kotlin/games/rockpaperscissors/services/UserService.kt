package games.rockpaperscissors.services

import games.rockpaperscissors.entity.ERole
import games.rockpaperscissors.entity.User
import games.rockpaperscissors.repository.RoleRepository
import games.rockpaperscissors.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val encoder: PasswordEncoder
) {

    fun findAll(): ResponseEntity<List<User>> = ResponseEntity.ok().body(userRepository.findAll())

    fun enable(user: String): ResponseEntity<User> {
        val userEntity = userRepository.findUserByNickname(user)
        if (userEntity.isEmpty) {
            return ResponseEntity.of(userEntity)
        }
        val newUser = userEntity.get()
        newUser.enabled = true
        return ResponseEntity.ok().body(userRepository.save(newUser))
    }

    fun disable(user: String): ResponseEntity<User> {
        val userEntity = userRepository.findUserByNickname(user)
        if (userEntity.isEmpty) {
            return ResponseEntity.of(userEntity)
        }
        val newUser = userEntity.get()
        newUser.enabled = false
        return ResponseEntity.ok().body(userRepository.save(newUser))
    }

    fun modifyRoles(user: String, roles: List<ERole>): ResponseEntity<User> {
        val userEntity = userRepository.findUserByNickname(user)
        if (userEntity.isEmpty) {
            return ResponseEntity.of(userEntity)
        }
        val newUser = userEntity.get()
        val newRoles = roles.map { roleName -> roleRepository.findRoleByRoleName(roleName) }
        newUser.roles.removeIf { role -> !newRoles.contains(Optional.of(role)) }
        newRoles.map { newRole ->
            if (!newUser.roles.contains(newRole.get())) {
                newUser.roles.add(newRole.get())
            }
        }
        return ResponseEntity.ok().body(userRepository.save(newUser))
    }

    fun delete(user: String): ResponseEntity<User> {
        val userEntity = userRepository.findUserByNickname(user)
        if (userEntity.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        val newUser = userEntity.get()
        userRepository.delete(newUser)
        return ResponseEntity.ok().body(newUser)
    }

    fun modifyPassword(user: String, password: String): ResponseEntity<out Any> {
        val userEntity = userRepository.findUserByNickname(user)
        if (userEntity.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        val newUser = userEntity.get()
        newUser.userPassword = encoder.encode(password)
        return ResponseEntity.ok().body(userRepository.save(newUser))
    }
}