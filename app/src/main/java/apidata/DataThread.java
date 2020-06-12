package apidata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.NewsModel;

public class DataThread extends Thread {

    private ArrayList<NewsModel> dataSet = new ArrayList<>();

    public ArrayList<NewsModel> getDataSet() {
        return dataSet;
    }

    @Override
    public void run() {
        URL url;
        HttpURLConnection connection;
        String result = "";

        try {

            url = new URL("https://newsapi.org/v2/top-headlines?" + "country=in&" +
                    "apiKey=cd51dd738b8549c09f35d7fb7faf3923");
            connection = (HttpURLConnection) url.openConnection();
            BufferedReader bs = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String data;

            while ((data = bs.readLine()) != null) {

                result += data + "\n";
            }


            if (!(result == null)) {


                JSONObject object = new JSONObject(result);


                String stories = object.getString("articles");
                JSONArray array = new JSONArray(stories);

                String title, Url, image;

                for (int i = 0; i < array.length(); i++) {

                    JSONObject obj = array.getJSONObject(i);

                    title = obj.getString("title");
                    Url = obj.getString("url");
                    image = obj.getString("urlToImage");

                    if (!title.equals("") && !url.equals("") && !image.equals("")) {

                        dataSet.add(new NewsModel(title, image, Url));
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
