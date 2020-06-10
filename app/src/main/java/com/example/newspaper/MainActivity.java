package com.example.newspaper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import Adapter.NewsAdapter;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnNewsListener {

    RecyclerView recyclerView;
    private ArrayList<String> titles, urls, imageUrls;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.refresh:
                recreate();
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        titles = new ArrayList<>();
        urls = new ArrayList<>();
        imageUrls = new ArrayList<>();

        DataDownload task = new DataDownload();
        task.execute("https://newsapi.org/v2/top-headlines?" +"country=in&" +
                "apiKey=cd51dd738b8549c09f35d7fb7faf3923");

    }

    @Override
    public void OnNewsClick(int position) {

        Intent intent = new Intent(this, NewsView.class);
        intent.putExtra("url", urls.get(position));

        startActivity(intent);
    }

    public class DataDownload extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection connection;
            String result = "";

            try {

                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                BufferedReader bs = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String data;
                Instant doInBgS = Instant.now();
                while ((data = bs.readLine()) != null) {

                    result += data + "\n";
                }
                Instant doInBgE = Instant.now();
                Log.i("TAG", "doInBackground: " + Double.toString(Duration.between(doInBgS, doInBgE).toMillis()));
                return result;

            } catch (Exception e) {
                e.printStackTrace();

                new Thread()
                {
                    public void run()
                    {
                        MainActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                Toast.makeText(getApplicationContext(), "Cant Connect!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {


            try {

                if (!(s==null)) {


                    JSONObject object = new JSONObject(s);

                    Instant onPeS =Instant.now();
                    String stories = object.getString("articles");
                    JSONArray array = new JSONArray(stories);

                    String title, url, image;

                    for (int i=0; i<array.length(); i++) {

                        JSONObject obj = array.getJSONObject(i);

                        title = obj.getString("title");
                        url = obj.getString("url");
                        image = obj.getString("urlToImage");

                        if (!title.equals("") && !url.equals("")) {

                            if (!image.equals(""))
                                imageUrls.add(image);

                            titles.add(title);
                            urls.add(url);
                        }

                    }
                    Instant onPeE =Instant.now();
                    Log.i("TAG", "onPostExecute: " + Double.toString(Duration.between(onPeS, onPeE).toMillis()));
                }

            } catch (Exception e) {

            }
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(new NewsAdapter(titles, urls, imageUrls, MainActivity.this));
        }
    }


}

