package games.rockpaperscissors.controllers

import games.rockpaperscissors.dto.UserDTO
import games.rockpaperscissors.entity.Figure
import games.rockpaperscissors.entity.Game
import games.rockpaperscissors.services.GameService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = ["http://localhost:4200"], maxAge = 3600, allowCredentials="true")
class GameController(private val gameService: GameService) {

    @GetMapping("")
    fun getGames(): ResponseEntity<List<Game>> = gameService.games()

    @PostMapping("/new")
    fun newGame(@RequestBody userDTO: UserDTO): ResponseEntity<Game> = gameService.newGame(userDTO.user)

    @GetMapping("/{id}")
    fun getGame(@PathVariable("id") id: Int): ResponseEntity<Game> = gameService.getGame(id)

    @PutMapping("/{id}/play/{figure}")
    fun play(@PathVariable("id") id: Int, @PathVariable("figure") figure: Figure): ResponseEntity<out Any> = gameService.play(id, figure)
}