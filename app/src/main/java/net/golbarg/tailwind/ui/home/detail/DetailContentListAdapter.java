package net.golbarg.tailwind.ui.home.detail;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.interstitial.InterstitialAd;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.model.ContentValue;
import net.golbarg.tailwind.ui.home.content.ContentListAdapter;

import java.util.ArrayList;

public class DetailContentListAdapter extends ArrayAdapter<ContentValue> {
    public static final String TAG = ContentListAdapter.class.getName();

    private final Activity context;
    private final ArrayList<ContentValue> contentValues;
    private InterstitialAd mInterstitialAd;
    DatabaseHandler handler;

    public DetailContentListAdapter(Activity context, ArrayList<ContentValue> contentValues) {
        super(context, R.layout.custom_list_content_value, contentValues);
        this.context = context;
        this.contentValues = contentValues;
        this.handler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list_content_value, null,true);

        TextView txtKey = rowView.findViewById(R.id.txt_key);
        txtKey.setText(contentValues.get(position).getKey());

        TextView txtValue = rowView.findViewById(R.id.txt_value);
        txtValue.setText(contentValues.get(position).getValue());

        return rowView;
    }
}