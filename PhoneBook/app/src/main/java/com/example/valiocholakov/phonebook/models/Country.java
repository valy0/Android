package com.example.valiocholakov.phonebook.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by valiocholakov on 10/19/16.
 */

public class Country implements Parcelable {
    private int    mId;
    private int    mCode;
    private String mName;

    public Country(int id, String name, int code) {
        mId = id;
        mName = name;
        mCode = code;
    }

    protected Country(Parcel in) {
        mId = in.readInt();
        mCode = in.readInt();
        mName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mCode);
        dest.writeString(mName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public int    getId()   { return mId;   }
    public int    getCode() { return mCode; }
    public String getName() { return mName; }
}
