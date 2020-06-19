package com.example.newspaper.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "news_table")
public class NewsTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("urlToImage")
    @Expose
    private String imageUrl;

    @SerializedName("articles")
    @Ignore
    private List<NewsTable> articles;

    public NewsTable(String title, String url, String imageUrl) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
