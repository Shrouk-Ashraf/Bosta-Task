package com.example.bosta.model

import com.example.bosta.model.AddressModel
import java.util.Objects

data class UserModel(
    val id:Int,
    val name:String,
    val username: String,
    val address :AddressModel
    )
