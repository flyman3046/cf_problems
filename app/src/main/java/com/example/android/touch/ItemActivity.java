package com.example.android.touch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FZ on 12/27/2014.
 */
public class ItemActivity extends ActionBarActivity {
    private ListView listView;
    private List<String> listProbs;
    private TextView mTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.problems);
        mTextView = (TextView) findViewById(R.id.problem_num);
        mTextView.setText(intent.getStringExtra("title"));
        listProbs = new ArrayList<String>();
        for(int i = 0; i < 20; i++)
            listProbs.add("Test " + i);

        updateAndDisplay();
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
}
