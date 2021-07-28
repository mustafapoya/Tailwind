package net.golbarg.tailwind.ui.home.content;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.interstitial.InterstitialAd;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableContent;
import net.golbarg.tailwind.db.TableContentValues;
import net.golbarg.tailwind.model.Category;
import net.golbarg.tailwind.model.Content;
import net.golbarg.tailwind.model.ContentValue;
import net.golbarg.tailwind.ui.home.detail.DetailFragment;

import java.util.ArrayList;

public class ContentListAdapter extends ArrayAdapter<Content> {
    public static final String TAG = ContentListAdapter.class.getName();

    private final Activity context;
    private final ArrayList<Content> contents;
    private InterstitialAd mInterstitialAd;
    DatabaseHandler handler;
    FragmentManager fragmentManager;

    public ContentListAdapter(Activity context, ArrayList<Content> contents) {
        super(context, R.layout.custom_list_content, contents);
        this.context = context;
        this.contents = contents;
        this.handler = new DatabaseHandler(context);
    }

    public ContentListAdapter(Activity context, ArrayList<Content> contents, FragmentManager fragmentManager) {
        super(context, R.layout.custom_list_content, contents);
        this.context = context;
        this.contents = contents;
        this.handler = new DatabaseHandler(context);
        this.fragmentManager = fragmentManager;
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
                replaceFragments(contents.get(position).getId());
            }
        });

        return rowView;
    }

    private void replaceFragments(int contentId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DetailFragment fragment = new DetailFragment(contentId);
        transaction.addToBackStack("xyz");
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        transaction.commit();
    }
}