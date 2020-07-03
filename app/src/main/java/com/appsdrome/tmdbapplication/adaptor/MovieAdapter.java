package com.appsdrome.tmdbapplication.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsdrome.tmdbapplication.R;
import com.appsdrome.tmdbapplication.model.Movie;
import com.appsdrome.tmdbapplication.view.MovieActivity;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context context;
    private ArrayList<Movie> movieArrayList;

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        holder.movieTitle.setText(movieArrayList.get(position).getOriginalTitle());
        holder.rate.setText(Double.toString(movieArrayList.get(position).getVoteAverage()));

        String imagePath="https://image.tmdb.org/t/p/w500"+movieArrayList.get(position).getPosterPath();

        Glide.with(context)
                .load(imagePath)
                .placeholder(R.drawable.loading)
                .into(holder.movieImage);


    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {


        public TextView movieTitle, rate;
        public ImageView movieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieImage = (ImageView) itemView.findViewById(R.id.ivMovie);
            rate = (TextView) itemView.findViewById(R.id.tvRating);
            movieTitle = (TextView) itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position=getAdapterPosition();

                    if(position!=RecyclerView.NO_POSITION) {

                        Movie selctedMovie = movieArrayList.get(position);
                        Intent intent=new Intent(context, MovieActivity.class);
                        intent.putExtra("movie",selctedMovie);
                        context.startActivity(intent);

                    }
                }
            });

        }
    }
}
