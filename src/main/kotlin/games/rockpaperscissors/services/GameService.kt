package games.rockpaperscissors.services

import games.rockpaperscissors.entity.*
import games.rockpaperscissors.repository.GameRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val movementService: MovementService) {

    fun games(): ResponseEntity<List<Game>> {
        val loggedUser = SecurityContextHolder.getContext().authentication
        if (loggedUser.principal != null && loggedUser.principal != "anonymousUser") {
            val user = loggedUser.principal as UserDetails
            return if (user.authorities.contains(SimpleGrantedAuthority("ROLE_${ERole.ADMIN.name}"))) {
                ResponseEntity.ok().body(gameRepository.findAllByOrderByWinnerPlayerDesc().toList())
            } else {
                ResponseEntity.ok().body(gameRepository.findGamesByUserOrWinnerPlayerIsNotNullOrderByWinnerPlayerDesc(user.username).toList())
            }
        }
        return ResponseEntity.ok().body(gameRepository.findGamesByWinnerPlayerIsNotNullOrderByWinnerPlayerDesc().toList())
    }

    fun newGame(user: String): ResponseEntity<out Any> {
        val game = Game(user = user)
        val loggedUser = SecurityContextHolder.getContext().authentication.principal as UserDetails
        return if (loggedUser.authorities.contains(SimpleGrantedAuthority("ROLE_${ERole.ADMIN.name}"))) {
            ResponseEntity.ok().body(gameRepository.save(game))
        } else {
            if (loggedUser.username == user) {
                ResponseEntity.ok().body(gameRepository.save(game))
            } else {
                ResponseEntity.status(403).body("User ${loggedUser.username} is not authorized to create games for another users")
            }
        }
    }

    fun play(id: Int, figure: Figure): ResponseEntity<out Any> {
        val game = gameRepository.findById(id)
        if (!game.isEmpty) {
            val loggedUser = SecurityContextHolder.getContext().authentication.principal as UserDetails
            if (!loggedUser.authorities.contains(SimpleGrantedAuthority("ROLE_${ERole.ADMIN.name}")) && game.get().user != loggedUser.username) {
                return ResponseEntity.status(403).body("User ${loggedUser.username} is not authorized to play this game")
            }
            val gameValue = game.get()
            if (gameValue.winnerPlayer == null) {
                return try {
                    val movPlayer = movementService.newMovement(Player.PERSON, id, figure)
                    val movMachine = movementService.newMovement(Player.MACHINE, id, figure.figureRandom())
                    val winnerPlayer = figure.wins(movPlayer, movMachine)
                    if (winnerPlayer != null) {
                        gameValue.winnerPlayer = winnerPlayer
                        gameValue.winner = winnerPlayer == Player.PERSON;
                    }
                    ResponseEntity.ok().body(gameRepository.save(gameValue))
                }catch (e: Error) {
                    ResponseEntity.internalServerError().body("Game $id internal error: ${e.localizedMessage}")
                }
            }
            return ResponseEntity.unprocessableEntity().body("Game $id already finished")
        } else {
            return ResponseEntity.of(game)
        }
    }
}