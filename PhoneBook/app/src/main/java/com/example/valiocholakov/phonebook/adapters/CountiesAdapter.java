package com.example.valiocholakov.phonebook.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.valiocholakov.phonebook.R;
import com.example.valiocholakov.phonebook.models.Country;

import java.util.ArrayList;

/**
 * Created by valiocholakov on 10/19/16.
 */

public class CountiesAdapter extends ArrayAdapter<Country> {
    private Context mContext;
    private ArrayList<Country> mCountriesArrayList;
    private LayoutInflater mLayoutInflater;

    public CountiesAdapter(Context context, ArrayList<Country> countries) {
        super(context, R.layout.item_county, countries);

        this.mContext = context;
        this.mCountriesArrayList = countries;
        this.mLayoutInflater = ((Activity) mContext).getLayoutInflater();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_county, null);
            CountiesAdapter.ViewHolder viewHolder = new CountiesAdapter.ViewHolder();
            viewHolder.mNameTextView = (TextView) convertView.findViewById(R.id.name_text_view);
            viewHolder.mCodeTextView = (TextView) convertView.findViewById(R.id.code_text_view);

            convertView.setTag(viewHolder);
        }

        final CountiesAdapter.ViewHolder holder = (CountiesAdapter.ViewHolder) convertView.getTag();
        final Country countryModel = mCountriesArrayList.get(position);

        holder.mNameTextView.setText(countryModel.getName());
        holder.mCodeTextView.setText(String.format("%s", countryModel.getCode()));

        return convertView;
    }

    public static class ViewHolder {
        public TextView mNameTextView;
        public TextView mCodeTextView;
    }
}
