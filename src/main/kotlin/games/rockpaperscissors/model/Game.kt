package games.rockpaperscissors.model

import jakarta.persistence.*


@Entity
@Table(name = "game")
data class Game (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(name = "user_name", nullable = false)
    val user: String,
    @Column
    var winner: Boolean = false,
    @Column
    var winnerPlayer: Player? = null,
    @OneToMany(mappedBy = "game_id")
    val movements: List<Movement> = emptyList()
)