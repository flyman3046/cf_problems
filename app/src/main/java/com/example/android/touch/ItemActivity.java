package com.example.android.touch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FZ on 12/27/2014.
 */
public class ItemActivity extends ActionBarActivity implements OnItemClickListener{
    private ListView listView;
    private List<String> listProbs;
    private List<String> listHref;
    private TextView mTextView;
    private ProgressDialog mProgressDialog;
    private String url = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.problems);
        mTextView = (TextView) findViewById(R.id.problem_num);
        mTextView.setText(intent.getStringExtra("title"));
        url = intent.getStringExtra("href");
        listProbs = new ArrayList<String>();
        listHref = new ArrayList<String>();

        new LoadTest().execute();
        updateAndDisplay();
        listView.setOnItemClickListener(this);
    }

    public void updateAndDisplay(){
        ArrayAdapter<String> mForecastAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_problem,
                R.id.list_problem_textview,
                listProbs
        );
        listView.setAdapter(mForecastAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("Start a new activity", "");
        Intent intent = new Intent(this, ProblemActivity.class);
        intent.putExtra("href", listHref.get(i));
        intent.putExtra("title", listProbs.get(i));
        this.startActivity(intent);
    }

    private class LoadTest extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ItemActivity.this);
            mProgressDialog.setTitle("Codeforces test");
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
                    listProbs.add("Problme " + title);
                    listHref.add(href);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            ItemActivity.this.updateAndDisplay();
            mProgressDialog.dismiss();
        }
    }
}
