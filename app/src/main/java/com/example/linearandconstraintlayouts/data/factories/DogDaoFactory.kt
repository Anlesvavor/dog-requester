package com.example.linearandconstraintlayouts.data.factories

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.linearandconstraintlayouts.api.dogapi.BreedDataSource
import com.example.linearandconstraintlayouts.data.AppDatabase
import com.example.linearandconstraintlayouts.data.dao.BreedDao
import com.example.linearandconstraintlayouts.data.dao.DogDao
import javax.inject.Inject
import kotlin.jvm.Throws

class DogDaoFactory @Inject constructor(
    private val database: AppDatabase
) {
    @Throws
    fun createFor(location: DataLocation): DogDao = when (location) {
        DataLocation.LOCAL-> database.dogDao()
        DataLocation.EXTERNAL -> {
            throw NotImplementedError("The DogDao Is not available externally, sorry")
        }
    }

}
