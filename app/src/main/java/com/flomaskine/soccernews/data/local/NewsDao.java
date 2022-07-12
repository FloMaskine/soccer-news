package com.flomaskine.soccernews.data.local;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.flomaskine.soccernews.domain.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news WHERE favorite = :favorite")
    List<News> getFavoriteNews(boolean favorite);

    @Insert
    void insert(News news);

    @Delete
    void delete(News news);

}
