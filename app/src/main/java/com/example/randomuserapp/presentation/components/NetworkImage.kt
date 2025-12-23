package com.example.randomuserapp.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
internal fun NetworkImage(url: String, contentDescription: String?, width: Int, height: Int) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier.padding(10.dp).width(width.dp).height(height.dp).clip(RoundedCornerShape(8.dp)),
        loading = {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.requiredSize(48.dp)
            )
        },
        error = {
            Log.d("TAG", url)
            Log.d("TAG", "something went wrong ${it.result.throwable.localizedMessage}")
        }
    )
}