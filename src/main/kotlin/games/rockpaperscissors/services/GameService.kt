package games.rockpaperscissors.services

import games.rockpaperscissors.entity.Figure
import games.rockpaperscissors.entity.Game
import games.rockpaperscissors.entity.Movement
import games.rockpaperscissors.entity.Player
import games.rockpaperscissors.repository.GameRepository
import games.rockpaperscissors.repository.MovementRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.server.ResponseStatusException

@Service
class GameService {

    @Autowired
    lateinit var gameRepository: GameRepository

    @Autowired
    lateinit var movementService: MovementService

    fun games(): List<Game> = gameRepository.findAll().toList()

    fun newGame(user: String): Game {
        val game = Game(user = user)
        return gameRepository.save(game)
    }

    fun getGame(id: Int): Game {
        val game = gameRepository.findById(id)
        if (!game.isEmpty) {
            return game.get()
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "Game $id not found")
    }

    fun play(id: Int, figure: Figure): Game {
        val game = gameRepository.findById(id)
        if (!game.isEmpty) {
            val gameValue = game.get()
            if (gameValue.winnerPlayer == null) {
                try {
                    val movPlayer = movementService.newMovement(Player.PERSON, id, figure)
                    val movMachine = movementService.newMovement(Player.MACHINE, id, figure.figureRandom())
                    val winnerPlayer = figure.wins(movPlayer, movMachine)
                    if (winnerPlayer != null) {
                        gameValue.winnerPlayer = winnerPlayer
                        gameValue.winner = winnerPlayer == Player.PERSON;
                    }
                    return gameRepository.save(gameValue)
                }catch (e: Error) {
                    throw ResponseStatusException(HttpStatusCode.valueOf(500), "Game $id internal error: ${e.localizedMessage}")
                }
            }
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "Game $id already finished")
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Game $id not found")
        }
    }
}