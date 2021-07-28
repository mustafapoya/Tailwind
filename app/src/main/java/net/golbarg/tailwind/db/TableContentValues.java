package net.golbarg.tailwind.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.golbarg.tailwind.model.Content;
import net.golbarg.tailwind.model.ContentValue;

import java.util.ArrayList;

public class TableContentValues implements CRUDHandler<ContentValue>{

    public static final String TABLE_NAME = "content_values";
    public static final String KEY_ID = "id";
    public static final String KEY_CONTENT_ID = "content_id";
    public static final String KEY_KEY = "key";
    public static final String KEY_VALUE = "value";
    public static final String [] ALL_COLUMNS = {KEY_ID, KEY_CONTENT_ID, KEY_KEY, KEY_VALUE};
    private DatabaseHandler dbHandler;

    public TableContentValues(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public static String createTableQuery() {
        return String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER, %s TEXT, %s TEXT)",
                TABLE_NAME, KEY_ID, KEY_CONTENT_ID, KEY_KEY, KEY_VALUE);
    }

    public static String dropTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    @Override
    public void add(ContentValue object) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.insert(TABLE_NAME, null, putValues(object));
        db.close();
    }

    @Override
    public ContentValue findById(int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            return mapColumn(cursor);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<ContentValue> getAll() {
        ArrayList<ContentValue> result = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                result.add(mapColumn(cursor));
            }while(cursor.moveToNext());
        }
        return result;
    }

    public ArrayList<ContentValue> getContentValuesOf(int contentId) {
        ArrayList<ContentValue> result = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_CONTENT_ID + " = " + contentId;

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                result.add(mapColumn(cursor));
            }while(cursor.moveToNext());
        }
        return result;
    }

    @Override
    public int update(ContentValue object) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.update(TABLE_NAME, putValues(object), KEY_ID + "=?", new String[]{String.valueOf(object.getId())});
    }

    @Override
    public void delete(ContentValue object) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "= ?", new String[]{String.valueOf(object.getId())});
    }

    @Override
    public void emptyTable() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    @Override
    public int getCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @Override
    public ContentValue mapColumn(Cursor cursor) {
        return new ContentValue(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CONTENT_ID))),
                cursor.getString(cursor.getColumnIndex(KEY_KEY)),
                cursor.getString(cursor.getColumnIndex(KEY_VALUE))
        );
    }

    @Override
    public ContentValues putValues(ContentValue object) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, object.getId());
        values.put(KEY_CONTENT_ID, object.getContentId());
        values.put(KEY_KEY, object.getKey());
        values.put(KEY_VALUE, object.getValue());
        return values;
    }
}
