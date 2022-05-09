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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import retrofit2.Response
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
class DispatchDogTestTwo {

    @Mock
    lateinit var mockDogApiHttpClient: DogApiInterface

    lateinit var database: AppDatabase
    lateinit var repository: Repository

    @Before
    fun setup () {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        repository = Repository(
            dogDataSource = DogDataSource(
                mockDogApiHttpClient
            ),
            breedDaoFactory = BreedDaoFactory(
                breedDataSource = BreedDataSource(
                    dogApiInterface = mockDogApiHttpClient
                ),
                database = database
            ),
            dogDaoFactory = DogDaoFactory(
                database = database
            ),
            mediaDaoFactory = MediaDaoFactory(
                database = database,
                mediaDataSource = MediaDataSource(
                    dogApiInterface = mockDogApiHttpClient,
                    dogCdnInterface = DogCdnHTTPClient.getInstance()
                )
            )
        )
    }

    @Test
    fun dispatchDogAPICallReturnsAMessage() {
        runBlocking {
            val dogRequest = DispatchDogRequest("Joe")
            val dogResponse = DispatchDogResponse("Joe Bad")
            mockDogApiHttpClient.stub {
                onBlocking { dispatchDog(dogRequest) }.doReturn(
                    Response.success(dogResponse)
                )
            }

            assert(repository.dispatchDog("Joe") == "Joe Bad")
        }
    }
}