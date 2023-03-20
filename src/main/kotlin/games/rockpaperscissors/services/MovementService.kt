package games.rockpaperscissors.services

import games.rockpaperscissors.model.Figure
import games.rockpaperscissors.model.Movement
import games.rockpaperscissors.model.Player
import games.rockpaperscissors.repository.MovementRepository
import org.springframework.stereotype.Service

@Service
class MovementService(private val movementRepository: MovementRepository) {

    fun newMovement(player: Player, id: Int, figure: Figure): Movement {
        val movement = Movement(player = player, figure = figure, game_id = id)
        return movementRepository.save(movement)
    }
}