package blog.yrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class ReactiveMonoFlux implements CommandLineRunner {
    
    public static final Logger LOG = LoggerFactory.getLogger(ReactiveMonoFlux.class);

    /**
     * Returning a single element data
     * Using optional log() for printing the events
     * **/
    public Mono<String> nameMono() {
        return Mono.just("James").log();
    }


    /**
     * Returning a dataset / stream of data using Flux. In reality this will be coming from a DB or an external service
     * Using optional log() for printing the events
     * **/
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .log();
    }

    /**
     * Using the map() operator to convert lower case to upper
     * **/
    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .log();
    }


    /**
     * Checking the immutability - Reactive streams are immutable (an object can't be changes once created)
     * Hence namesFlux.map() below won't effective.
     * **/
    public Flux<String> namesFluxImmutability() {
        var namesFlux =  Flux.fromIterable(List.of("Alex", "Ben", "Chloe"));
        namesFlux.map(String::toUpperCase); // not effective
        return namesFlux;
    }

    /**
     * Using the filter function
     * Return names that are only greater than stringLength in uppercase
     * **/
    public Flux<String> namesFluxFilter(int stringLength) {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s->s.length() > stringLength) // using lambda functions
                .log();
    }

    /**
     * Example chaining multiple operators (filters and maps)
     * **/
    public Flux<String> namesFluxMultipleChaining(int stringLength) {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s->s.length() > stringLength) // using lambda functions
                .map(s-> s.length() + "-" +s) // return string length and the name. Ex: "5 - CHLOE"
                .log();
    }

    /**
     * Using the flatmap() operator
     * Flatmap transforms one source element to a Flux of 1 to N elements. Ex: ALEX -> "A", "L", "E", "X"
     * **/
    public Flux<String> namesFluxFlatmap(int stringLength) {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s->s.length() > stringLength) // using lambda functions
//                .flatMap(s->splitString(s)) // Another way to call splitString()
                .flatMap(this::splitString)
                .log();
    }

    /**
     * Mimicking the Async behaviour of flatmaps
     * **/
    public Flux<String> namesFluxFlatmapAsync(int stringLength) {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s->s.length() > stringLength) // using lambda functions
//                .flatMap(s->splitString(s)) // Another way to call splitString()
                .flatMap(this::splitStringWithDelay)
                .log();
    }


    /**
     * Using ConcatMap in Async
     * Unlike using flatMaps with Async, this will preserve the ordering of the elements
     * Trade-off (flatMaps vs concatMap): processing time will take longer
     * **/
    public Flux<String> namesFluxFlatmapAsyncConcat(int stringLength) {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s->s.length() > stringLength) // using lambda functions
//                .flatMap(s->splitString(s)) // Another way to call splitString()
                .concatMap(this::splitStringWithDelay)
                .log();
    }

    public Mono<List<String>> nameMonoFlatMap(int stringLength) {
        return Mono.just("James")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono)
                .log();
    }

    /**
     * Example using flatMapMany
     * Unlike flatmap, the flatMapMany can only be used with Flux as return type
     * **/
    public Flux<String> nameMonoFlatMapMany(int stringLength) {
        return Mono.just("James")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitString)
                .log();
    }


    /**
     * Supportive function for splitting a string with a delay and return Flux of String
     * **/
    public Flux<String> splitStringWithDelay(String name) {
        var delay = new Random().nextInt(1000);
        var charArray = name.split(""); // Splitting name string and put each char into an array

        // Adding a random delay under one second
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray); // ALEX -> A, L, E, X
        return Mono.just(charList);
    }

    /**
     * Supportive function which converts a string to flatmap and return Flux of String. Ex: ALEX -> FLUX(A,L,E,X)
     * **/
    public Flux<String> splitString(String name) {
        var charArray = name.split(""); // Splitting name string and put each char into an array
        return Flux.fromArray(charArray);
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