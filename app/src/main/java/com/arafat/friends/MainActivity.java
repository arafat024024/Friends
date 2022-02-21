package com.arafat.friends;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.arafat.friends.adapter.ListAdapter;
import com.arafat.friends.mode.Profile;
import com.arafat.friends.utils.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Profile> profileList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileList = new ArrayList<Profile>();
        recyclerView = (RecyclerView) findViewById(R.id.idRVProfile);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "Loading",
                    "Data Loading..Please Wait", true);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpRequest sh = new HttpRequest();
            // Making a request to url and getting response
            String url = "https://randomuser.me/api/";
            String jsonStr = sh.makeRequest(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting the parent JSON Array
                    JSONArray profiles = jsonObj.getJSONArray("results");

                    // looping through All Profiles
                    for (int i = 0; i < profiles.length(); i++) {
                        JSONObject c = profiles.getJSONObject(i);
                        String portrait= c.getJSONObject("picture").getString("thumbnail");
                        String fullPortrait= c.getJSONObject("picture").getString("large");
                        //fetching the full name from a JSONObject
                        JSONObject nameJson = c.getJSONObject("name");
                        String fullName = nameJson.getString("title")+" "
                                +nameJson.getString("first")+" "+nameJson.getString("last");
                        String street = c.getJSONObject("location").getJSONObject("street").getString("name");
                        String city = c.getJSONObject("location").getString("city");
                        String state = c.getJSONObject("location").getString("state");
                        String country = c.getJSONObject("location").getString("country");

                        String cellPhone = c.getString("cell");
                        String email = c.getString("email");

                        Profile singleProFile = new Profile(portrait,fullPortrait,fullName,street,city,
                                state,country,email,cellPhone);


                        // adding single profile to profile list
                        profileList.add(singleProFile);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            ListAdapter adapter = new ListAdapter(MainActivity.this,profileList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);

            // in below two lines we are setting layoutmanager and adapter to our recycler view.
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}