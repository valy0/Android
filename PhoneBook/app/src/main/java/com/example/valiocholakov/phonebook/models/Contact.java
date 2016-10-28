package com.example.valiocholakov.phonebook.models;

import android.os.Parcel;
import android.os.Parcelable;

import static com.example.valiocholakov.phonebook.global.Constants.GENDER_FEMALE;
import static com.example.valiocholakov.phonebook.global.Constants.GENDER_MALE;

/**
 * Created by valiocholakov on 10/18/16.
 */

public class Contact implements Parcelable {
    private int mId;
    private String mFirstName;
    private String mLastName;
    private Gender mGender;
    private String mPhoneNumber;
    private Country mCountry;

    public enum Gender {
        MALE,
        FEMALE,
    }

    public Contact(int id,
                   String firstName,
                   String lastName,
                   Gender gender,
                   Country country,
                   String phoneNumber) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mGender = gender;
        mCountry = country;
        mPhoneNumber = phoneNumber;
    }

    protected Contact(Parcel in) {
        mId = in.readInt();
        mFirstName = in.readString();
        mLastName = in.readString();
        mCountry = in.readParcelable(Country.class.getClassLoader());
        mPhoneNumber = in.readString();
        mGender = in.readInt() == GENDER_MALE ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mFirstName);
        dest.writeString(mLastName);
        dest.writeParcelable(mCountry, flags);
        dest.writeString(mPhoneNumber);
        dest.writeInt(mGender == Gender.MALE ? GENDER_MALE : GENDER_FEMALE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getId() {
        return mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public Gender getGender() {
        return mGender;
    }

    public Country getCountry() {
        return mCountry;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public String getFormattedPhoneNumber() {
        return "+" + mCountry.getCode() + " " + mPhoneNumber;
    }

    public void update(Contact contact) {
        mFirstName = contact.getFirstName();
        mLastName = contact.getLastName();
        mCountry = contact.getCountry();
        mPhoneNumber = contact.getPhoneNumber();
        mGender = contact.getGender();
    }
}
