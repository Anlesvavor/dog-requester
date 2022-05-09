package com.example.linearandconstraintlayouts.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.linearandconstraintlayouts.interfaces.RoughlyEquals

@Entity
data class Dog (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DogColumns.ID) var id: Long?,
    @ColumnInfo(name = DogColumns.NAME) var name: String,
    @ColumnInfo(name = DogColumns.RATING) var rating: Int,
    @ColumnInfo(name = DogColumns.BREED_ID) var breedId: Long?
) : RoughlyEquals {
    override fun roughlyEqualsTo(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Dog

        if (name != other.name) return false
        if (rating != other.rating) return false
        if (breedId != other.breedId) return false

        return true
    }
}

object DogColumns {
    const val ID: String = "id"
    const val NAME: String = "name"
    const val RATING: String = "rating"
    const val BREED_ID: String = "breed_id"
}