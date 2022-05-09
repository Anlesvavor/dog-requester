package com.example.linearandconstraintlayouts

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.linearandconstraintlayouts.api.dogapi.*
import com.example.linearandconstraintlayouts.data.AppDatabase
import com.example.linearandconstraintlayouts.data.dao.BreedDao
import com.example.linearandconstraintlayouts.data.dao.DogDao
import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.data.entities.Dog
import com.example.linearandconstraintlayouts.data.factories.*
import com.example.linearandconstraintlayouts.repository.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.linearandconstraintlayouts.util.assertNull
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltAndroidTest
import util.DogFactory
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class RepositoryTest {
    lateinit var repository: Repository
    lateinit var dogDaoFactory: DogDaoFactory
    lateinit var breedDaoFactory: BreedDaoFactory
    private val testDog: Dog = DogFactory.getRandomTestInstance(1233)
    private val testBreed: Breed = BreedFactory.getRandomTestInstance(312)
    lateinit var dogDao: DogDao
    lateinit var breedDao: BreedDao

    @Before
    fun initializeRepository() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        val dogApiInstance = DogApiHTTPClient.getInstance()

        val dogDaoFactory = DogDaoFactory(database)
        val breedDaoFactory  = BreedDaoFactory(database, breedDataSource = BreedDataSource(dogApiInstance))
        repository = Repository(
            dogDaoFactory = dogDaoFactory,
            breedDaoFactory = breedDaoFactory,
            dogDataSource = DogDataSource(client = dogApiInstance),
            mediaDaoFactory = MediaDaoFactory(
                database = database,
                mediaDataSource = MediaDataSource(
                    dogApiInterface = dogApiInstance,
                    dogCdnInterface = DogCdnHTTPClient.getInstance()
                )
            )
        )

        // Create some test data to test the repository.
        dogDao = dogDaoFactory.createFor(DataLocation.LOCAL)
        breedDao = breedDaoFactory.createFor(DataLocation.LOCAL)

        runBlocking {
            dogDao.insertAll(testDog)
            breedDao.insertAll(testBreed)
            return@runBlocking
        }
    }

    @Test
    fun getAllBreeds() = runBlocking {
        dogDao.insertAll(testDog)
        breedDao.insertAll(testBreed)
        val allBreeds = repository.getAllBreeds()
        return@runBlocking assert(allBreeds.isNotEmpty())
    }

    @Test
    fun refreshBreeds() {
        runBlocking {
            repository.refreshBreeds() // There is at least ONE breed coming from the API
            return@runBlocking assert(repository.getAllBreeds().size > 1)
        }
    }

    @Test
    fun requestDog() {
        runBlocking {
            val newTestDog = DogFactory.getRandomTestInstance(2)
            repository.requestDog(newTestDog) // Create a new dog sort of speak
            return@runBlocking assert(repository.getAllDogs().any { it roughlyEqualsTo newTestDog })
        }
    }

    @Test
    fun dismissDog() {
        runBlocking {
            val newTestDog = DogFactory.getRandomTestInstance(2)
            val id = repository.requestDog(newTestDog)
            // This MUST return the dog we just inserted
            val dogBackFromDatabase = dogDao.findById(id)!!
            repository.dismissDog(dogBackFromDatabase)
            return@runBlocking assertNull(dogDao.findById(id))
        }
    }


}