package games.rockpaperscissors.controllers

import games.rockpaperscissors.dto.UserDTO
import games.rockpaperscissors.entity.Figure
import games.rockpaperscissors.entity.Game
import games.rockpaperscissors.services.GameService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/game")
class GameController(private val gameService: GameService) {

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    fun getGames(): List<Game> = gameService.games()

    @PostMapping("/new")
    @PreAuthorize("hasRole('USER')")
    fun newGame(@RequestBody userDTO: UserDTO): Game = gameService.newGame(userDTO.user)

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getGame(@PathVariable("id") id: Int): Game = gameService.getGame(id)

    @PutMapping("/{id}/play/{figure}")
    @PreAuthorize("hasRole('USER')")
    fun play(@PathVariable("id") id: Int, @PathVariable("figure") figure: Figure): Game = gameService.play(id, figure)
}