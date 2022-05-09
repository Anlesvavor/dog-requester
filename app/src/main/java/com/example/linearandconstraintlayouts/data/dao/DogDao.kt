package com.example.linearandconstraintlayouts.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.linearandconstraintlayouts.data.entities.Dog
import com.example.linearandconstraintlayouts.data.entities.DogColumns

@Dao
interface DogDao {
    @Query("SELECT * FROM dog")
    suspend fun getAll(): List<Dog>

    @Query("SELECT * FROM dog where ${DogColumns.NAME} LIKE :name")
    suspend fun findByName(name: String): List<Dog>

    @Query("SELECT * FROM dog where ${DogColumns.ID} = :id")
    suspend fun findById(id: Long): Dog?

    @Insert
    suspend fun insertAll(vararg dogs: Dog): List<Long>

    @Delete
    suspend fun delete(dog: Dog)

    @Query("DELETE FROM dog WHERE id = :id")
    suspend fun deleteById(id: Long)

}
