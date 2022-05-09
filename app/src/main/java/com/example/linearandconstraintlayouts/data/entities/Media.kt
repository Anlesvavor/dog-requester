package com.example.linearandconstraintlayouts.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Media(
   @PrimaryKey(autoGenerate = true) @ColumnInfo(name = MediaColumns.ID) var id: Long?,
   @ColumnInfo(name = MediaColumns.EXTERNAL_ID) var externalId: String,
   @ColumnInfo(name = MediaColumns.URL) var url: String,
   @ColumnInfo(name = MediaColumns.IMAGE, typeAffinity = ColumnInfo.BLOB) var image: ByteArray?,
   @ColumnInfo(name = MediaColumns.WIDTH) var width: Long,
   @ColumnInfo(name = MediaColumns.HEIGHT) var height: Long,
) {
   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Media

      if (image != null) {
         if (other.image == null) return false
         if (!image.contentEquals(other.image)) return false
      } else if (other.image != null) return false

      return true
   }

   override fun hashCode(): Int {
      return image?.contentHashCode() ?: 0
   }
}

object MediaColumns {
   const val ID = "ID"
   const val URL = "url"
   const val IMAGE = "image"
   const val HEIGHT= "height"
   const val WIDTH = "width"
   const val EXTERNAL_ID = "external_id"
}