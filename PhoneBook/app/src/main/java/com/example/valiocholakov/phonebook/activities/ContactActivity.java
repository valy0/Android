package com.example.valiocholakov.phonebook.activities;

/**
 * Created by valiocholakov on 10/18/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.valiocholakov.phonebook.R;
import com.example.valiocholakov.phonebook.global.Constants;
import com.example.valiocholakov.phonebook.models.Contact;
import com.example.valiocholakov.phonebook.models.Country;

public class ContactActivity extends BaseActivity {

    public EditText mFirstNameEditText;
    public EditText mLastNameEditText;
    public EditText mCountryEditText;
    public EditText mCountryCodeEditText;
    public EditText mPhoneNumberEditText;
    public EditText mGenderEditText;
    public CheckBox mMaleCheckBox;
    public CheckBox mFemaleCheckBox;

    private FloatingActionButton mSaveButton;

    private Contact mCurrentContact;
    private Country mSelectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViews();
        addListeners();
        customInit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.COUNTRIES_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mSelectedCountry= data.getParcelableExtra(Constants.COUNTRY);

            mCountryEditText.setText(mSelectedCountry.getName());
            mCountryCodeEditText.setText(String.format("%s", mSelectedCountry.getCode()));
            mPhoneNumberEditText.requestFocus();

            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.showSoftInput(mPhoneNumberEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    // PUBLIC METHODS

    public void setContactDetails(Contact contact) {
        mCurrentContact = contact;
        mSelectedCountry = contact.getCountry();

        mFirstNameEditText.setText(contact.getFirstName());
        mLastNameEditText.setText(contact.getLastName());
        mCountryEditText.setText(contact.getCountry().getName());
        mCountryCodeEditText.setText(String.format("+%s", contact.getCountry().getCode()));
        mPhoneNumberEditText.setText(contact.getPhoneNumber());
        mMaleCheckBox.setChecked(contact.getGender() == Contact.Gender.MALE);
        mFemaleCheckBox.setChecked(contact.getGender() == Contact.Gender.FEMALE);
    }

    public Contact contactFromInput() {
        return new Contact(
                mCurrentContact == null ? 0 : mCurrentContact.getId(),
                String.valueOf(mFirstNameEditText.getText()),
                String.valueOf(mLastNameEditText.getText()),
                mMaleCheckBox.isChecked() ? Contact.Gender.MALE : Contact.Gender.FEMALE,
                mSelectedCountry,
                String.valueOf(mPhoneNumberEditText.getText())
        );
    }

    public boolean formIsValid() {
        if (editTextIsEmpty(mFirstNameEditText)) {
            showToast("Please enter a first name");
            return false;
        }

        if (editTextIsEmpty(mLastNameEditText)) {
            showToast("Please enter a last name");
            return false;
        }

        if (editTextIsEmpty(mCountryCodeEditText)) {
            showToast("Please select a country");
            return false;
        }

        if (editTextIsEmpty(mPhoneNumberEditText)) {
            showToast("Please enter a phone number");
            return false;
        }

        if (!mMaleCheckBox.isChecked() && !mFemaleCheckBox.isChecked()) {
            showToast("Please select gender");
            return false;
        }

        return true;
    }

    public void save() {
        Intent intent = new Intent();
        intent.putExtra(Constants.CONTACT , contactFromInput());
        setResult(RESULT_OK, intent);
        finish();
    }


    // PRIVATE METHODS

    private void setViews() {
        mFirstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        mLastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        mCountryEditText = (EditText) findViewById(R.id.country_edit_text);
        mCountryCodeEditText = (EditText) findViewById(R.id.country_code_edit_text);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
        mGenderEditText = (EditText) findViewById(R.id.gender_edit_text);
        mMaleCheckBox = (CheckBox) findViewById(R.id.male_checkbox);
        mFemaleCheckBox = (CheckBox) findViewById(R.id.female_checkbox);
        mSaveButton = (FloatingActionButton) findViewById(R.id.save_button);
    }

    private void addListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        mCountryEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openCounties();
                }
            }
        });

        addCheckBoxCheckListener(mMaleCheckBox, mFemaleCheckBox);
        addCheckBoxCheckListener(mFemaleCheckBox, mMaleCheckBox);
    }

    private void customInit() {
        mGenderEditText.setInputType(InputType.TYPE_NULL);
        mCountryEditText.setInputType(InputType.TYPE_NULL);
        mCountryCodeEditText.setInputType(InputType.TYPE_NULL);
    }

    private void openCounties() {
        Intent intent = new Intent(ContactActivity.this, CountiesActivity.class);
        startActivityForResult(intent, Constants.COUNTRIES_ACTIVITY_REQUEST_CODE);
    }

    private void addCheckBoxCheckListener(CheckBox clickedCheckBox, final CheckBox affectedCheckBox) {
        clickedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                affectedCheckBox.setChecked(!isChecked);
            }
        });
    }

    // HELPERS

    private boolean editTextIsEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
