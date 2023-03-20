package games.rockpaperscissors.security

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DatabaseConfiguration {
    @Bean
    fun getDataSource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url("jdbc:postgresql://db:5432/postgres")
            .username("postgres")
            .password("postgres")
            .build()
    }
}