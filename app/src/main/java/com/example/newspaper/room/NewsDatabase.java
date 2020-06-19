package com.example.newspaper.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = NewsTable.class, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    private static final String TAG = "NewsDatabase";

    private static NewsDatabase instance;

    public abstract NewsDao newsDao();

    public static synchronized NewsDatabase getInstance(Context context) {
        Log.d(TAG, "getInstance: called");
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NewsDatabase.class, "news_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "Database Created");

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "Database Opened");
        }
    };

}

