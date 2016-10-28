package com.example.valiocholakov.phonebook.activities;

import android.os.Bundle;

import com.example.valiocholakov.phonebook.R;
import com.example.valiocholakov.phonebook.global.Constants;
import com.example.valiocholakov.phonebook.global.Database;

public class AddContactActivity extends ContactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_contact);

        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra(Constants.HIDE_BACK, false)) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public void save() {
        if (formIsValid()) {
            Database.getInstance(getApplicationContext()).addContact(contactFromInput());
            super.save();
        }
    }
}
