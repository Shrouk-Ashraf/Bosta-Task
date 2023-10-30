package com.example.bosta

import com.example.bosta.model.*
import com.example.bosta.viewmodel.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bosta.view.*

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<UserViewModel>()
    var loading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = viewModel.loading
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
            ) {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    content = {

                        AlbumList(albumData = viewModel.albumLiveData, userData =viewModel.userLiveData,loading=loading ,this)
                        viewModel.getAUser()
                        viewModel.getAlbums()
                        loading =false
                    })
            }

        }
    }


    @Composable
    fun AlbumList(albumData: List<AlbumModel>, userData:UserModel, loading:Boolean,activity :MainActivity) {
        if(loading){
            ShowProgressBar(isDisplayed = loading)
        }else{
        LazyColumn(modifier = Modifier.padding(8.dp)) {

            item { Text(userData.name, style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
            ), modifier = Modifier.padding(8.dp)
            )}
            item {
                UserAddress(userData = userData)
            }

            item { Divider(color = Color.Gray, thickness = 0.5.dp ,startIndent = 8.dp, modifier = Modifier.padding(vertical = 5.dp)) }

            item { Text("My Albums", style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
            ), modifier = Modifier.padding(8.dp)
            )}

            items(albumData) {
                AlbumItem(it,activity)
            }
        }
        }
    }

    @Composable
    fun UserAddress(userData:UserModel){
        val address = userData.address
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)) {
            Text(text = "${address.street}, ${address.city}, ${address.suite}, ${address.zipcode}", style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)
        }

    }


}