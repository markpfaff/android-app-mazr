package com.markpfaff.mazr;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate() Restoring previous state");
        } else {
            Log.d(TAG, "onCreate() No saved state available");
        }

        try {
            if (isConnected() == true) {
                Toast.makeText(MainActivity.this, "You are connected",
                        Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(MainActivity.this, "You are not connected",
                        Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

    }

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



    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        if (view != null) {
            Log.d(TAG, "view passed to sendMessage successfully");

        } else {
            Log.d(TAG, "no view to pass");

        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            imageView.setId(position);
            imageView.setOnClickListener(new BtnOnClickListener(position));
            return imageView;
        }

    }

    //    Called when the user taps an image in the grid
    public void showList(int id){
        if (id==0){
            Intent recycle = new Intent(this, RecyclerActivity.class);
            startActivity(recycle);
        }else if(id==1){
            DialogFragment newFragment = new CustomDialogFragment();
            newFragment.show(getSupportFragmentManager(), "main_dialog");
        }else {
            Toast.makeText(MainActivity.this, "" + id,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // references to images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5

    };

    class BtnOnClickListener implements View.OnClickListener
    {
        private final int position;

        public BtnOnClickListener(int position)
        {
            this.position = position;
        }

        public void onClick(View v)
        {
            Log.d(TAG, "button tapped");
            showList(v.getId());
        }
    }

//    public void confirmFireMissiles() {
//        DialogFragment newFragment = new FireMissilesDialogFragment();
//        newFragment.show(getSupportFragmentManager(), "missiles");
//    }

    public static class CustomDialogFragment extends DialogFragment {
        /** The system calls this to get the DialogFragment's layout, regardless
         of whether it's being displayed as a dialog or an embedded fragment. */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout to use as dialog or embedded fragment
            return inflater.inflate(R.layout.main_dialog, container, false);
        }

        /** The system calls this only when creating the layout in a dialog. */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.main_dialog_title)
                    .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.d(TAG, "user answered yes");
                        }
                    })
                    .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.d(TAG, "user answered no");
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    public boolean isConnected() throws InterruptedException, IOException
    {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }



}
