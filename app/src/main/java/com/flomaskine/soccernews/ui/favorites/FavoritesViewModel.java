package com.flomaskine.soccernews.ui.favorites;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flomaskine.soccernews.data.SoccerNewsRepository;
import com.flomaskine.soccernews.domain.News;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    public FavoritesViewModel() {

    }

    public LiveData<List<News>> loadFavoriteNews() {
        return SoccerNewsRepository.getInstance().getLocalDb().newsDao().getFavoriteNews();
    }

    public void saveNews(News news) {
        AsyncTask.execute(() ->
                SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }


}