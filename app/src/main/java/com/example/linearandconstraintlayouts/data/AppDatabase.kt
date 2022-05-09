package com.example.linearandconstraintlayouts.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.linearandconstraintlayouts.data.dao.BreedDao
import com.example.linearandconstraintlayouts.data.dao.DogDao
import com.example.linearandconstraintlayouts.data.dao.MediaDao
import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.data.entities.Dog
import com.example.linearandconstraintlayouts.data.entities.Media

@Database(entities = [Breed::class, Dog::class, Media::class], version = 4,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun dogDao(): DogDao
    abstract fun mediaDao(): MediaDao
}
