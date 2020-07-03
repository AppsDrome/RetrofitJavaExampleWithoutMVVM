package com.appsdrome.tmdbapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;

import com.appsdrome.tmdbapplication.R;
import com.appsdrome.tmdbapplication.adaptor.MovieAdapter;
import com.appsdrome.tmdbapplication.model.Movie;
import com.appsdrome.tmdbapplication.model.MovieDbResponse;
import com.appsdrome.tmdbapplication.service.MovieDbService;
import com.appsdrome.tmdbapplication.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Movie> movieArrayList;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("TMBD Popular Movies");

        getPopularMovies();

        swipeRefreshLayout=findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getPopularMovies();

            }
        });
    }

    private void getPopularMovies() {

        MovieDbService movieDbService= RetrofitInstance.getMovieData();
        Call<MovieDbResponse> call = movieDbService.getPopularMovies(this.getString(R.string.api_key));
        call.enqueue(new Callback<MovieDbResponse>() {
            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {
                MovieDbResponse  movieDbResponse = response.body();

                if(movieDbResponse!=null && movieDbResponse.getMovies()!=null){
                    movieArrayList = (ArrayList<Movie>) movieDbResponse.getMovies();
                    showRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<MovieDbResponse> call, Throwable t) {

            }
        });
    }

    private void showRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvMovies);
        movieAdapter = new MovieAdapter(this, movieArrayList);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {


            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }
}