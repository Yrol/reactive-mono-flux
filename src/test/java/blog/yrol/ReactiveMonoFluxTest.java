package blog.yrol;


import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Test case creation for Reative Flux
 * Reative Flux cannot be tested with Junit Assertions, hence a special library is being used - reactor-test
 * **/
public class ReactiveMonoFluxTest {

    ReactiveMonoFlux reactiveMonoFlux = new ReactiveMonoFlux();


    /**
     * Testing the namesFlux
     * **/
    @Test
    void testNames_whenCallingNamesFlux_returnNames(){

        // Arrange
        var namesFlux = reactiveMonoFlux.namesFlux();

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("Alex", "Ben", "Chloe")
                .verifyComplete();
    }

    @Test
    void testNameCount_whenCallingNamesFlux_returnElementCount(){

        // Arrange
        var namesFlux = reactiveMonoFlux.namesFlux();

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    /**
     * Testing the remaining number of elements after verifying the first element
     * **/
    @Test
    void testNameCountMixAndMatch_whenCallingNamesFlux_returnElementAndRemainingCount(){
        // Arrange
        var namesFlux = reactiveMonoFlux.namesFlux();

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("Alex")
                .expectNextCount(2)
                .verifyComplete();
    }
}
