package com.example.linearandconstraintlayouts.repository

import com.example.linearandconstraintlayouts.api.dogapi.DogDataSource
import com.example.linearandconstraintlayouts.api.dogapi.MediaDataSource
import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.data.entities.Dog
import com.example.linearandconstraintlayouts.data.entities.Media
import com.example.linearandconstraintlayouts.data.factories.BreedDaoFactory
import com.example.linearandconstraintlayouts.data.factories.DataLocation
import com.example.linearandconstraintlayouts.data.factories.DogDaoFactory
import com.example.linearandconstraintlayouts.data.factories.MediaDaoFactory
import javax.inject.Inject

class Repository @Inject constructor(
    private val breedDaoFactory: BreedDaoFactory,
    private val dogDaoFactory: DogDaoFactory,
    private val dogDataSource: DogDataSource,
    private val mediaDaoFactory: MediaDaoFactory
) {
    suspend fun getAllBreeds(): List<Breed> {
        val breedDao = breedDaoFactory.createFor(DataLocation.LOCAL)
        return breedDao.getAll()
    }

    suspend fun refreshBreeds() {
        val breedDao = breedDaoFactory.createFor(DataLocation.LOCAL)
        val externalBreedDao = breedDaoFactory.createFor(DataLocation.EXTERNAL)
        val externalBreeds = externalBreedDao.getAll()
        breedDao.upsert(*externalBreeds.toTypedArray())
    }

    suspend fun requestDog(dog: Dog): Long {
        val dogDao = dogDaoFactory.createFor(DataLocation.LOCAL)
        return dogDao.insertAll(dog).first()
    }

    suspend fun getAllDogs(): List<Dog> {
        val dogDao = dogDaoFactory.createFor(DataLocation.LOCAL)
        return dogDao.getAll()
    }

    suspend fun dismissDog(dog: Dog) {
        val dogDao = dogDaoFactory.createFor(DataLocation.LOCAL)
        dogDao.delete(dog)

    }

    suspend fun dismissDog(id: Long) {
        val dogDao = dogDaoFactory.createFor(DataLocation.LOCAL)
        dogDao.deleteById(id)
    }


    suspend fun getBreedById(id: Long): Breed? {
        val breedDao = breedDaoFactory.createFor(DataLocation.LOCAL)
        return breedDao.findById(id)
    }

    suspend fun getDogById(id: Long): Dog? {
        val dogDao = dogDaoFactory.createFor(DataLocation.LOCAL)
        return dogDao.findById(id)
    }

    // Just for the mockito test!
    suspend fun dispatchDog(name: String): String {
        return dogDataSource.dispatchDog(name).message
    }

    suspend fun getMediaByExternalId(id: String): Media? {
        return mediaDaoFactory.createFor(DataLocation.EXTERNAL).findByExternalId(id)
    }
}