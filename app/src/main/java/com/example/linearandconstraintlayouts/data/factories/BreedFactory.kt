package com.example.linearandconstraintlayouts.data.factories

import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.util.nextString
import kotlin.random.Random

class BreedFactory {
    private var breed: Breed = Breed(
        null,
        "Golden Retriever",
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    fun id(id: Long?) = apply { breed.id = id }
    fun name(name: String) = apply { breed.name = name }
    fun weight(weight: String) = apply { breed.weight = weight }
    fun height(height: String) = apply { breed.height = height }
    fun lifeSpan(lifeSpan: String) = apply { breed.lifeSpan = lifeSpan }
    fun breedFor(breedFor: String) = apply { breed.bredFor = breedFor }
    fun breedGroup(breedGroup: String) = apply { breed.breedGroup = breedGroup }
    fun mediaId(mediaId: String) = apply { breed.mediaId = mediaId }
    fun externalId(externalId: String) = apply { breed.externalId = externalId }
    fun imageUrl(imageUrl: String) = apply { breed.imageUrl = imageUrl }
    fun build() = breed

    companion object {
        fun getTestInstance() = BreedFactory()
            .name("Golden Retriever")
            .weight("23")
            .height("123")
            .lifeSpan("20")
            .breedFor("Nothing")
            .breedGroup("None")
            .build()


        fun getRandomTestInstance(seed: Int): Breed = Random(seed).run {
            BreedFactory()
                .name(nextString(10))
                .weight(nextString(10))
                .height(nextString(10))
                .lifeSpan(nextString(10))
                .breedFor(nextString(10))
                .breedGroup(nextString(10))
                .build()
        }
    }
}
