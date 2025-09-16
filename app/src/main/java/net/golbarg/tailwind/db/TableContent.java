package net.golbarg.tailwind.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.golbarg.tailwind.model.Content;

import java.util.ArrayList;

public class TableContent implements CRUDHandler<Content>{
    public static final String TABLE_NAME = "contents";
    public static final String KEY_ID = "id";
    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_LINK_ADDRESS = "link_address";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TAGS = "tags";
    public static final String [] ALL_COLUMNS = {KEY_ID, KEY_CATEGORY_ID, KEY_TITLE, KEY_LINK_ADDRESS, KEY_DESCRIPTION, KEY_TAGS};

    private DatabaseHandler dbHandler;

    public TableContent(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public static String createTableQuery() {
        return String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, KEY_ID, KEY_CATEGORY_ID, KEY_TITLE, KEY_LINK_ADDRESS, KEY_DESCRIPTION, KEY_TAGS
            );
    }

    public static String dropTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    @Override
    public void add(Content object) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.insert(TABLE_NAME, null, putValues(object));
        db.close();
    }

    @Override
    public Content findById(int id) {
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
    public ArrayList<Content> getAll() {
        ArrayList<Content> result = new ArrayList<>();
        // Select All Query
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

    public ArrayList<Content> getContents(int categoryId) {
        ArrayList<Content> result = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_CATEGORY_ID + " = " + categoryId;

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
    public int update(Content object) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        return db.update(TABLE_NAME, putValues(object), KEY_ID + "=?", new String[]{String.valueOf(object.getId())});
    }

    @Override
    public void delete(Content object) {
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

    @SuppressLint("Range")
    @Override
    public Content mapColumn(Cursor cursor) {
        return new Content(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_ID))),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                cursor.getString(cursor.getColumnIndex(KEY_LINK_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(KEY_TAGS))
        );
    }

    @Override
    public ContentValues putValues(Content object) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, object.getId());
        values.put(KEY_CATEGORY_ID, object.getCategoryId());
        values.put(KEY_TITLE, object.getTitle());
        values.put(KEY_LINK_ADDRESS, object.getLinkAddress());
        values.put(KEY_DESCRIPTION, object.getDescription());
        values.put(KEY_TAGS, object.getTags());
        return values;
    }
}
