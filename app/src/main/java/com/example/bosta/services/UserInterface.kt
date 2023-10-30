package com.example.bosta.services

import retrofit2.http.GET
import retrofit2.http.Path
import com.example.bosta.model.*
import retrofit2.Response
import retrofit2.http.Query


interface UserInterface {
    @GET("users/{id}")
    suspend fun getAUser(@Path("id") id:Int) : UserModel

    @GET("albums")
    suspend fun getAlbumsForAUser(@Query("userId") userId:Int):List<AlbumModel>

    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId:Int): List<PhotosModel>
}