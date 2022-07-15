package com.flomaskine.soccernews.ui.news;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flomaskine.soccernews.data.SoccerNewsRepository;
import com.flomaskine.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;

public class NewsViewModel extends ViewModel {

    public enum State {
        DOING,
        DONE,
        ERROR
    }


    private final MutableLiveData<List<News>> news = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();


    public NewsViewModel() {


        loadNewsFromGitHub();

    }

    public void loadNewsFromGitHub() {
        state.setValue(State.DOING);
        SoccerNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new retrofit2.Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull retrofit2.Response<List<News>> response) {
                if (response.isSuccessful()) {
                    state.setValue(State.DONE);
                    news.setValue(response.body());

                } else {
                    state.setValue(State.ERROR);
                    Log.e("NewsViewModel", "Error getting data onResponse");

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t) {
                state.setValue(State.ERROR);
                Log.e("NewsViewModel", "Error getting data onFailure", t);
            }
        });
    }

    public MutableLiveData<List<News>> getNews() {
        return this.news;
    }

    public MutableLiveData<State> getState() {
        return this.state;
    }

    public void saveNews(News news) {
        AsyncTask.execute(() ->
                SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }


}