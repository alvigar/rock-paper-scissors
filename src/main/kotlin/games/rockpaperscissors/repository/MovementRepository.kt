package games.rockpaperscissors.repository

import games.rockpaperscissors.entity.Movement
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovementRepository : CrudRepository<Movement, Int>