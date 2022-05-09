package com.example.linearandconstraintlayouts

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.linearandconstraintlayouts.api.dogapi.BreedDataSource
import com.example.linearandconstraintlayouts.api.dogapi.DogApiHTTPClient
import com.example.linearandconstraintlayouts.data.entities.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BreedDataSourceTest {

    lateinit var breedDataSource: BreedDataSource

    @Before
    fun setup() {
        breedDataSource =
            BreedDataSource(DogApiHTTPClient.getInstance())
    }

    @Test
    fun getAllBreeds() {
        runBlocking(Dispatchers.IO) {
            val breeds = breedDataSource.getAll()
            assert(breeds.isNotEmpty())
        }
    }

    @Test
    fun getFirstBreed() {
        runBlocking {
            val breed: Breed = breedDataSource.findById(1) ?: return@runBlocking assert(false)

            assert(
                listOf(
                    breed.bredFor == "Small rodent hunting, lapdog",
                    breed.breedGroup == "Toy",
                    breed.lifeSpan == "10 - 12 years",
                    breed.height == "23 - 29",
                    breed.name == "Affenpinscher",
                    breed.weight == "3 - 6",
                    breed.externalId == "1"
                ).all { it }
            )
        }
    }
}