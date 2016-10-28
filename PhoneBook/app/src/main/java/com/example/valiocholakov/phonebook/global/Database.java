package com.example.valiocholakov.phonebook.global;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.valiocholakov.phonebook.models.Contact;
import com.example.valiocholakov.phonebook.models.Country;

import java.util.ArrayList;

import static com.example.valiocholakov.phonebook.global.Constants.GENDER_FEMALE;
import static com.example.valiocholakov.phonebook.global.Constants.GENDER_MALE;

/**
 * Created by valiocholakov on 10/18/16.
 */

public class Database extends SQLiteOpenHelper {
    private static final int    DATABASE_VERSION = 2;
    private static final String DATABASE_NAME    = "DATABASE";

    private static final String TABLE_CONTACTS  = "CONTACTS";
    private static final String TABLE_COUNTRIES = "COUNTRIES";

    private static final String COLUMN_ID           = "ID";
    private static final String COLUMN_FIRST_NAME   = "FIRST_NAME";
    private static final String COLUMN_LAST_NAME    = "LAST_NAME";
    private static final String COLUMN_COUNTRY_ID   = "COUNTRY_ID";
    private static final String COLUMN_PHONE_NUMBER = "PHONE_NUMBER";
    private static final String COLUMN_GENDER       = "GENDER";
    private static final String COLUMN_NAME         = "NAME";
    private static final String COLUMN_CODE         = "CODE";

    private static final String TABLE_CONTACT_COLUMN_ID   = "contactId";
    private static final String TABLE_COUNTRIES_COLUMN_ID = "countryId";

    private Context mContext;

    private static Database database;

    public static Database getInstance(Context context) {
        if (database == null) {
            database = new Database(context);
        }
        return database;
    }

    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable(TABLE_CONTACTS, getTableContactsColumns()));
        db.execSQL(createTable(TABLE_COUNTRIES, getTableCountiesColumns()));

        if (Settings.getInstance(mContext).addCounties()) {
            addCountries(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    // CRUD Methods

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CONTACTS, null, updatedContactValues(contact));
    }

    public ArrayList<Contact> getContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor     = db.rawQuery(String.format("SELECT %s as %s, %s, %s, %s, %s, %s as %s, %s, %s FROM %s INNER JOIN %s ON %s = %s",
                columnFromTable(TABLE_CONTACTS, COLUMN_ID),
                TABLE_CONTACT_COLUMN_ID,
                columnFromTable(TABLE_CONTACTS, COLUMN_FIRST_NAME),
                columnFromTable(TABLE_CONTACTS, COLUMN_LAST_NAME),
                columnFromTable(TABLE_CONTACTS, COLUMN_PHONE_NUMBER),
                columnFromTable(TABLE_CONTACTS, COLUMN_GENDER),
                columnFromTable(TABLE_COUNTRIES, COLUMN_ID),
                TABLE_COUNTRIES_COLUMN_ID,
                columnFromTable(TABLE_COUNTRIES, COLUMN_NAME),
                columnFromTable(TABLE_COUNTRIES, COLUMN_CODE),
                TABLE_CONTACTS,
                TABLE_COUNTRIES,
                columnFromTable(TABLE_CONTACTS, COLUMN_COUNTRY_ID),
                columnFromTable(TABLE_COUNTRIES, COLUMN_ID)),
                null);

        ArrayList<Contact> contacts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                try {
                    Country country = new Country(
                            cursor.getInt(cursor.getColumnIndex(TABLE_COUNTRIES_COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_CODE))
                    );

                    Contact contact = new Contact(
                            cursor.getInt(cursor.getColumnIndex(TABLE_CONTACT_COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER)) == GENDER_MALE ? Contact.Gender.MALE : Contact.Gender.FEMALE,
                            country,
                            cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
                    contacts.add(contact);
                }
                catch (Exception e) {
                    Log.e("exception", "getContacts: ", e);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return contacts;
    }

    public ArrayList<Country> getCounties() {
        SQLiteDatabase db           = this.getReadableDatabase();
        Cursor cursor               = db.rawQuery(getRecords(TABLE_COUNTRIES), null);
        ArrayList<Country> counties = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                try {
                    Country country = new Country(
                            cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_CODE))
                    );
                    counties.add(country);
                }
                catch (Exception e) {
                    Log.e("exception", "getCounties: ", e);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return counties;
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase  db = this.getWritableDatabase();

        db.update(TABLE_CONTACTS, updatedContactValues(contact), COLUMN_ID + "=" + contact.getId(), null);
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACTS, COLUMN_ID + "=" + contact.getId(), null);
    }

    private ContentValues updatedContactValues(Contact contact) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FIRST_NAME,   contact.getFirstName());
        contentValues.put(COLUMN_LAST_NAME,    contact.getLastName());
        contentValues.put(COLUMN_COUNTRY_ID,   contact.getCountry().getId());
        contentValues.put(COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        contentValues.put(COLUMN_GENDER,       contact.getGender() == Contact.Gender.MALE ? GENDER_MALE : GENDER_FEMALE);

        return contentValues;
    }

    private void addCountries(SQLiteDatabase db) {
        for (String country : Constants.COUNTRIES) {
            String[] data = country.split(";");

            if (data.length != 2) {
                continue;
            }

            String name = data[0];
            String code = data[1];

            db.execSQL("INSERT INTO " + TABLE_COUNTRIES + " (" + COLUMN_NAME + ", " + COLUMN_CODE + ") VALUES ('" + name + "', " + Integer.parseInt(code) + ");");
        }
    }

    // HELPERS

    private static String getRecords(String tableName) {
        return "SELECT * FROM " + tableName + ";";
    }

    private static String createTable(String name, String columns) {
        return "CREATE TABLE IF NOT EXISTS " + name + " (" + columns + ");";
    }

    private static String getTableContactsColumns() {
        return COLUMN_ID           + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME   + " TEXT NOT NULL, "  +
                COLUMN_LAST_NAME    + " TEXT NOT NULL, "  +
                COLUMN_COUNTRY_ID   + " INTEGER NOT NULL, "  +
                COLUMN_PHONE_NUMBER + " TEXT NOT NULL, "  +
                COLUMN_GENDER       + " INTEGER NOT NULL";
    }

    private static String getTableCountiesColumns() {
        return COLUMN_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_CODE + " INTEGER NOT NULL";
    }

    private static String columnFromTable(String table, String column) {
        return String.format("%s.%s", table, column);
    }
}
