package blog.yrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class ReactiveMonoFlux implements CommandLineRunner {
    
    public static final Logger LOG = LoggerFactory.getLogger(ReactiveMonoFlux.class);


    /**
     * Returning a dataset / stream of data using Flux. In reality this will be coming from a DB or an external service
     * Using optional log() for printing the events
     * **/
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .log();
    }

    /**
     * Returning a single element data
     * Using optional log() for printing the events
     * **/
    public Mono<String> nameMono() {
        return Mono.just("James").log();
    }


    public static void main(String[] args) {
        SpringApplication.run(ReactiveMonoFlux.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("ReactiveMonoFlux started....");

        /**
         * Accessing the flux values
         * To access we need to subscribe to it first using the function - subscribe().
         * All the values in Flux will be return in a stream one by one
         * Using lambdas to loop through Flux and Mono data to print data
         * **/
        ReactiveMonoFlux reactiveMonoFlux = new ReactiveMonoFlux();
        reactiveMonoFlux.namesFlux().subscribe(name -> {
            System.out.println("(Flux) Name is : " + name);
        });

        reactiveMonoFlux.nameMono().subscribe(name -> {
            System.out.println("(Mono) Name is : " + name);
        });
    }
}