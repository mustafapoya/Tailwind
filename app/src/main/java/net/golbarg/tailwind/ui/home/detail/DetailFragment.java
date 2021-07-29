package net.golbarg.tailwind.ui.home.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableContent;
import net.golbarg.tailwind.model.Content;
import net.golbarg.tailwind.model.ContentValue;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    public static final String TAG = DetailFragment.class.getName();
    Context context;
    ProgressBar progressLoading;
    TextView txtTitle;
    TextView txtDescription;

    DatabaseHandler dbHandler;
    ArrayList<ContentValue> contentValues = new ArrayList<>();
    private int contentId;

    public DetailFragment() {
    }

    public DetailFragment(int contentId) {
        this.contentId = contentId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        context = root.getContext();
        dbHandler = new DatabaseHandler(context);
        TableContent tableContent = new TableContent(dbHandler);
        Content content = tableContent.findById(contentId);

        progressLoading = root.findViewById(R.id.progress_loading);
        txtTitle        = root.findViewById(R.id.txt_title);
        txtDescription  = root.findViewById(R.id.txt_description);

        txtTitle.setText(content.getTitle());
        txtDescription.setText(content.getDescription());

        TabLayout tabLayout      = root.findViewById(R.id.tab_layout_details);
        TabItem tabDetails       = root.findViewById(R.id.tab_details);
        TabItem tabDocumentation = root.findViewById(R.id.tab_documentation);

        ViewPager2 viewPager = root.findViewById(R.id.view_pager_container);
        DetailPagerAdapter pagerAdapter = new DetailPagerAdapter(getParentFragment().getActivity(), tabLayout.getTabCount(), contentId);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setUserInputEnabled(false);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.details);
                        tab.setIcon(R.drawable.ic_detail);
                        break;
                    case 1:
                        tab.setText(R.string.docs);
                        tab.setIcon(R.drawable.ic_search);
                        break;
                }
            }
        }
        );
        tabLayoutMediator.attach();

        return root;
    }
}
