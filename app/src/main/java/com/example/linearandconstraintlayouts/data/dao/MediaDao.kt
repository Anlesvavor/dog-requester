package com.example.linearandconstraintlayouts.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.linearandconstraintlayouts.data.entities.Media
import com.example.linearandconstraintlayouts.data.entities.MediaColumns

@Dao
interface MediaDao {
    @Query("SELECT * FROM media")
    suspend fun getAll(): List<Media>

    @Insert
    suspend fun insertAll(vararg medias: Media): List<Long>

    @Delete
    suspend fun delete(media: Media)

    @Query("SELECT * FROM media where ${MediaColumns.EXTERNAL_ID} = :id")
    suspend fun findByExternalId(id: String): Media?

    @Query("SELECT image FROM media where id = :id")
    suspend fun getImageById(id: Long): ByteArray
}
