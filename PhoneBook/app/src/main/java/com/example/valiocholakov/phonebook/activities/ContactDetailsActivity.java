package com.example.valiocholakov.phonebook.activities;

import android.os.Bundle;
import android.text.InputType;

import com.example.valiocholakov.phonebook.R;
import com.example.valiocholakov.phonebook.global.Constants;
import com.example.valiocholakov.phonebook.global.Database;
import com.example.valiocholakov.phonebook.models.Contact;

public class ContactDetailsActivity extends ContactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact_details);

        super.onCreate(savedInstanceState);

        disableViews();

        if (getIntent().hasExtra(Constants.CONTACT)) {
            Contact contact = getIntent().getParcelableExtra(Constants.CONTACT);
            setContactDetails(contact);
        }
    }

    private void disableViews() {
        mGenderEditText.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void save() {
        if (formIsValid()) {
            Database.getInstance(getApplicationContext()).updateContact(contactFromInput());
            super.save();
        }
    }
}
