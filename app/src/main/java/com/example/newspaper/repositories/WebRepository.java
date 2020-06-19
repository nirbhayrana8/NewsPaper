package com.example.newspaper.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newspaper.apidata.RestApi;
import com.example.newspaper.room.NewsTable;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebRepository {
    private static final String TAG = "WebRepository";

    private Application application;

    public WebRepository(Application application) {
        this.application = application;
    }

    List<NewsTable> webServiceResponse = new ArrayList<>();

    public LiveData<List<NewsTable>> getFromWeb() {
        Log.d(TAG, "getFromWeb: something");

        final MutableLiveData<List<NewsTable>> data = new MutableLiveData<>();
        RestApi api = RestApi.retrofit.create(RestApi.class);
        api.getFreshNews("?country=in&apiKey=cd51dd738b8549c09f35d7fb7faf3923")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        webServiceResponse = parseJson(response.body().toString());
                        DbRepository repository = new DbRepository(application);
                        repository.insert(webServiceResponse);
                        data.setValue(webServiceResponse);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                    }
                });

        return data;
    }

    private List<NewsTable> parseJson(String json) {

        List<NewsTable> apiResult = new ArrayList<>();
        JSONObject object;
        JSONArray array;

        try {

            object = new JSONObject(json);

            String articles = object.getString("articles");

            array = new JSONArray(articles);

            for (int i=0; i<array.length(); i++) {

                object = array.getJSONObject(i);

                String title = object.getString("title");
                String url = object.getString("url");
                String imageUrl = object.getString("urlToImage");

                apiResult.add(new NewsTable(title, url, imageUrl));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return apiResult;
    }
}
