package com.appsdrome.tmdbapplication.service;

import com.appsdrome.tmdbapplication.model.MovieDbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbService {

    @GET("movie/popular")
    Call<MovieDbResponse> getPopularMovies(@Query("api_key") String api_key);
}
