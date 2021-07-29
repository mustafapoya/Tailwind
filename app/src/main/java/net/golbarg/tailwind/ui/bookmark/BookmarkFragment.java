package net.golbarg.tailwind.ui.bookmark;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableBookmark;
import net.golbarg.tailwind.model.ContentValue;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment {

    Context context;
    private AdView mAdViewBookmarkScreenBanner;
    ProgressBar progressLoading;
    private ListView listViewContent;
    BookmarkListAdapter bookmarkListAdapter;
    ArrayList<ContentValue> contentValues = new ArrayList<>();
    DatabaseHandler dbHandler;
    TableBookmark tableBookmark;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookmark, container, false);
        context = root.getContext();
        dbHandler = new DatabaseHandler(context);
        tableBookmark = new TableBookmark(dbHandler);

        mAdViewBookmarkScreenBanner = root.findViewById(R.id.adViewScreenBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewBookmarkScreenBanner.loadAd(adRequest);

        progressLoading = root.findViewById(R.id.progress_loading);
        listViewContent = root.findViewById(R.id.list_view_content_value);

        bookmarkListAdapter = new BookmarkListAdapter(getActivity(), contentValues);
        listViewContent.setAdapter(bookmarkListAdapter);

        new FetchBookmarkDataTask().execute();

        return root;
    }

    private class FetchBookmarkDataTask extends AsyncTask<String, String, ArrayList<ContentValue>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<ContentValue> doInBackground(String... params) {
            ArrayList<ContentValue> channelContents = new ArrayList<>();

            try {

                channelContents = tableBookmark.getAll();

                return channelContents;
            }catch(Exception e) {
                e.printStackTrace();
            }

            return channelContents;
        }

        @Override
        protected void onPostExecute(ArrayList<ContentValue> channelContents) {
            super.onPostExecute(channelContents);
            progressLoading.setVisibility(View.GONE);

            contentValues.clear();
            contentValues.addAll(channelContents);
            bookmarkListAdapter.notifyDataSetChanged();
        }
    }
}