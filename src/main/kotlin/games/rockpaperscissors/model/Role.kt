package games.rockpaperscissors.model

import jakarta.persistence.*

@Entity
@Table(name = "role_detail")
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Enumerated(EnumType.STRING)
    val roleName: ERole
)