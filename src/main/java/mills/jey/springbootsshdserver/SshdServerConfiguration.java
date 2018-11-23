package com.example.springbootsshdserver;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class SshdServerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SshdServerConfiguration.class);

    @Bean
    public SshServer sshServer() {
        LOGGER.info("Initializing SSHD server...");
        final SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setHost("localhost");
        sshd.setPort(22);
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
        LOGGER.info("SSHD server initialized...");
        return sshd;
    }

    @PostConstruct
    public void start() throws IOException, InterruptedException {
        LOGGER.info("Starting SSHD server...");
        sshServer().start();
        LOGGER.info("SSHD server started on port {}", sshServer().getPort());
    }

    @PreDestroy
    public void stop() throws IOException {
        LOGGER.info("Stopping SSHD server...");
        sshServer().stop();
        LOGGER.info("SSHD server stopped");
    }
}
