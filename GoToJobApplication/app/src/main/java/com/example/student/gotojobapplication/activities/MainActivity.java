package com.example.student.gotojobapplication.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.student.gotojobapplication.services.APIServices;
import com.example.student.gotojobapplication.adapters.AdapterForRecycler;
import com.example.student.gotojobapplication.utils.DataProvider;
import com.example.student.gotojobapplication.R;
import com.example.student.gotojobapplication.models.ModelForRecycler;
import com.example.student.gotojobapplication.models.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AdapterForRecycler adapterForRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getUsersTwo();
        setRecycler();
    }

    private void setRecycler() {
        adapterForRecycler = new AdapterForRecycler(this);
        final RecyclerView recyclerView = findViewById(R.id.users_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterForRecycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterForRecycler.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterForRecycler.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getUsersTwo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServices service = retrofit.create(APIServices.class);
        service.randomUsers(10).enqueue(new Callback<ModelForRecycler>() {
            @Override
            public void onResponse(Call<ModelForRecycler> call, Response<ModelForRecycler> response) {
                if (response != null && response.isSuccess()) {
                    ModelForRecycler modelForRecycler = response.body();
                    List<Result> result = modelForRecycler.getResults();
                    DataProvider.list.addAll(response.body().getResults());

                } else {
                    Toast.makeText(MainActivity.this, "You Have Problem With Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelForRecycler> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Try load app again", Toast.LENGTH_SHORT).show();
                recreate();
            }
        });
    }


}
