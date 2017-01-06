package com.delta.smt.ui.feeder.handle.feederCheckIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.delta.smt.R;

public class FeederCheckInActivity extends AppCompatActivity {
    private static final String TAG = "FeederCheckInActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeder_check_in);
    }
}
