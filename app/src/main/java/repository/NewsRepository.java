package repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import apidata.DataThread;
import model.NewsModel;

public final class NewsRepository {

    private static NewsRepository instance;
    private ArrayList<NewsModel> dataSet = new ArrayList<>();
    private static final String TAG = "NewsRepository";
    private DataThread thread;

    private static int result = 0;


    public static NewsRepository getInstance() {
        if (instance == null) {
            instance = new NewsRepository();
        }

        Log.d(TAG, "getInstance: started");
        return instance;
    }

    public MutableLiveData<List<NewsModel>> getResultingNews() {

        Log.d(TAG, "getResultingNews: called");
        getData();
        
        MutableLiveData<List<NewsModel>> data = new MutableLiveData<>();

        dataSet = thread.getDataSet();
        data.setValue(dataSet);
        return data;
    }

    private void getData() {
        Log.d(TAG, "getData: call begins");
       thread = new DataThread();
       thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
