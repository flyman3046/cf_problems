package com.example.android.touch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by FZ on 12/27/2014.
 */
public class ItemActivity extends ActionBarActivity {
    private TextView mTextView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view);
        Intent intent = getIntent();
        mTextView = (TextView) findViewById(R.id.problems);
        mTextView.setText(intent.getStringExtra("href"));

    }
}
