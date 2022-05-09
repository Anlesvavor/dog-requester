package com.example.linearandconstraintlayouts.api.dogapi

import com.example.linearandconstraintlayouts.data.dao.BreedDao
import com.example.linearandconstraintlayouts.data.entities.Breed
import javax.inject.Inject

class BreedDataSource @Inject constructor(
    val dogApiInterface: DogApiInterface
) : BreedDao {
    /*
    private val dogApiInterface: DogApiInterface by lazy {
        DogApiHTTPClient
            .getInstance()
            .create(DogApiInterface::class.java)
    }
    */

    override suspend fun getAll(): List<Breed> {
        val request = dogApiInterface.getAllBreeds()
        if (!request.isSuccessful) { throw Exception("Not successful") }
        return request.body()?.map {
           it.BreedModelAdapter().toBreed()

        } ?: emptyList()
    }

    override suspend fun findByName(name: String): List<Breed> {
        return dogApiInterface.searchBreedByName(name).body()?.map {
            it.BreedModelAdapter().toBreed()
        } ?: emptyList()
    }

    override suspend fun findById(id: Long): Breed? {
        return dogApiInterface.getBreedById(id).body()?.BreedModelAdapter()?.toBreed()
    }

    override suspend fun insertAll(vararg breeds: Breed): List<Long> {
        // Wow yeah! you inserted those records! :thumbsup:
        return listOf(9999)
    }

    override suspend fun update(breeds: Breed): Int {
        // Wow yeah! you updated those records! :thumbsup:
        return 1
    }

    override suspend fun delete(breed: Breed) {
        // Deleted! No need to check, you did it! : )
        return
    }
}