package com.example.linearandconstraintlayouts.api.dogapi

import com.example.linearandconstraintlayouts.data.dao.BreedDao
import javax.inject.Inject

// Open just for  mockito, couldn't install the libraries that supposedly enables mockito kotlin
// to run with final classes
open class DogDataSource @Inject constructor(
    var client: DogApiInterface
) {

    suspend fun dispatchDog(name: String): DispatchDogResponse {
        val request = client.dispatchDog(DispatchDogRequest(name))
        if (!request.isSuccessful) { throw Exception("Not successful") }

        request.body()?.let {
            return it
        }
        throw Exception("Empty body")
    }

    fun test(name: String): String {
        return "Potato"
    }
}