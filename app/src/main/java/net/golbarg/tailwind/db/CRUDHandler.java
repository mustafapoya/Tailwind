package net.golbarg.tailwind.db;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public interface CRUDHandler<T> {
    public void add(T object);
    public T findById(int id);
    public ArrayList<T> getAll();
    public int update(T object);
    public void delete(T object);
    public int getCount();
    public T mapColumn(Cursor cursor);
    public ContentValues putValues(T object);
    public void emptyTable();
}
