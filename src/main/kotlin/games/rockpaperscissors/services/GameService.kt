package games.rockpaperscissors.services

import games.rockpaperscissors.entity.Figure
import games.rockpaperscissors.entity.Game
import games.rockpaperscissors.entity.Movement
import games.rockpaperscissors.entity.Player
import games.rockpaperscissors.repository.GameRepository
import games.rockpaperscissors.repository.MovementRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.server.ResponseStatusException

@Service
class GameService {

    @Autowired
    lateinit var gameRepository: GameRepository

    @Autowired
    lateinit var movementService: MovementService

    fun games(): ResponseEntity<List<Game>> = ResponseEntity.ok().body(gameRepository.findAll().toList())

    fun newGame(user: String): ResponseEntity<Game> {
        val game = Game(user = user)
        return ResponseEntity.ok().body(gameRepository.save(game))
    }

    fun getGame(id: Int): ResponseEntity<Game> {
        val game = gameRepository.findById(id)
        return ResponseEntity.of(game)
    }

    fun play(id: Int, figure: Figure): ResponseEntity<out Any> {
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
                    return ResponseEntity.ok().body(gameRepository.save(gameValue))
                }catch (e: Error) {
                    return ResponseEntity.internalServerError().body("Game $id internal error: ${e.localizedMessage}")
                }
            }
            return ResponseEntity.unprocessableEntity().body("Game $id already finished")
        } else {
            return ResponseEntity.of(game)
        }
    }
}