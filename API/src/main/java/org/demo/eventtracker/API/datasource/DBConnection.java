package org.demo.eventtracker.API.datasource;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    @Bean
    public Connection getConnection() {

        Dotenv dotenv = Dotenv.load();

        try {
            String jdbcURl = dotenv.get("JDBC_URL");
            String user = dotenv.get("USER");
            String password = dotenv.get("PASSWORD");
            return DriverManager.getConnection(jdbcURl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
