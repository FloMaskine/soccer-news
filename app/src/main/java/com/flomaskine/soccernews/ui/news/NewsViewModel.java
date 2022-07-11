package com.flomaskine.soccernews.ui.news;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flomaskine.soccernews.data.remote.SoccerNewsApi;
import com.flomaskine.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news = new MutableLiveData<>();

    private final SoccerNewsApi api;

    public NewsViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://flomaskine.github.io/soccer-news-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(SoccerNewsApi.class);
        loadNews();

    }

    private void loadNews() {
        api.getNews().enqueue(new retrofit2.Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull retrofit2.Response<List<News>> response) {
                if(response.isSuccessful()) {
                    news.setValue(response.body());

                } else {
                    news.setValue(null);
                    //TODO handle error
                    Log.e("NewsViewModel", "Error getting data");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t) {
                news.setValue(null);
                //TODO handle error
                Log.e("NewsViewModel", "Error getting data", t);
            }
        });
    }

    public MutableLiveData<List<News>> getNews() {
        return this.news;
    }
}