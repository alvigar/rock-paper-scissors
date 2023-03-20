package games.rockpaperscissors.dto

import games.rockpaperscissors.model.ERole
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

data class RoleDTO (
    @Enumerated(EnumType.STRING)
    val roles: List<ERole>
)