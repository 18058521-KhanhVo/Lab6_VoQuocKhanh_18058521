package com.example.lab6_voquockhanh_18058521;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PersonDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "persons";

    private static final String KEY_NAME = "name";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_persons_table = String.format("CREATE TABLE %s( %s TEXT)", TABLE_NAME, KEY_NAME);
        sqLiteDatabase.execSQL(create_persons_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_persons_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(drop_persons_table);

        onCreate(sqLiteDatabase);
    }

    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Person getStudent(String personName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, KEY_NAME + " = ?", new String[]{String.valueOf(personName)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Person person = new Person(cursor.getString(0));
        return person;
    }

    public List<Person> getAllStudents() {
        List<Person> personList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Person person = new Person(cursor.getString(0));
            personList.add(person);
            cursor.moveToNext();
        }
        return personList;
    }

    public void deletePerson(String personName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + " = ?", new String[]{String.valueOf(personName)});
        db.close();
    }
}
