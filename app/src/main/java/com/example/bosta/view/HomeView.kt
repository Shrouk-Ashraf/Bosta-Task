package com.example.bosta.view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.example.bosta.model.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bosta.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumItem(albums: AlbumModel,activity: Activity){
    Card(modifier = Modifier
        .padding(8.dp, 4.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 7.dp,
        onClick ={
            val intent : Intent = Intent(activity,AlbumsActivity::class.java)
            intent.putExtra("albumId",albums.id)
            activity.startActivity(intent)
        }
    ) {
        Surface {
            Text(text = albums.title ,style = TextStyle(
                    color = Color.Black,
                fontSize = 15.sp,
            ), modifier = Modifier.padding(8.dp)
            )
        }

    }

}