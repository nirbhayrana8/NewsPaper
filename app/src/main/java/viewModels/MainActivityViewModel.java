package viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import model.NewsModel;
import repository.NewsRepository;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = "MainActivityViewModel";
    private MutableLiveData<List<NewsModel>> mutableLiveData;
    private NewsRepository repository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        Log.d(TAG, "init: started");

        repository = NewsRepository.getInstance();
        mutableLiveData = repository.getResultingNews();

    }

    public LiveData<List<NewsModel>> getNews() {
        Log.d(TAG, "getNews: started");
        return mutableLiveData;
    }
}
