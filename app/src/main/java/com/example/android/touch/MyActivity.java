package com.example.android.touch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity implements OnItemClickListener {

    // URL Address
    String url = "http://codeforces.com/contests";
    ProgressDialog mProgressDialog;
    Button mButton;
    List<String> listTests;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Application", "runs");
        setContentView(R.layout.activity_my);

        // Locate the Buttons in activity_main.xml
        mButton = (Button) findViewById(R.id.refresh);

        // Capture button click
        mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTask
                new Description().execute();
            }
        });


        listTests = new ArrayList<String>();
//        ArrayAdapter<String> mForecastAdapter = new ArrayAdapter<String>(
//                this,
//                R.layout.list_item,
//                R.id.list_item_textview,
//                listTests
//        );
//        ListView listView = (ListView) findViewById(R.id.listview_main);
//        listView.setAdapter(mForecastAdapter);
        listView = (ListView) findViewById(R.id.listview_main);
        listView.setOnItemClickListener(this);
        new Description().execute();
        Log.v("Start a new execution", "");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String s = listTests.get(i);
        Log.v("Start a new activity", "");
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("href", s);
        this.startActivity(intent);
    }


    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MyActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
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
                Elements description = document
                        .select("body div.contests-table tbody tr[data-contestid]");
                // Locate the content attribute
//                System.out.println(description.toString());
                for(int i = 0; i < description.size(); i++) {
                    Element element = description.get(i);
                    String[] title = element.select("td").get(0).toString().split("<br>");
                    String tp = title[0];
                    String[] ss = tp.split("<td>");
                    String tit = ss[1].trim();

                    String href = element.select("a[href]").first().attr("abs:href").toString();
                    listTests.add(tit + " " + href);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            MyActivity.this.updateAndDisplay();
            mProgressDialog.dismiss();
        }
    }
    public void updateAndDisplay(){
        ArrayAdapter<String> mForecastAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item,
                R.id.list_item_textview,
                listTests
        );

        listView.setAdapter(mForecastAdapter);

    }




}
