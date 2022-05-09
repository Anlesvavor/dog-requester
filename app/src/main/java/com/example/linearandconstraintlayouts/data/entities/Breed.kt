package com.example.linearandconstraintlayouts.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.linearandconstraintlayouts.interfaces.RoughlyEquals

@Entity(
    indices = [Index(value = [BreedColumns.NAME], unique = true)]
)
data class Breed (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = BreedColumns.ID) var id: Long?,
    @ColumnInfo(name = BreedColumns.NAME)
    var name: String,
    @ColumnInfo(name = BreedColumns.WEIGHT) var weight: String?,
    @ColumnInfo(name = BreedColumns.HEIGHT) var height: String?,
    @ColumnInfo(name = BreedColumns.LIFE_SPAN) var lifeSpan: String?,
    @ColumnInfo(name = BreedColumns.BRED_FOR) var bredFor: String?,
    @ColumnInfo(name = BreedColumns.BREED_GROUP) var breedGroup: String?,
    @ColumnInfo(name = BreedColumns.MEDIA_ID)var mediaId: String?,
    @ColumnInfo(name = BreedColumns.EXTERNAL_ID) var externalId: String?,
    @ColumnInfo(name = BreedColumns.IMAGE_URL) var imageUrl: String?
) : RoughlyEquals {
    override infix fun roughlyEqualsTo(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Breed

        if (name != other.name) return false
        if (weight != other.weight) return false
        if (height != other.height) return false
        if (lifeSpan != other.lifeSpan) return false
        if (bredFor != other.bredFor) return false
        if (breedGroup != other.breedGroup) return false
        if (mediaId != other.mediaId) return false
        if (externalId != other.externalId) return false

        return true
    }

    override fun toString(): String {
        return "Breed(id=$id, name='$name', weight=$weight, height=$height, lifeSpan=$lifeSpan, bredFor=$bredFor, breedGroup=$breedGroup, mediaId=$mediaId, externalId=$externalId)"
    }

}

object BreedColumns {
    const val ID = "id"
    const val NAME = "name"
    const val WEIGHT = "weight"
    const val HEIGHT = "height"
    const val LIFE_SPAN = "life_span"
    const val BRED_FOR = "bred_for"
    const val BREED_GROUP = "breed_group"
    const val MEDIA_ID = "media_id"
    const val EXTERNAL_ID = "external_id"
    const val IMAGE_URL = "image_url"
}