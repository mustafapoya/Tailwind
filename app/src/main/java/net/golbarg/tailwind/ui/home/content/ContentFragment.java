package net.golbarg.tailwind.ui.home.content;

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
import net.golbarg.tailwind.db.TableContent;
import net.golbarg.tailwind.model.Content;

import java.util.ArrayList;

public class ContentFragment extends Fragment {
    public static final String TAG = ContentFragment.class.getName();

    Context context;
    private AdView mAdViewScreenBanner;
    ProgressBar progressLoading;
    private ListView listViewCategory;
    ContentListAdapter contentListAdapter;
    ArrayList<Content> contentArrayList = new ArrayList<>();
    DatabaseHandler dbHandler;
    private int categoryId;

    public ContentFragment() {

    }

    public ContentFragment(int categoryId) {
        this.categoryId = categoryId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_content, container, false);
        context = root.getContext();
        dbHandler = new DatabaseHandler(context);

        mAdViewScreenBanner = root.findViewById(R.id.adViewScreenBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewScreenBanner.loadAd(adRequest);

        progressLoading = root.findViewById(R.id.progress_loading);
        listViewCategory = root.findViewById(R.id.list_view_content);

        contentListAdapter = new ContentListAdapter(getActivity(), contentArrayList, getParentFragmentManager());
        listViewCategory.setAdapter(contentListAdapter);

        try {
            new FetchContentDataTask().execute(String.valueOf(categoryId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    private class FetchContentDataTask extends AsyncTask<String, String, ArrayList<Content>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Content> doInBackground(String... params) {
            ArrayList<Content> result = new ArrayList<>();

            try {
                int categoryId = Integer.parseInt(params[0]);
                TableContent tableContent = new TableContent(dbHandler);
                result = tableContent.getContents(categoryId);

                return result;
            }catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Content> result) {
            super.onPostExecute(result);
            progressLoading.setVisibility(View.GONE);
            contentArrayList.clear();
            contentArrayList.addAll(result);
            contentListAdapter.notifyDataSetChanged();
        }
    }
}
