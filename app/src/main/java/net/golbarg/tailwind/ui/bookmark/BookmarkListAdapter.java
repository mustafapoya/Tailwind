package net.golbarg.tailwind.ui.bookmark;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableBookmark;
import net.golbarg.tailwind.model.ContentValue;

import java.util.ArrayList;

public class BookmarkListAdapter extends ArrayAdapter<ContentValue> {
    private final Activity context;
    private final ArrayList<ContentValue> contentValues;
    DatabaseHandler dbHandler;
    TableBookmark tableBookmark;

    public BookmarkListAdapter(Activity context, ArrayList<ContentValue> contentValues) {
        super(context, R.layout.custom_list_content_value_bookmark, contentValues);
        this.context = context;
        this.contentValues = contentValues;
        dbHandler = new DatabaseHandler(context);
        tableBookmark = new TableBookmark(dbHandler);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list_content_value_bookmark, null,true);


        TextView txtKey = rowView.findViewById(R.id.txt_key);
        txtKey.setText(contentValues.get(position).getKey());

        TextView txtValue = rowView.findViewById(R.id.txt_value);
        txtValue.setText(contentValues.get(position).getValue());

        ImageButton btnDelete = rowView.findViewById(R.id.btn_delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tableBookmark.delete(contentValues.get(position));
                    contentValues.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, R.string.bookmark_deleted, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rowView;
    }

}
