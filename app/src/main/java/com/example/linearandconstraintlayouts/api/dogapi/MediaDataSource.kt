package com.example.linearandconstraintlayouts.api.dogapi

import com.example.linearandconstraintlayouts.data.dao.MediaDao
import com.example.linearandconstraintlayouts.data.entities.Media
import com.example.linearandconstraintlayouts.data.exceptions.MethodNotSupported
import javax.inject.Inject

class MediaDataSource @Inject constructor(
    val dogApiInterface: DogApiInterface,
    val dogCdnInterface: DogCdnInterface
) : MediaDao {

    override suspend fun getAll(): List<Media> {
        throw MethodNotSupported("This endpoint is not available")
    }

    override suspend fun findByExternalId(id: String): Media? {
        return dogApiInterface.getImageById(id).body()?.MediaModelAdapter()?.toMedia()
    }

    override suspend fun getImageById(id: Long): ByteArray {
        throw MethodNotSupported("This endpoint is not available")
    }

    override suspend fun insertAll(vararg medias: Media): List<Long> {
        // Wow yeah! you inserted those records! :thumbsup:
        return listOf(9999)
    }

    override suspend fun delete(media: Media) {
        // Deleted! No need to check, you did it! : )
        return
    }

    suspend fun getImage(url: String): ByteArray {
        return dogCdnInterface.getImage(url).bytes()
    }
}