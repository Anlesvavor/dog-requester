package com.example.linearandconstraintlayouts

import org.junit.Test
import com.example.linearandconstraintlayouts.data.factories.BreedFactory
import java.security.SecureRandom

class BreedTestFactoryTest {
    @Test
    fun differentBreedsWhenUsingDifferentSeeds() = with(SecureRandom.getInstanceStrong()) {
        arrayOf(nextInt(), nextInt())
            .map(BreedFactory::getRandomTestInstance) // Create a instance for testing
            .zipWithNext() // Build pairs of objects in order to compare them
            .all { pair -> pair.first !== pair.second } // Compare that such pairs are different
            .let { assert(it) }                         // And by extension, that all elements are.
    }

    @Test
    fun sameBreedsWhenUsingSameSeeds() = with(SecureRandom.getInstanceStrong()) {
        val seed = nextInt()
        arrayOf(seed, seed, seed)
            .map(BreedFactory::getRandomTestInstance)
            .zipWithNext()
            .all { pair -> pair.first == pair.second }
            .let { assert(it) }
    }

}