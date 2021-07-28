package net.golbarg.tailwind.ui.about;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import net.golbarg.tailwind.R;

public class AboutFragment extends Fragment {

    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookmark, container, false);
        context = root.getContext();

        return root;
    }
}