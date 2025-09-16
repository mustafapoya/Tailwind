package net.golbarg.tailwind.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableCategory;
import net.golbarg.tailwind.model.Category;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getName();

    Context context;
    private AdView mAdViewScreenBanner;
    ProgressBar progressLoading;
    private ListView listViewCategory;
    CategoryListAdapter categoryListAdapter;
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    private ConstraintLayout layoutMainLayout;
    DatabaseHandler dbHandler;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = root.getContext();
        dbHandler = new DatabaseHandler(context);

        mAdViewScreenBanner = root.findViewById(R.id.adViewScreenBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewScreenBanner.loadAd(adRequest);

        progressLoading = root.findViewById(R.id.progress_loading);
        listViewCategory = root.findViewById(R.id.list_view_category);
        layoutMainLayout = root.findViewById(R.id.layout_main_layout);

        categoryListAdapter = new CategoryListAdapter(getActivity(), categoryArrayList);
        listViewCategory.setAdapter(categoryListAdapter);

        try {
            new FetchCategoryDataTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    private class FetchCategoryDataTask extends AsyncTask<String, String, ArrayList<Category>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Category> doInBackground(String... params) {
            ArrayList<Category> result = new ArrayList<>();

            try {

                TableCategory tableCategory = new TableCategory(dbHandler);
                result = tableCategory.getAll();

                return result;
            }catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Category> result) {
            super.onPostExecute(result);
            progressLoading.setVisibility(View.GONE);
            categoryArrayList.clear();
            categoryArrayList.addAll(result);
            categoryListAdapter.notifyDataSetChanged();
        }
    }
}