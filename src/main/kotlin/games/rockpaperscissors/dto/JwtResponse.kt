package games.rockpaperscissors.dto

data class JwtResponse(
    val token: String,
    val username: String,
    val roles: List<String>
)