package com.example.linearandconstraintlayouts.data.factories

import android.content.Context
import com.example.linearandconstraintlayouts.api.dogapi.MediaDataSource
import com.example.linearandconstraintlayouts.data.AppDatabase
import com.example.linearandconstraintlayouts.data.dao.MediaDao
import javax.inject.Inject

class MediaDaoFactory @Inject constructor(
    private val database: AppDatabase,
    private val mediaDataSource: MediaDataSource
) {
    fun createFor(location: DataLocation): MediaDao = when (location) {
        DataLocation.LOCAL-> database.mediaDao()
        DataLocation.EXTERNAL -> mediaDataSource
    }

}
