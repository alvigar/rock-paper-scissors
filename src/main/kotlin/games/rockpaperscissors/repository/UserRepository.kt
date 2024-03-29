package games.rockpaperscissors.repository

import games.rockpaperscissors.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Int>{
    fun findUserByNickname(nickName: String): Optional<User>
}