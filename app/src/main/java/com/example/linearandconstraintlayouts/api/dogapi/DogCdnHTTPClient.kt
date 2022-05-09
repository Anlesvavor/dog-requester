package com.example.linearandconstraintlayouts.api.dogapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DogCdnHTTPClient {
    private val client: DogCdnInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://cdn2.thedogapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogCdnInterface::class.java)
    }

    fun getInstance() = client
}
