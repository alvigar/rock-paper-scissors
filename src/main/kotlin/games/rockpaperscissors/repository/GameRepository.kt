package games.rockpaperscissors.repository

import games.rockpaperscissors.entity.Game
import games.rockpaperscissors.entity.Player
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GameRepository : CrudRepository<Game, Int> {

    fun findAllByOrderByWinnerPlayerDesc(): List<Game>
    fun findGamesByUserOrWinnerPlayerIsNotNullOrderByWinnerPlayerDesc(user: String): List<Game>
    fun findGamesByWinnerPlayerIsNotNullOrderByWinnerPlayerDesc(): List<Game>
}