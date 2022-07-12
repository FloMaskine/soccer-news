package com.flomaskine.soccernews.data.local;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.flomaskine.soccernews.domain.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news WHERE favorite = 1")
    List<News> getFavoriteNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(News news);

    @Delete
    void delete(News news);


}
