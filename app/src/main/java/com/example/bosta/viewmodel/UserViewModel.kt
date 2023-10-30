package com.example.bosta.viewmodel



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bosta.model.*
import com.example.bosta.services.UserClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserViewModel(): ViewModel() {

    var userLiveData :UserModel by mutableStateOf(UserModel(0,"","", AddressModel("","","","",)))
    var albumLiveData :List<AlbumModel> by mutableStateOf(listOf())
    var photoLiveData:List<PhotosModel>by mutableStateOf(listOf())
    var errorMessage :String by mutableStateOf("")
    var loading:Boolean by mutableStateOf(true)
    private val _searchText = MutableStateFlow("")


    private val randomId = kotlin.random.Random.nextInt(1,10)


    fun getAUser(){
        viewModelScope.launch(Dispatchers.IO){
            loading =true
            try {
                var response =async { UserClient().getINSTANCE().getAUser(id = randomId)}
                userLiveData= response.await()
                if(response.isCompleted)
                    loading = false
            }catch (e:Exception){
            errorMessage = e.message.toString()
            }

        }
    }

    fun getAlbums(){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                var response =async {   UserClient().getINSTANCE().getAlbumsForAUser(randomId)}
                albumLiveData= response.await()

            }catch (e:Exception){
                errorMessage = e.message.toString()
            }

        }
    }

    fun getPhotos(albumId:Int){
        viewModelScope.launch (Dispatchers.IO) {
            try {
            var response =async { UserClient().getINSTANCE().getPhotos(albumId)}
                photoLiveData =response.await()
            }catch (e:Exception){
                errorMessage = e.message.toString()
            }
        }
    }
}