package com.personal.movieapp.widgets

import android.content.Context
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import com.personal.movieapp.model.MovieDetail
import util.AppConstant.IMAGE_BASE_URL

@Composable
fun MovieRow(movie: MovieDetail, context : Context = LocalContext.current) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        ,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Surface(modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
                shape = CircleShape,
                elevation = 4.dp) {
                val (_,modifier) = remember {
                        BlurTransformation(context = context, radius = 16f, sampling = 4f) to Modifier

                }

                Image(painter = rememberImagePainter(data = IMAGE_BASE_URL+movie.posterPath,
                                                    builder = {
                                                        crossfade(true)
                                                        transformations(CircleCropTransformation())
                                                    }),
                    modifier  = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.surface)
                        .then(modifier)
                    ,
                    contentDescription = "Movie Poster")
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = movie.title,
                    style = MaterialTheme.typography.h6)
                Text(text = "Rating: ${movie.voteAverage}",
                    style = MaterialTheme.typography.caption)
                Text(text = "Released Date: ${movie.releaseDate}",
                    style = MaterialTheme.typography.caption)

                AnimatedVisibility(visible = expanded) {
                    Column {
                        Divider(modifier = Modifier.padding(1.dp))
                        Text(text = "Overview: ${movie.overview}", style = MaterialTheme.typography.caption)
                        Text(text = "Tagline: ${movie.tagline}", style = MaterialTheme.typography.caption)

                    }
                }

                Icon(imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Down Arrow",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    tint = Color.DarkGray)
            }
        }
    }
}
