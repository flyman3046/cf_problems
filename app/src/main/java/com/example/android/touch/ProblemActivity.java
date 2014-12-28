package com.example.android.touch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by FZ on 12/28/2014.
 */
public class ProblemActivity extends ActionBarActivity {
    private ProgressDialog mProgressDialog;
    private String url = "";
    private TextView mTitle;
    private TextView mContent;
    private String content;
    private String eTitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_view);
        Intent intent = getIntent();
        url = intent.getStringExtra("href");
        mTitle = (TextView) findViewById(R.id.problem_title);
        mContent = (TextView) findViewById(R.id.problem_content);
        new LoadProblem().execute();
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
                Elements element = document
                        .select("body div[id=body] div[class=problem-statement] div[class=title]");
                eTitle = element.first().text().toString();

                Elements problem = document
                        .select("body div[id=body] div[class=problem-statement]");
                content = problem.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            mTitle.setText(eTitle);
            mContent.setText(Html.fromHtml(content.toString()));
            mProgressDialog.dismiss();
        }
    }
}
