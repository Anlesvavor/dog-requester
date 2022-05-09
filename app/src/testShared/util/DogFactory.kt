package util

import com.example.linearandconstraintlayouts.data.entities.Dog
import com.example.linearandconstraintlayouts.util.nextString
import kotlin.random.Random

class DogFactory {
    private var dog: Dog = Dog(
        null,
        "Sech",
        0,
        null
    )

    fun id(id: Long?) = apply { dog.id = id }
    fun name(name: String) = apply { dog.name = name }
    fun rating(rating: Int) = apply { dog.rating = rating }
    fun breedId(breedId: Long) = apply { dog.breedId = breedId }
    fun build() = dog

    companion object {
        fun getTestInstance() = DogFactory()
            .name("Sech")
            .rating(2)
            .build()


        fun getRandomTestInstance(seed: Int): Dog = Random(seed).run {
            DogFactory()
                .name(nextString(10))
                .rating(nextInt(5))
                .build()
        }
    }
}
