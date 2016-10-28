package com.example.valiocholakov.phonebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.valiocholakov.phonebook.R;
import com.example.valiocholakov.phonebook.adapters.ContactsAdapter;
import com.example.valiocholakov.phonebook.global.Constants;
import com.example.valiocholakov.phonebook.global.Database;
import com.example.valiocholakov.phonebook.models.Contact;

import java.util.ArrayList;

public class ContactsActivity extends BaseActivity {

    private ListView mContactsListView;
    private FloatingActionButton mAddButton;

    private ArrayList<Contact> mContactsArrayList;
    private ContactsAdapter mContactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setViews();
        addListeners();
        setContacts();

        if (mContactsArrayList.size() == 0) {
            addContact(true);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.list_contacts) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contacts_list_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contact selectedContact = mContactsArrayList.get(info.position);

        switch (item.getItemId()) {
            case R.id.edit:
                openContactDetails(selectedContact);
                return true;

            case R.id.delete:
                deleteContact(selectedContact);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            Contact contact = data.getParcelableExtra(Constants.CONTACT);

            if (requestCode == Constants.ADD_CONTACT_ACTIVITY_REQUEST_CODE) {
                mContactsArrayList.add(contact);
            }
            else if (requestCode == Constants.EDIT_CONTACT_ACTIVITY_REQUEST_CODE) {
                updateContact(contact);
            }
            else {
                return;
            }

            mContactsAdapter = new ContactsAdapter(ContactsActivity.this, mContactsArrayList);
            mContactsListView.setAdapter(mContactsAdapter);
        }
    }

    private void updateContact(Contact updatedContact) {
        for (Contact contact : mContactsArrayList) {
            if (contact.getId() == updatedContact.getId()) {
                contact.update(updatedContact);
                break;
            }
        }
    }

    private void setViews() {
        mAddButton = (FloatingActionButton) findViewById(R.id.add_button);
        mContactsListView = (ListView) findViewById(R.id.list_contacts);
    }

    private void addListeners() {
        registerForContextMenu(mContactsListView);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(false);
            }
        });

        mContactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openContactDetails(mContactsArrayList.get(position));
            }
        });
    }

    private void setContacts() {
        try {
            mContactsArrayList = Database.getInstance(getApplicationContext()).getContacts();
            mContactsAdapter = new ContactsAdapter(ContactsActivity.this, mContactsArrayList);
            mContactsListView.setAdapter(mContactsAdapter);
        } catch (Exception e) {
            Log.e("exception", "setContacts: ", e);
        }
    }

    private void openContactDetails(Contact contact) {
        Intent intent = new Intent(ContactsActivity.this, ContactDetailsActivity.class);
        intent.putExtra(Constants.CONTACT, contact);
        startActivityForResult(intent, Constants.EDIT_CONTACT_ACTIVITY_REQUEST_CODE);
    }

    private void deleteContact(Contact contact) {
        Database.getInstance(getApplicationContext()).deleteContact(contact);
        mContactsArrayList.remove(contact);
        mContactsListView.setAdapter(mContactsAdapter);
    }

    private void addContact(boolean hideBack) {
        Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
        intent.putExtra(Constants.HIDE_BACK, hideBack);
        startActivityForResult(intent, Constants.ADD_CONTACT_ACTIVITY_REQUEST_CODE);
    }
}
