package games.rockpaperscissors.repository

import games.rockpaperscissors.entity.Movement
import org.springframework.data.repository.CrudRepository

interface MovementRepository : CrudRepository<Movement, Int>