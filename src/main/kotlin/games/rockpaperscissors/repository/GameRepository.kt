package games.rockpaperscissors.repository

import games.rockpaperscissors.entity.Game
import org.springframework.data.repository.CrudRepository

interface GameRepository : CrudRepository<Game, Int>