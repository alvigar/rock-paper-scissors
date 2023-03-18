package games.rockpaperscissors.security

import games.rockpaperscissors.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserByNickname(username).orElseThrow{ UsernameNotFoundException("User not found $username") }
        return User.builder().username(user.nickname).password(user.userPassword).authorities(user.authorities).build();
    }
}