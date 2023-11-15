package blog.yrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveMonoFlux implements CommandLineRunner {
    
    public static final Logger LOG = LoggerFactory.getLogger(ReactiveMonoFlux.class);

    public static void main(String[] args) {
        SpringApplication.run(ReactiveMonoFlux.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("ReactiveMonoFlux started....");
    }
}