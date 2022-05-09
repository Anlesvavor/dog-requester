package com.example.linearandconstraintlayouts.data.dao

import androidx.room.*
import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.data.entities.BreedColumns

@Dao
interface BreedDao {
    @Query("SELECT * FROM breed")
    suspend fun getAll(): List<Breed>

    @Query("SELECT * FROM breed where ${BreedColumns.NAME} LIKE :name")
    suspend fun findByName(name: String): List<Breed>

    @Query("SELECT * FROM breed where ${BreedColumns.ID} = :id")
    suspend fun findById(id: Long): Breed?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg breeds: Breed): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(breeds: Breed): Int

    @Delete
    suspend fun delete(breed: Breed)

    @Transaction
    suspend fun upsert(vararg breeds: Breed) {
        val ids = insertAll(*breeds)

        val idsWIthIndex = ids.mapIndexed { index, id -> Pair(index, id) }
        val toUpdate = idsWIthIndex.filter { pair -> pair.second == (-1).toLong() }
        val breedsToUpdate = toUpdate. map { breeds[it.first] }
        breedsToUpdate.forEach { update(it) }

    }
}