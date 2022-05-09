package com.example.linearandconstraintlayouts.data.factories

import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.data.entities.Dog
import com.example.linearandconstraintlayouts.util.randomName

class DogFactory {
    fun fromBreed(breed: Breed) = Dog(
        name = randomName(4),
        rating = 0,
        breedId = breed.id,
        id = null
    )
}