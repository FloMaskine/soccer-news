package com.flomaskine.soccernews.data.local;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.flomaskine.soccernews.domain.News;

import java.util.List;

@Database(entities = {News.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //Room database
    public abstract NewsDao newsDao();


}
