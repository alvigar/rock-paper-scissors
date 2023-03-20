package games.rockpaperscissors.repository

import games.rockpaperscissors.model.Game
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : CrudRepository<Game, Int> {

    fun findAllByOrderByWinnerPlayerDesc(): List<Game>
    fun findGamesByUserOrWinnerPlayerIsNotNullOrderByWinnerPlayerDesc(user: String): List<Game>
    fun findGamesByWinnerPlayerIsNotNullOrderByWinnerPlayerDesc(): List<Game>
}