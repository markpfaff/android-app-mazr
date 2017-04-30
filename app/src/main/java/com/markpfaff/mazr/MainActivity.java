package com.markpfaff.mazr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

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


}
