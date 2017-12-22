package learn.blockchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("${peerNodes}")
    private String[] peerNodes;

    @PostConstruct
    public void init() {
        logger.info("Registered peer nodes: {}", peerNodes);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
