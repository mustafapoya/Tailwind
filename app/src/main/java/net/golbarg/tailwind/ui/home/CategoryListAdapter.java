package net.golbarg.tailwind.ui.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableContent;
import net.golbarg.tailwind.model.Category;
import net.golbarg.tailwind.ui.home.content.ContentFragment;

import java.util.ArrayList;

public class CategoryListAdapter extends ArrayAdapter<Category> {
    public static final String TAG = CategoryListAdapter.class.getName();

    private final Activity context;
    private final ArrayList<Category> categories;
    private InterstitialAd mInterstitialAd;
    DatabaseHandler handler;
    TableContent tableContent;
    FragmentManager fragmentManager;

    public CategoryListAdapter(Activity context, ArrayList<Category> categories) {
        super(context, R.layout.custom_list_category, categories);
        this.context = context;
        this.categories = categories;
        this.handler = new DatabaseHandler(context);
        this.tableContent = new TableContent(handler);
    }

    public CategoryListAdapter(Activity context, ArrayList<Category> categories, FragmentManager fragmentManager) {
        super(context, R.layout.custom_list_category, categories);
        this.context = context;
        this.categories = categories;
        this.handler = new DatabaseHandler(context);
        this.tableContent = new TableContent(handler);
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list_category, null,true);

        TextView txtNumber = rowView.findViewById(R.id.txt_number);
        txtNumber.setText(String.valueOf(categories.get(position).getId()));

        TextView txtTitle = rowView.findViewById(R.id.txt_title);
        txtTitle.setText(categories.get(position).getTitle());

        TextView txtContentCount = rowView.findViewById(R.id.txt_content_count);
        txtContentCount.setText(tableContent.getContents(categories.get(position).getId()).size() + " section");

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder().build();
                /* real ad Unit: ca-app-pub-3540008829614888/7986789044 */
                /* test ad Unit: ca-app-pub-3940256099942544/1033173712 */
                InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                mInterstitialAd = interstitialAd;
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mInterstitialAd = null;
                            }
                        });

                if(mInterstitialAd != null) {
                    mInterstitialAd.show(context);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            replaceFragments(categories.get(position).getId());
                        }
                    });
                } else {
                    replaceFragments(categories.get(position).getId());
                }
            }
        });

        return rowView;
    }

    private void replaceFragments(int categoryId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ContentFragment fragment = new ContentFragment(categoryId);
        transaction.addToBackStack("xyz");
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        transaction.commit();
    }

}
