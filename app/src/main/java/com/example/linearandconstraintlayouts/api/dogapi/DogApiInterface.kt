package com.example.linearandconstraintlayouts.api.dogapi

import retrofit2.Response
import retrofit2.http.*

interface DogApiInterface {

    @GET("v1/breeds")
    suspend fun getAllBreeds(): Response<List<BreedResponse>>

    @GET("v1/breeds")
    suspend fun getPaginatedBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<List<BreedResponse>>

    @GET("v1/breeds/search")
    suspend fun searchBreedByName(@Query("q") query: String): Response<List<BreedResponse>>

    @GET("v1/breeds/{id}")
    suspend fun getBreedById(@Path("id") id: Long): Response<BreedResponse?>

    // FAKE METHOD
    @POST("v1/dispatch")
    suspend fun dispatchDog(@Body dogRequest: DispatchDogRequest): Response<DispatchDogResponse>

    @GET("v1/images/{imageId}")
    suspend fun getImageById(@Path("imageId") id: String): Response<ImageResponse?>
}