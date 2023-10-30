package com.example.bosta.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserClient {

    private val BASE_URL :String ="https://jsonplaceholder.typicode.com/"
    private lateinit var userInterface:UserInterface



    fun getINSTANCE() :UserInterface{
        var retrofit:Retrofit =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        userInterface= retrofit.create(UserInterface::class.java)

       return  userInterface
    }


}