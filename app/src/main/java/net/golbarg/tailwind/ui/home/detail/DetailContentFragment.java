package net.golbarg.tailwind.ui.home.detail;

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
import net.golbarg.tailwind.db.TableContentValues;
import net.golbarg.tailwind.model.ContentValue;

import java.util.ArrayList;

public class DetailContentFragment extends Fragment {
    public static final String TAG = DetailContentFragment.class.getName();

    Context context;
    private AdView mAdViewScreenBanner;
    ProgressBar progressLoading;
    private ListView listViewContentValue;
    DetailContentListAdapter detailContentListAdapter;
    ArrayList<ContentValue> contentArrayList = new ArrayList<>();
    DatabaseHandler dbHandler;
    private int contentId;

    public DetailContentFragment() {

    }

    public DetailContentFragment(int contentId) {
        this.contentId = contentId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_content, container, false);
        context = root.getContext();
        dbHandler = new DatabaseHandler(context);

        mAdViewScreenBanner = root.findViewById(R.id.adViewScreenBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewScreenBanner.loadAd(adRequest);


        progressLoading = root.findViewById(R.id.progress_loading);
        listViewContentValue = root.findViewById(R.id.list_view_content_value);
        detailContentListAdapter = new DetailContentListAdapter(getActivity(), contentArrayList);
        listViewContentValue.setAdapter(detailContentListAdapter);

        try {
            new FetchContentDataTask().execute(String.valueOf(contentId));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return root;
    }

    private class FetchContentDataTask extends AsyncTask<String, String, ArrayList<ContentValue>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<ContentValue> doInBackground(String... params) {
            ArrayList<ContentValue> result = new ArrayList<>();

            try {
                int contentId = Integer.parseInt(params[0]);
                TableContentValues tableContent = new TableContentValues(dbHandler);
                result = tableContent.getContentValuesOf(contentId);

                return result;
            }catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<ContentValue> result) {
            super.onPostExecute(result);
            progressLoading.setVisibility(View.GONE);
            contentArrayList.clear();
            contentArrayList.addAll(result);
            detailContentListAdapter.notifyDataSetChanged();
        }
    }
}
