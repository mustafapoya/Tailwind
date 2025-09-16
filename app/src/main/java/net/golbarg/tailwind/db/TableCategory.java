package net.golbarg.tailwind.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.golbarg.tailwind.model.Category;

import java.util.ArrayList;

public class TableCategory implements CRUDHandler<Category>{

    public static final String TABLE_NAME = "categories";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String [] ALL_COLUMNS = {KEY_ID, KEY_TITLE};
    private DatabaseHandler dbHandler;

    public TableCategory(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public static String createTableQuery() {
        return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT)", TABLE_NAME, KEY_ID, KEY_TITLE);
    }

    public static String dropTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    @Override
    public void add(Category object) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.insert(TABLE_NAME, null, putValues(object));
        db.close();
    }

    @Override
    public Category findById(int id) {
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
    public ArrayList<Category> getAll() {
        ArrayList<Category> result = new ArrayList<>();
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

    @Override
    public int update(Category object) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.update(TABLE_NAME, putValues(object), KEY_ID + "=?", new String[]{String.valueOf(object.getId())});
    }

    @Override
    public void delete(Category object) {
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
    public Category mapColumn(Cursor cursor) {
        return new Category(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE))
        );
    }
    @Override
    public ContentValues putValues(Category object) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, object.getId());
        values.put(KEY_TITLE, object.getTitle());
        return values;
    }
}
