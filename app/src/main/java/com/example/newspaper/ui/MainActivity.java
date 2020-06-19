package com.example.newspaper.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.adapter.NewsAdapter;
import com.example.newspaper.room.NewsTable;
import com.example.newspaper.viewModels.MainActivityViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnNewsListener {

    RecyclerView recyclerView;
    private MainActivityViewModel viewModel;
    private NewsAdapter adapter;
    public static boolean networkStatus;
    private static final String TAG = "MainActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.refresh) {
            recreate();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        networkStatus = isConnected();

        if (!networkStatus)
            Toast.makeText(this, "Unable to connect to network", Toast.LENGTH_LONG).show();

        recyclerView = findViewById(R.id.recyclerView);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.getNews().observe(this, newsModels -> adapter.setData(newsModels));

        initRecyclerView();

    }

    private void initRecyclerView() {
        adapter = new NewsAdapter(MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClick(int position) {

        Intent intent = new Intent(this, NewsView.class);
        intent.putExtra("url", viewModel.getNews().getValue().get(position).getUrl());
        startActivity(intent);
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

