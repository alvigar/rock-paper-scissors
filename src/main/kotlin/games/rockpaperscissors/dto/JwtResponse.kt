package games.rockpaperscissors.dto

data class JwtResponse(
    val token: String,
    val id: Int,
    val username: String,
    val roles: List<String>
)