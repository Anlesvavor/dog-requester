package com.example.linearandconstraintlayouts

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.linearandconstraintlayouts.api.dogapi.BreedDataSource
import com.example.linearandconstraintlayouts.api.dogapi.DogApiHTTPClient
import com.example.linearandconstraintlayouts.api.dogapi.DogApiInterface
import com.example.linearandconstraintlayouts.data.AppDatabase
import com.example.linearandconstraintlayouts.data.dao.BreedDao
import com.example.linearandconstraintlayouts.data.factories.BreedDaoFactory
import com.example.linearandconstraintlayouts.data.factories.DataLocation
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.linearandconstraintlayouts.data.factories.BreedFactory
import com.example.linearandconstraintlayouts.util.assertNull
import dagger.hilt.android.testing.HiltAndroidTest
import org.intellij.lang.annotations.JdkConstants
import java.io.IOException
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class BreedDaoTest {
    private lateinit var breedDao: BreedDao
    lateinit var breedDaoFactory: BreedDaoFactory
    private lateinit var db: AppDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        breedDaoFactory = BreedDaoFactory(
            breedDataSource = BreedDataSource(
                DogApiHTTPClient.getInstance()
            ),
            database = db
        )
        breedDao = breedDaoFactory.createFor(DataLocation.LOCAL)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeBreedAndReadInList() = runBlocking {
        with(BreedFactory.getRandomTestInstance(420)) {
            val id = breedDao.insertAll(this).first()
            val recoveredBreed =
                breedDao.findById(id) ?: return@runBlocking assert(false)
            assert(recoveredBreed roughlyEqualsTo this)
        }
    }

    @Test
    fun writeBreedAndDeleteIt() = runBlocking {
        with(BreedFactory.getRandomTestInstance(234)) {
            val id = breedDao.insertAll(this).first()
            val recoveredBreed =
                breedDao.findById(id) ?: return@runBlocking assert(false)
            breedDao.delete(recoveredBreed)
            assertNull(breedDao.findById(id))
        }
    }

    @Test
    fun getAll() = runBlocking {
        with(BreedFactory.getRandomTestInstance(234)) {
            breedDao.insertAll(this)
            val allBreeds = breedDao.getAll()
            return@runBlocking assert(allBreeds.isNotEmpty())
        }
    }

}