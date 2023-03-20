package games.rockpaperscissors.model

import jakarta.persistence.*


@Entity
@Table(name = "movement")
data class Movement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(nullable = false)
    val player: Player,
    @Column(nullable = false)
    val figure: Figure,
    @Column
    val game_id: Int? = null
)