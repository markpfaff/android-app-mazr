package com.markpfaff.mazr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
/**
 * Created by markp on 4/28/17.
 */

public class RecyclerActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

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

    // array of titles and categories
    String[][] movies =
            {
                    { "Star Wars VII", "Sci-Fi" },
                    { "Star Wars: Rogue One", "Sci-Fi" },
                    { "Arrival", "Sci-Fi" },
                    { "Lord of the Rings", "Fantasy" },
                    { "A Dark Song", "Horror" },
                    { "The Void", "Horror" },
                    { "We're Still Here", "Horror" },
                    { "Big Little Lies", "Drama" },
                    { "American Gods", "Fantasy" },
                    { "Chewing Gum", "Comedy" },
                    { "Santa Clarita Diet", "Comedy/Horror" },
                    { "Game of Thrones", "Fantasy" },
                    { "Westworld", "Sci-Fi" },
                    { "VVitch", "Horror" },
                    { "Suspiria", "Horror" },
                    { "The Love Witch", "Horror" }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_recycler);

        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recylerViewLayoutManager = new LinearLayoutManager(context);

        // use linear layout
        recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView titleText;
            public TextView categoryText;
            public ViewHolder(View v) {
                super(v);
                titleText = (TextView) v.findViewById(R.id.titleView);
                categoryText = (TextView) v.findViewById(R.id.categoryView);
            }
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            // Inflate view
            View item = getLayoutInflater().inflate(R.layout.recycler_items, parent,
                    false);
            
            // return new view holder
            ViewHolder viewh = new ViewHolder(item);
            return viewh;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get title and detail from their position in array
            holder.titleText.setText(movies[position][0]);
            holder.categoryText.setText(movies[position][1]);
        }

        // Return size of array
        @Override
        public int getItemCount() {
            return movies.length;
        }
    }
}