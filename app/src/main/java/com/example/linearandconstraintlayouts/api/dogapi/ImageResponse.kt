package com.example.linearandconstraintlayouts.api.dogapi

import com.example.linearandconstraintlayouts.data.adapters.MediaAdapter
import com.example.linearandconstraintlayouts.data.entities.Media

data class ImageResponse(
    val height: Long,
    val id: String,
    val url: String,
    val width: Long,
) {
    inner class MediaModelAdapter : MediaAdapter {
        override fun toMedia() = Media(
            id = null,
            url = url,
            image = null,
            width = width,
            height = height,
            externalId = id
        )
    }
}

