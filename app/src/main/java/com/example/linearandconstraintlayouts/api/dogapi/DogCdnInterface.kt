package com.example.linearandconstraintlayouts.api.dogapi

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface DogCdnInterface {

    @GET
    suspend fun getImage(@Url imagePath: String): ResponseBody

}
