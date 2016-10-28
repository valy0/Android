package com.example.valiocholakov.phonebook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.valiocholakov.phonebook.R;
import com.example.valiocholakov.phonebook.adapters.CountiesAdapter;
import com.example.valiocholakov.phonebook.global.Constants;
import com.example.valiocholakov.phonebook.global.Database;
import com.example.valiocholakov.phonebook.models.Country;

import java.util.ArrayList;

public class CountiesActivity extends AppCompatActivity {

    private ListView mCountiesListView;

    private ArrayList<Country> mCountriesArrayList;
    private CountiesAdapter mCountriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counties);

        setViews();
        setCounties();
        addListeners();
    }

    private void setViews() {
        mCountiesListView = (ListView) findViewById(R.id.list_counties);
    }

    private void setCounties() {
        try {
            mCountriesArrayList = Database.getInstance(getApplicationContext()).getCounties();
            mCountriesAdapter = new CountiesAdapter(CountiesActivity.this, mCountriesArrayList);
            mCountiesListView.setAdapter(mCountriesAdapter);
        } catch (Exception e) {
            Log.e("exception", "setContacts: ", e);
        }
    }

    private void addListeners() {
        mCountiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(Constants.COUNTRY , mCountriesArrayList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
