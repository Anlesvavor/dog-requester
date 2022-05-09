package com.example.linearandconstraintlayouts.data.factories

import android.content.Context
import com.example.linearandconstraintlayouts.api.dogapi.BreedDataSource
import com.example.linearandconstraintlayouts.data.AppDatabase
import com.example.linearandconstraintlayouts.data.dao.BreedDao
import javax.inject.Inject

class BreedDaoFactory @Inject constructor(
    private val database: AppDatabase,
    private val breedDataSource: BreedDataSource
) {
    fun createFor(location: DataLocation): BreedDao = when (location) {
        DataLocation.LOCAL-> database.breedDao()
        DataLocation.EXTERNAL -> breedDataSource
    }

}
