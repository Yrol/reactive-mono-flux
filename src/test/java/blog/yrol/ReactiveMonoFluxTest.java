package blog.yrol;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

/**
 * Test case creation for Reative Flux
 * Reative Flux cannot be tested with Junit Assertions, hence a special library is being used - reactor-test
 * **/
public class ReactiveMonoFluxTest {

    ReactiveMonoFlux reactiveMonoFlux = new ReactiveMonoFlux();

    @Test
    void testMono_whenCallingNameMono_returnName() {
        // Arrange
        var namesFlux = reactiveMonoFlux.nameMono();

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("James")
                .verifyComplete();
    }
    
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

    @Test
    void testNamesUppercase_whenCallingNamesFluxMap_returnAllNamesInUpperCase() {
        // Arrange
        var namesFlux = reactiveMonoFlux.namesFluxMap();

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    /**
     * This will prove the returned stream assigned to namesFlux variable in the namesFluxImmutability() cannot be changed once created
     * The only way to change the values is by chaining the functions. Ex: map() & etc.
     * **/
    @Test
    void testNamesImmutability_whenCallingNamesFluxImmutability_returnNamesUnchanged() {
        // Arrange
        var namesFlux = reactiveMonoFlux.namesFluxImmutability();

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("Alex", "Ben", "Chloe")
                .verifyComplete();
    }

    /**
     * Testing the Filter function - return the names in uppercase which are only greater than the stringLength.
     * **/
    @Test
    void testNamesFilter_whenCallingNamesFluxFilter_returnSomeNamesConvertedToUpperCase() {

        // Arrange
        var namesFlux = reactiveMonoFlux.namesFluxFilter(3);

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "CHLOE")
                .verifyComplete();
    }

    /**
     * Testing multiple chain operators
     * **/
    @Test
    void testChainingMultipleOperators_whenCallingNamesFluxMultipleChaining_returnSomeNamesConvertedToUpperCaseWithStringLength() {
        
        // Arrange
        var namesFlux = reactiveMonoFlux.namesFluxMultipleChaining(3);
        
        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    /**
     * Testing flatmaps
     */
    @Test
    void testFlatmapOperators_whenCallingNamesFluxFlatmap_returnNamesAsFlatmap() {
        // Arrange
        var namesFlux = reactiveMonoFlux.namesFluxFlatmap(3);

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }


    /**
     * Testing Async behaviour of the Flatmaps
     */
    @Test
    void testAsyncBehaviour_whenCallingNamesFluxFlatmapAsync_returnNamesInRandomOrder() {
        // Arrange
        var namesFlux = reactiveMonoFlux.namesFluxFlatmapAsync(3);

        // Act and Assert
        StepVerifier.create(namesFlux)
//                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E") // won't work since the order of the element is not guaranteed
                .expectNextCount(9)
                .verifyComplete();
    }

    /**
     * Testing Async behaviour of the Flatmaps
     */
    @Test
    void testConcatBehaviour_whenCallingNamesFluxFlatmapConcat_returnNamesInOrder() {
        // Arrange
        var namesFlux = reactiveMonoFlux.namesFluxFlatmapAsyncConcat(3);

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void testFlatmapWithMono_whenCallingNameMonoFlatMap_returnList() {
        // Arrange
        var namesFlux = reactiveMonoFlux.nameMonoFlatMap(3);

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext(List.of("J", "A", "M", "E", "S"))
                .verifyComplete();
    }

    @Test
    void testFlatmapMany_whenCallingNameMonoFlatMapMany_returnFlux() {
        // Arrange
        var namesFlux = reactiveMonoFlux.nameMonoFlatMapMany(3);

        // Act and Assert
        StepVerifier.create(namesFlux)
                .expectNext("J", "A", "M", "E", "S")
                .verifyComplete();
    }

    @Test
    void testTransform_whenCallingNamesFluxTransform_returnFlux() {
        var namesFlux = reactiveMonoFlux.namesFluxTransform(3);

        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void testDefaultOutput_whenCallingNamesDefaultIfEmpty_returnFluxOfDefaultString() {
        var namesFlux = reactiveMonoFlux.namesDefaultIfEmpty();
        StepVerifier.create(namesFlux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void testSwitchIfEmpty_whenCallingNamesSwitchIfEmpty_returnFluxOfDefaultString() {
        var namesFlux = reactiveMonoFlux.namesSwitchIfEmpty();
        StepVerifier.create(namesFlux)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }

    @Test
    void testConcat_whenCallingExploreConcat_returnFluxOfMultipleStrings() {
        var namesFlux = reactiveMonoFlux.exploreConcat();
        StepVerifier.create(namesFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }


    @Test
    void testConcatWith_whenCallingExploreConcatWithFlux_returnFluxOfMultipleStrings() {
        var namesFlux = reactiveMonoFlux.exploreConcatWithFlux();
        StepVerifier.create(namesFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void testConcatWithMono_whenCallingExploreConcatWithMono_returnFluxOfConcatenatedMonoStrings() {
        var namesFlux = reactiveMonoFlux.exploreConcatWithMono();
        StepVerifier.create(namesFlux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void testMerge_whenCallingExploreMerge_returnFluxOfMultipleStrings() {
        var namesFlux = reactiveMonoFlux.exploreMerge();
        StepVerifier.create(namesFlux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void testMerge_whenCallingExploreMergeWith_returnFluxOfMultipleStrings() {
        var namesFlux = reactiveMonoFlux.exploreMergeWith();
        StepVerifier.create(namesFlux)
                .expectNext("A","B")
                .verifyComplete();
    }

    @Test
    void testMergeSequential_whenCallingExploreMergeSequential_returnFluxOfStringsInSequence() {
        var namesFlux = reactiveMonoFlux.exploreMergeSequential();
        StepVerifier.create(namesFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void testZip_whenCallingExploreZip_returnFluxOfString() {
        var namesFlux = reactiveMonoFlux.exploreZip();
        StepVerifier.create(namesFlux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void testZipTuple_whenCallingExploreZipTuple_returnFluxOfString() {
        var namesFlux = reactiveMonoFlux.exploreZipTuple();
        StepVerifier.create(namesFlux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void testZipWith_whenCallingExploreZipWith_returnFluxOfString() {
        var namesFlux = reactiveMonoFlux.exploreZipWith();
        StepVerifier.create(namesFlux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }
}
