package com.example.newspaper.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.newspaper.room.NewsDao;
import com.example.newspaper.room.NewsDatabase;
import com.example.newspaper.room.NewsTable;

import java.util.List;

public class DbRepository {
    private static final String TAG = "DbRepository";

    private Application application;
    private NewsDao newsDao;
    private LiveData<List<NewsTable>> allData;

    public DbRepository(Application application) {
        this.application = application;
        NewsDatabase database = NewsDatabase.getInstance(application);
        newsDao = database.newsDao();
    }

    public void insert(List<NewsTable> newsTableList) {
        Log.d(TAG, "insert: called");
        new InsertTask(newsDao).execute(newsTableList);
    }

    public LiveData<List<NewsTable>> getAll() {
        try {
            return new GetTask(newsDao).execute().get();
        } catch (Exception e) { return null; }
    }

    private class InsertTask extends AsyncTask<List<NewsTable>, Void, Void> {

        private NewsDao newsDao;

        public InsertTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(List<NewsTable>... params) {

            newsDao.insert(params[0]);
            return null;
        }
    }

    private class GetTask extends AsyncTask<Void, Void, LiveData<List<NewsTable>>> {

        private NewsDao newsDao;

        public GetTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected LiveData<List<NewsTable>> doInBackground(Void... voids) {

            return newsDao.getAllNews();
        }
    }
}
