package games.rockpaperscissors

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["games.rockpaperscissors"])
@EnableJpaRepositories(basePackages = ["games.rockpaperscissors.repository"])
class RockPaperScissorsApplication

fun main(args: Array<String>) {
	runApplication<RockPaperScissorsApplication>(*args)
}
