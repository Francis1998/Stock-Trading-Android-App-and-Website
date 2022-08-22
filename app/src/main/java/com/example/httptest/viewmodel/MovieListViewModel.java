package com.example.httptest.viewmodel;


import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.httptest.model.MovieModel;
import com.example.httptest.network.APIService;
import com.example.httptest.network.RetroInstance;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListViewModel extends ViewModel {

    private MutableLiveData<List<MovieModel>> moviesList;

    public MovieListViewModel(){
        moviesList = new MutableLiveData<>();
    }

    public MutableLiveData<List<MovieModel>> getMoviesListObserver() {
//        Log.d("willget", moviesList);
        return moviesList;
    }

    public void makeApiCall(String query){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<List<MovieModel>> call = apiService.getMovieList(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                Log.d("willresponse", String.valueOf(response.body()));
                moviesList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                moviesList.postValue(null);
            }
        });
    }
}
