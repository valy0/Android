package com.example.valiocholakov.phonebook.global;

import java.lang.reflect.Array;

/**
 * Created by valiocholakov on 10/18/16.
 */

public class Constants {

    // ACTIVITY REQUEST CODES
    public static final int COUNTRIES_ACTIVITY_REQUEST_CODE    = 1;
    public static final int ADD_CONTACT_ACTIVITY_REQUEST_CODE  = 2;
    public static final int EDIT_CONTACT_ACTIVITY_REQUEST_CODE = 3;

    // INTENT EXTRAS
    public static final String CONTACT = "contact";
    public static final String COUNTRY = "country";
    public static final String HIDE_BACK = "hideBack";

    // DATABASE
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    // SHARED PREFERENCES
    public static final String ADDED_COUNTRIES = "addedCounties";

    // STATIC DATA
    public static final String[] COUNTRIES = new String[] {
            "Abkhazia;7840",
            "Afghanistan;93",
            "Albania;355",
            "Algeria;213",
            "American Samoa;1684",
            "Belgium;32",
            "Brazil;55",
            "Bulgaria;359",
            "Chile;56",
            "China;86",
            "Cyprus;537",
            "France;33",
            "Germany;49",
            "Greece;30",
            "Haiti;509",
            "Hungary;36",
            "Iceland;354",
            "India;91",
            "Iran;98",
            "Iraq;964",
            "Italy;39",
            "Japan;81",
            "Kenya;254",
            "Kuwait;965",
            "Latvia;371",
            "Luxembourg;352",
            "Malaysia;60",
            "Maldives;960",
            "Malta;356",
            "Mexico;52",
            "Netherlands;31",
            "New Zealand;64",
            "Norway;47",
            "Philippines;63",
            "Poland;48",
            "Portugal;351",
            "Russia;7",
            "Spain;34",
            "Sweden;46",
            "Switzerland;41",
            "Thailand;66",
            "United Kingdom;44",
            "United States;1"
    };
}
