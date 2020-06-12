package model;

public class NewsModel {
    private String titles, imageUrls, urls;

    public NewsModel(String titles, String imageUrls, String urls) {
        this.imageUrls = imageUrls;
        this.titles = titles;
        this.urls = urls;
    }

    public String getUrls() {
        return urls;
    }

    public String getTitles() {
        return titles;
    }

    public String getImageUrls() {
        return imageUrls;
    }
}
