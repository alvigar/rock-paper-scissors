package games.rockpaperscissors.repository

import games.rockpaperscissors.model.ERole
import games.rockpaperscissors.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Int>{
    fun findRoleByRoleName(roleName: ERole): Optional<Role>
}