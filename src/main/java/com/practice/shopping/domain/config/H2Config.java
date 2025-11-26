package com.practice.shopping.domain.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.SQLException;

@Configuration
public class H2Config {

    /**
     * Starts an H2 TCP server, making the in-memory database accessible to external tools.
     * The server will be available at jdbc:h2:tcp://localhost:9092/mem:mydb
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}
