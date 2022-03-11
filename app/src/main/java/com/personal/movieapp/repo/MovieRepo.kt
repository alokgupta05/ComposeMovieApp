package com.personal.movieapp.repo

import com.personal.movieapp.model.MovieDetail
import com.personal.movieapp.network.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import util.AppConstant

import java.lang.RuntimeException
import javax.inject.Inject

class MovieDetailsRepo @Inject constructor(private val movieApi: MovieApi) {

    suspend fun fetchMovieDetail(): Flow<Result<MovieDetail>> {
        return flow {
            emit(
                Result.success(
                    movieApi.getMovieDetails(
                        "550",
                        AppConstant.API_KEY
                    )
                )
            )
        }.catch { exception ->
            emit(Result.failure(RuntimeException(exception.message)));
        }
    }

}


