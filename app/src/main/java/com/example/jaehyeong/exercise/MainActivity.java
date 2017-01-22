package com.example.jaehyeong.exercise;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String originallink = "https://api.github.com/repos/JakeWharton/DiskLruCache/issues";
    ArrayList<Labels> labels;

    RecyclerView mRecyclerView;

    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labels = new ArrayList<Labels>();

        new RetrieveObjects().execute();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        itemAdapter = new ItemAdapter(this);
        mRecyclerView.setAdapter(itemAdapter);


    }

    private class RetrieveObjects extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            Document doc = null;
            String json = "{}";
            try {
                json = Jsoup.connect(originallink).ignoreContentType(true).execute().body();

                System.out.println("Link HTML: " + json);

            } catch (IOException e) {
                doc = Jsoup.parse("");

                Log.e("MainActivity", "Error in url retrieve", e);
            }
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray rootObj= parser.parse(json).getAsJsonArray();
            for(int i = 0; i < rootObj.size(); ++i) {
                System.out.println(rootObj.get(i).toString());
                JsonObject object = rootObj.get(i).getAsJsonObject();
                /*
                   String user_avatar;
                    String user_name;
                    String comment_count;
                    String title;
                    int comments;
                 */

                JsonObject user = object.get("user").getAsJsonObject();


                Labels label = new Labels();
                label.comments = Integer.parseInt(object.get("comments").getAsString());
                label.user_avatar = user.get("avatar_url").getAsString() + ".png";
                label.user_name = user.get("login").getAsString();
                label.title = object.get("title").getAsString();
                label.comments = object.get("comments").getAsInt();
                label.issue_number = object.get("number").getAsInt();
                Log.d(this.getClass().toString(), label.user_avatar);
                labels.add(label);

            }


//            JsonObject locObj = rootObj.getAsJsonObject("result")
//                    .getAsJsonObject("geometry").getAsJsonObject("location");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            itemAdapter.setItems(labels);


        }
    }
}
