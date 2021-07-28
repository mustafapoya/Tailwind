package net.golbarg.tailwind.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tailwind_db";

    public DatabaseHandler(Context context) {
        //3rd argument to be passed is CursorFactory instance
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableCategory.createTableQuery());
        db.execSQL(TableContent.createTableQuery());
        db.execSQL(TableContentValues.createTableQuery());
        db.execSQL(TableBookmark.createTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(TableCategory.dropTableQuery());
        db.execSQL(TableContent.dropTableQuery());
        db.execSQL(TableContentValues.dropTableQuery());
        db.execSQL(TableBookmark.dropTableQuery());
        // Create tables again
        onCreate(db);
    }
}
