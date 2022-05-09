package com.example.linearandconstraintlayouts

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.linearandconstraintlayouts.api.dogapi.*
import com.example.linearandconstraintlayouts.data.AppDatabase
import com.example.linearandconstraintlayouts.data.factories.BreedDaoFactory
import com.example.linearandconstraintlayouts.data.factories.DogDaoFactory
import com.example.linearandconstraintlayouts.data.factories.MediaDaoFactory
import com.example.linearandconstraintlayouts.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.stubbing

@RunWith(MockitoJUnitRunner::class)
class DispatchDogTest {

    lateinit var database: AppDatabase
    lateinit var dogApiClient: DogApiInterface
    lateinit var repositoryClosure: (DogDataSource) -> Repository

    @Before
    fun setup () {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        dogApiClient = DogApiHTTPClient.getInstance()

        repositoryClosure = { mockDogDataSource ->
            Repository(
                dogDataSource = mockDogDataSource,
                breedDaoFactory = BreedDaoFactory(
                    breedDataSource = BreedDataSource(
                        dogApiInterface = dogApiClient
                    ),
                    database = database
                ),
                dogDaoFactory = DogDaoFactory(
                    database = database
                ),
                mediaDaoFactory = MediaDaoFactory(
                    database = database,
                    mediaDataSource = MediaDataSource(
                        dogApiInterface = dogApiClient,
                        dogCdnInterface = DogCdnHTTPClient.getInstance()
                    )
                )
            )
        }
    }

    @Test
    fun dispatchDogAPICallReturnsAMessage() {
        runBlocking {

            val mockDogDataSource = Mockito.mock(DogDataSource::class.java)
            // Mockito.`when`(mockDogDataSource.dispatchDog(name = "pepe")).thenReturn(
            //     DispatchDogResponse(message = "Joe Bad")
            // )
            mockDogDataSource.client = DogApiHTTPClient.getInstance()
            mockDogDataSource.stub {
                onBlocking { dispatchDog("pepe") }.doReturn(DispatchDogResponse("Joe bad"))
            }

            val repository = repositoryClosure(mockDogDataSource)
/*
            assert(withContext(Dispatchers.Default) {
                mockDogDataSource.dispatchDog(
                    Mockito.anyString()
                ).message == "Joe bad"
            })
*/
            assert(withContext(Dispatchers.Default) {
                repository.dispatchDog(
                    name = "pepe"
                ) == "Joe bad"
            })
            // assert(verify(repository.dispatchDog("Joe")) == "Joe bad")
                //onBlocking { dispatchDog("pepe") } doThrow (Exception("Not successful"))

        }

        //assert(repository.dispatchDog("pepe") == "Pepe malo!")
    }



}