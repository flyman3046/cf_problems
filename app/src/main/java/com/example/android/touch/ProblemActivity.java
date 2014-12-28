package com.example.android.touch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by FZ on 12/28/2014.
 */
public class ProblemActivity extends ActionBarActivity {
    private ProgressDialog mProgressDialog;
    private String url = "";
    private TextView m1TextView;
    private TextView m2TextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_view);
        Intent intent = getIntent();
        url = intent.getStringExtra("href");
        m1TextView = (TextView) findViewById(R.id.problem_title);
        m2TextView = (TextView) findViewById(R.id.problem_content);

        new LoadProblem().execute();
    }

    public void updateAndDisplay(){
        m1TextView.setText("Title");
        m2TextView.setText("Problem content");
    }

    private class LoadProblem extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ProblemActivity.this);
            mProgressDialog.setTitle("Problem");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements problems = document
                        .select("body div[id=body] div[id=content] div.datatable table[class=problems] td[class=id] a");
                // Locate the content attribute

                for(int i = 0; i < problems.size(); i++){
                    Element element = problems.get(i);
                    String title = element.text().trim();

                    String href = element.attr("abs:href").toString();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            ProblemActivity.this.updateAndDisplay();
            mProgressDialog.dismiss();
        }
    }
}
