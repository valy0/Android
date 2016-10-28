package com.example.valiocholakov.phonebook.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.valiocholakov.phonebook.R;
import com.example.valiocholakov.phonebook.models.Contact;

import java.util.ArrayList;

/**
 * Created by valiocholakov on 10/18/16.
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {
    private Context mContext;
    private ArrayList<Contact> mContactsArrayList;
    private LayoutInflater mLayoutInflater;

    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, R.layout.item_contact, contacts);

        this.mContext = context;
        this.mContactsArrayList = contacts;
        this.mLayoutInflater = ((Activity) mContext).getLayoutInflater();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_contact, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mNameTextView = (TextView) convertView.findViewById(R.id.name_text_view);
            viewHolder.mPhoneNumberTextView = (TextView) convertView.findViewById(R.id.phone_number_text_view);

            convertView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final Contact contactModel = mContactsArrayList.get(position);

        holder.mNameTextView.setText(contactModel.getFullName());
        holder.mPhoneNumberTextView.setText(contactModel.getFormattedPhoneNumber());

        return convertView;
    }

    public static class ViewHolder {
        public TextView mNameTextView;
        public TextView mPhoneNumberTextView;
    }
}
