package com.example.linearandconstraintlayouts.api.dogapi

import com.example.linearandconstraintlayouts.data.adapters.BreedAdapter
import com.example.linearandconstraintlayouts.data.entities.Breed
import com.google.gson.annotations.SerializedName

data class BreedResponse(
    @SerializedName(value = "bred_for")
    val bredFor: String,
    @SerializedName(value = "breed_group")
    val breedGroup: String,
    val height: MetricResponse,
    val id: Long,
    val image: ImageResponse?,
    @SerializedName(value = "life_span")
    val lifeSpan: String,
    val name: String,
    @SerializedName(value = "reference_image_id")
    val referenceImageId: String,
    val temperament: String,
    val weight: MetricResponse
) {
    data class MetricResponse(
        val imperial: String,
        val metric: String
    )

    inner class BreedModelAdapter : BreedAdapter {
        override fun toBreed() = Breed(
            id = null,
            name = name,
            bredFor = bredFor,
            breedGroup = breedGroup,
            externalId = id.toString(),
            height = height.metric,
            lifeSpan = lifeSpan,
            weight = weight.metric,
            mediaId = image?.id,
            imageUrl = image?.url
        )

    }
}
