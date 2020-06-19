package com.example.newspaper.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NewsTable> news);

    @Query("DELETE FROM news_table")
    void clearDB();

    @Query("SELECT * FROM news_table")
    LiveData<List<NewsTable>> getAllNews();
}
