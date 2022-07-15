package com.flomaskine.soccernews.data;

import androidx.room.Room;

import com.flomaskine.soccernews.App;
import com.flomaskine.soccernews.data.local.SoccerNewsDb;
import com.flomaskine.soccernews.data.remote.SoccerNewsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepository {

    //region Constants
    private static final String REMOTE_API_URL = "https://flomaskine.github.io/soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";
    //endregion

    //region Acesso a API e BD local
    private final SoccerNewsApi remoteApi;
    private final SoccerNewsDb localDb;

    public SoccerNewsApi getRemoteApi() { return remoteApi;  }

    public SoccerNewsDb getLocalDb() { return localDb; }
    //endregion

    // region Singleton: Instancia única de acesso à API e BD local
    private SoccerNewsRepository() {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoccerNewsApi.class);
        localDb = Room.databaseBuilder(App.getInstance(), SoccerNewsDb.class, LOCAL_DB_NAME)
                .build();
    }

    public static SoccerNewsRepository getInstance() { return LazyHolder.INSTANCE; }

    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }

    //endregion


}
