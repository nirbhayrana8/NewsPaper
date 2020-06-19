package com.example.newspaper.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newspaper.repositories.DbRepository;
import com.example.newspaper.repositories.WebRepository;
import com.example.newspaper.room.NewsTable;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = "MainActivityViewModel";
    private LiveData<List<NewsTable>> liveData = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        WebRepository webRepo = new WebRepository(application);
        DbRepository dbRepo = new DbRepository(application);
        webRepo.getFromWeb().getValue();
        liveData = dbRepo.getAll();
    }

    public LiveData<List<NewsTable>> getNews() {
        Log.d(TAG, "getNews: started");
        return liveData;
    }

}
