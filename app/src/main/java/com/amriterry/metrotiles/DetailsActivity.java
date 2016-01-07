package com.amriterry.metrotiles;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by user on 1/7/2016.
 */
public class DetailsActivity extends ActionBarActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TILES", "Details Activity");

        setContentView(R.layout.activity_details);
    }
}
