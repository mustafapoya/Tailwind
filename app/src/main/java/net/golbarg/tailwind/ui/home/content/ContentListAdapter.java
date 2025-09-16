package net.golbarg.tailwind.ui.home.content;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.interstitial.InterstitialAd;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.model.Content;

import java.util.ArrayList;

public class ContentListAdapter extends ArrayAdapter<Content> {
    public static final String TAG = ContentListAdapter.class.getName();

    private final Activity context;
    private final ArrayList<Content> contents;
    private InterstitialAd mInterstitialAd;
    DatabaseHandler handler;

    public ContentListAdapter(Activity context, ArrayList<Content> contents) {
        super(context, R.layout.custom_list_content, contents);
        this.context = context;
        this.contents = contents;
        this.handler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list_content, null,true);

        TextView txtNumber = rowView.findViewById(R.id.txt_number);
        txtNumber.setText(String.valueOf(contents.get(position).getId()));

        TextView txtTitle = rowView.findViewById(R.id.txt_title);
        txtTitle.setText(contents.get(position).getTitle());

        TextView txtDescription = rowView.findViewById(R.id.txt_description);
        txtDescription.setText(contents.get(position).getDescription());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("contentId", contents.get(position).getId());
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_navigation_content_to_navigation_content_detail, args);
            }
        });

        return rowView;
    }

}