package com.example.bosta

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bosta.ui.theme.BostaTheme
import com.example.bosta.view.*
import com.example.bosta.model.*
import com.example.bosta.viewmodel.UserViewModel

class AlbumsActivity : ComponentActivity() {
    private val viewModel by viewModels<UserViewModel>()
    var loading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent.getIntExtra("albumId",-1)

        loading = viewModel.loading

        setContent {
            BostaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AlbumPhotosList(photoData = viewModel.photoLiveData, activity = this,modifier= Modifier.fillMaxSize(),loading =loading)
                    viewModel.getPhotos(intent)
                    loading =false
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumPhotosList(photoData:List<PhotosModel>, activity: Activity, modifier: Modifier,loading:Boolean) {
    if(loading){
        ShowProgressBar(isDisplayed = loading)
    }
    val textState = remember{ mutableStateOf(TextFieldValue("")) }

   Column(){
         SearchBar(state=textState,placeholder= "Search....", modifier=modifier)
       val searchedText = textState.value.text

            LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)) {
                items(photoData.filter {
                    it.title.contains(searchedText,ignoreCase = true)
                    },){
                    item->
                    AlbumItemData(photo = item, activity =activity )
                }

    }

    }
}

@Composable
fun SearchBar(state: MutableState<TextFieldValue>, placeholder: String,modifier: Modifier) {
    TextField(
        value =state.value,
        onValueChange ={
            value ->
            state.value = value
        },
        modifier= Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(2.dp,Color.Gray, RoundedCornerShape(30.dp)),
        placeholder ={
            Text(text =placeholder)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        ),
        maxLines = 1,
        singleLine = true
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumItemData(photo: PhotosModel, activity: Activity){
    Card(modifier = Modifier
        .padding(8.dp, 4.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        onClick ={
            val photoIntent : Intent = Intent(activity,PhotoActivity::class.java)
            photoIntent.putExtra("photoUrl",photo.url)
            activity.startActivity(photoIntent)
        }
    ) {
        Surface {
           AsyncImage(model = photo.url, contentDescription =photo.title)
        }

    }
}
