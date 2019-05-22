package com.example.guessthecelebrity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection connection = null;

            try {
                url = new URL(urls[0]); //converts string to url
                connection = (HttpURLConnection) url.openConnection(); //establishes connection
                InputStream in = connection.getInputStream(); //gets input from url
                InputStreamReader reader = new InputStreamReader(in); //reads input
                int data = reader.read(); //gets single character of stream
                while (data != -1){//-1 denotes end of file
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }finally {
                connection.disconnect();
            }
            return result;
        }
    }

    public void makeGuess(View v){
        String tag = v.getTag().toString();
        Log.d("Celeb", "Button " + tag + " Clicked");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask getHtml = new DownloadTask();
        String result = null;

        try{
            result = getHtml.execute("http://www.posh24.se/kandisar").get();
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("Celeb","Result***" + result);


    }
}


