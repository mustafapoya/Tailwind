package net.golbarg.tailwind.ui.home.detail.docs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import net.golbarg.tailwind.R;
import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableContent;
import net.golbarg.tailwind.model.Content;
import net.golbarg.tailwind.ui.home.detail.content.DetailContentFragment;

public class DetailDocumentationFragment extends Fragment {
    public static final String TAG = DetailContentFragment.class.getName();

    Context context;
    ProgressBar progressLoading;
    WebView webView;
    DatabaseHandler dbHandler;
    private int contentId;

    public DetailDocumentationFragment() {

    }

    public DetailDocumentationFragment(int contentId) {
        this.contentId = contentId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_docs, container, false);
        context = root.getContext();
        dbHandler = new DatabaseHandler(context);
        TableContent tableContent = new TableContent(dbHandler);
        Content content = tableContent.findById(contentId);

        progressLoading = root.findViewById(R.id.progress_loading);
        webView = root.findViewById(R.id.web_browser);
        progressLoading.setVisibility(View.GONE);

        try {
            webView.loadUrl(content.getLinkAddress());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return root;
    }
}
