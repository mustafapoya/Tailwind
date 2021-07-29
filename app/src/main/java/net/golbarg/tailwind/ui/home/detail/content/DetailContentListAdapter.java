package net.golbarg.tailwind.ui.home.detail.content;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableBookmark;
import net.golbarg.tailwind.model.ContentValue;
import net.golbarg.tailwind.ui.home.content.ContentListAdapter;

import java.util.ArrayList;

public class DetailContentListAdapter extends ArrayAdapter<ContentValue> {
    public static final String TAG = ContentListAdapter.class.getName();

    private final Activity context;
    private final ArrayList<ContentValue> contentValues;
    private InterstitialAd mInterstitialAd;
    DatabaseHandler handler;
    TableBookmark tableBookmark;
    public DetailContentListAdapter(Activity context, ArrayList<ContentValue> contentValues) {
        super(context, R.layout.custom_list_content_value, contentValues);
        this.context = context;
        this.contentValues = contentValues;
        this.handler = new DatabaseHandler(context);
        this.tableBookmark = new TableBookmark(handler);
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

        ImageButton btnBookmark = rowView.findViewById(R.id.btn_bookmark);
        ContentValue isBookmarked = tableBookmark.findById(contentValues.get(position).getId());
        if(isBookmarked != null) {
            btnBookmark.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_yes));
        }

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ContentValue isBookmarked = tableBookmark.findById(contentValues.get(position).getId());
                    if(isBookmarked != null) {
                        tableBookmark.delete(contentValues.get(position));
                        Toast.makeText(context, R.string.bookmark_deleted, Toast.LENGTH_SHORT).show();
                        btnBookmark.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_no));
                    } else {
                        tableBookmark.add(contentValues.get(position));
                        Toast.makeText(context, R.string.added_to_bookmark, Toast.LENGTH_SHORT).show();
                        btnBookmark.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_yes));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AdRequest adRequest = new AdRequest.Builder().build();
                /* real ad Unit: ca-app-pub-3540008829614888/3172047261 */
                /* test ad Unit: ca-app-pub-3940256099942544/1033173712 */

                InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                mInterstitialAd = interstitialAd;
                                Log.i("content_ad", "onAdLoaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                Log.i("content_ad", loadAdError.getMessage());
                                mInterstitialAd = null;
                            }
                        });

                if(mInterstitialAd != null) {
                    mInterstitialAd.show(context);
                }
            }
        });

        return rowView;
    }
}