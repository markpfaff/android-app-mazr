package com.markpfaff.mazr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

//    String[][] movies =
//            {
//                    { "Star Wars VII", "Sci-Fi" },
//                    { "Star Wars: Rogue One", "Sci-Fi" },
//                    { "Arrival", "Sci-Fi" },
//                    { "Lord of the Rings", "Fantasy" },
//                    { "A Dark Song", "Horror" },
//                    { "The Void", "Horror" },
//                    { "We're Still Here", "Horror" },
//                    { "Big Little Lies", "Drama" },
//                    { "American Gods", "Fantasy" },
//                    { "Chewing Gum", "Comedy" },
//                    { "Santa Clarita Diet", "Comedy/Horror" },
//                    { "Game of Thrones", "Fantasy" },
//                    { "Westworld", "Sci-Fi" },
//                    { "VVitch", "Horror" },
//                    { "Suspiria", "Horror" },
//                    { "The Love Witch", "Horror" }
//            };

    String[][] movies = new String[16][2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_recycler);

        final TextView mTxtDisplay = (TextView) findViewById(R.id.json_text);
        String url = "http://markpfaff.com/projects/ad340/mark2.json";
        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recylerViewLayoutManager = new LinearLayoutManager(context);

        // use linear layout
        recylerViewLayoutManager = new LinearLayoutManager(RecyclerActivity.this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        String TAG = null;
        Log.d(TAG, "JSON stuff started");

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                String TAG = null;
                Log.d(TAG, "JSON onResponse started");
                Log.d(TAG, response.toString());

                try {
//                    int total = response.length();
                    String moviesJson = "";

                    for (int i = 0; i < response.length(); i++){
                        JSONObject JO = response.getJSONObject(i);
                        String titleString = JO.getString("title");
                        String categoryString = JO.getString("genre");
                        movies[i][0] = titleString;
                        movies[i][i] = categoryString;
                        moviesJson += titleString + " ";
                        moviesJson += categoryString;

                    }
                    recyclerViewAdapter.notifyDataSetChanged();

                    mTxtDisplay.setText(moviesJson);


                }catch (JSONException e){
                    e.getStackTrace();
                }

    //                    titleText.setText("" + titleString);
    //                    categoryText.setText("" + categoryString);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
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