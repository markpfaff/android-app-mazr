package com.markpfaff.mazr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    private static final String TAG = DisplayMessageActivity.class.getSimpleName();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // add menu items to the action bar
        //getMenuInflater().inflate(R.menu.primary_menu, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.primary_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item

        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_movie_list:
                startActivity(new Intent(this, RecyclerActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate() Restoring previous state");
            /* restore state */
        } else {
            Log.d(TAG, "onCreate() No saved state available");
            /* initialize app */
        }
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView7 = (TextView) findViewById(R.id.textView7);
        textView7.setText(message);



        if (textView7 != null) {
            Log.d(TAG, "textview passed to DisplayMessage successfully");

        } else {
            Log.d(TAG, "no textview to pass");

        }
    }
}
