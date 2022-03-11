package com.personal.movieapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.movieapp.theme.MovieAppTheme
import com.personal.movieapp.R
import com.personal.movieapp.state.MovieDetailUIState
import com.personal.movieapp.viewmodel.MovieInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MovieDetailInformation()
            }
        }
    }

    @Composable
    fun MovieDetailInformation(movieInfoViewModel: MovieInfoViewModel = viewModel()) {
        Column{
            when (val state = movieInfoViewModel.uiState.collectAsState().value) {
                is MovieDetailUIState.Empty -> Text(
                    text = stringResource(R.string.no_data_available),
                    modifier = Modifier.padding(16.dp)
                )
                is MovieDetailUIState.Loading ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                is MovieDetailUIState.Error -> {
                    Text(
                        text = state.message,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is MovieDetailUIState.Loaded -> DetailsScreen(state.data)
            }
        }

    }

    @Composable
    fun MyApp(content: @Composable () -> Unit) {
        MovieAppTheme {
            content()
        }
    }

    @ExperimentalAnimationApi
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp {
            MovieDetailInformation()
        }
    }
}