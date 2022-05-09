package com.example.linearandconstraintlayouts.api.dogapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DogApiHTTPClient {
    private val client: DogApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiInterface::class.java)
    }

    fun getInstance() = client
}
