package com.example.numad22sp_shujunxiao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class WebServiceActivity extends AppCompatActivity {
    private static final String TAG = "WebServiceActivity";

    private TextInputLayout urlText;
    private TextInputLayout inputYear;
    private TextView movieName;
    private TextView contents;
    private ImageView poster;

    private CardView progressCard;
    private ProgressBar progressBar;
    private TextView progressText;

    private Handler mainHandler = new Handler();
    //private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        urlText = findViewById(R.id.input_movie);
        inputYear = findViewById(R.id.input_year);
        movieName = findViewById(R.id.movie_name);
        contents = findViewById(R.id.movie_content);
        poster = findViewById(R.id.poster);
        progressCard = findViewById(R.id.progress_card);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progree_text);
    }

    @SuppressLint("SetTextI18n")
    public void callWebserviceButtonHandler(View view){
        progressCard.setVisibility(View.VISIBLE);
        progressText.setText("Please wait...");
        String year = inputYear.getEditText().getText().toString();
        String text = convertInput(urlText.getEditText().getText().toString());
        SecondThread secondThread = new SecondThread(text, year);
        new Thread(secondThread).start();
    }

    class SecondThread implements Runnable {
        String url_text;
        String year;
        String[] results;

        public SecondThread(String url_text, String year) {
            this.url_text = url_text;
            this.year = year;
            this.results = new String[3];
        }
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                String website = "https://www.omdbapi.com/?i=tt3896198&apikey=4c71b1b2&t=";
                String yearInput = "";
                if(year.length() > 0) {
                    yearInput = "&y=" + year;
                }
                URL url = new URL(website + url_text + yearInput);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true); //retrieve from the server
                conn.connect();

                // Read response.
                InputStream inputStream = conn.getInputStream();
                final String resp = convertStreamToString(inputStream);
                Log.e(TAG, resp);
                JSONObject jObject = new JSONObject(resp);
                String jTitle = jObject.getString("Title");
                String jContent = jObject.getString("Plot");
                String jImg = jObject.getString("Poster");
                Log.d(TAG, "MSG: "+jTitle);
                results[0] = jTitle;
                results[1] = jContent;
                results[2] = jImg;
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(results[2]).getContent());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        movieName = findViewById(R.id.movie_name);
                        movieName.setText(results[0]);
                        contents.setText(results[1]);
                        poster.setImageBitmap(bitmap);
                        poster.setVisibility(View.VISIBLE);
                        progressEnd();
                    }
                });
                return;
            } catch (MalformedURLException e) {
                Log.e(TAG,"MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG,"ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG,"IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG,"JSONException");
                e.printStackTrace();
            } catch (InterruptedException e) {
                Log.e(TAG,"InterruptedException");
                e.printStackTrace();
            }
            results[0] = "Something went wrong";
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    movieName.setText(results[0]);
                    progressEnd();
                }
            });
        }

        @SuppressLint("SetTextI18n")
        private void progressEnd() {
            progressBar.setVisibility(View.GONE);
            progressText.setText("Completed");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressCard.setVisibility(View.GONE);
                }
            }, 3000);
        }
    }

    /**
     * Helper function
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private String convertInput(String s) {
        String[] str = s.trim().split(" ");
        StringBuilder result = new StringBuilder();
        if(str.length == 0) return result.toString();
        for(int i = 0; i < str.length - 1; i++) {
            result.append(str[i]).append("+");
        }
        result.append(str[str.length - 1]);
        return result.toString();
    }
}