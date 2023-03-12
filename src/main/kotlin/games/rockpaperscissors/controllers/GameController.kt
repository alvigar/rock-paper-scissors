package games.rockpaperscissors.controllers

import games.rockpaperscissors.dto.UserDTO
import games.rockpaperscissors.entity.Figure
import games.rockpaperscissors.entity.Game
import games.rockpaperscissors.services.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController {

    @Autowired
    lateinit var gameService: GameService

    @GetMapping("/games")
    fun getGames(): List<Game> = gameService.games()

    @PostMapping("/games/new")
    fun newGame(@RequestBody userDTO: UserDTO): Game = gameService.newGame(userDTO.user)

    @GetMapping("/games/{id}")
    fun getGame(@PathVariable("id") id: Int): Game = gameService.getGame(id)

    @PutMapping("/games/{id}/play/{figure}")
    fun play(@PathVariable("id") id: Int, @PathVariable("figure") figure: Figure): Game = gameService.play(id, figure)
}