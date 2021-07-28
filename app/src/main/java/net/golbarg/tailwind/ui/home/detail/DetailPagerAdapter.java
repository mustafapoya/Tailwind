package net.golbarg.tailwind.ui.home.detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DetailPagerAdapter extends FragmentStateAdapter {
    private int numberOfTabs;
    private int contentId;

    public DetailPagerAdapter(@NonNull FragmentActivity fragmentActivity, int numberOfTabs, int contentId) {
        super(fragmentActivity);
        this.numberOfTabs = numberOfTabs;
        this.contentId = contentId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DetailContentFragment(contentId);
            case 1:
                return new DetailDocumentationFragment(contentId);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return numberOfTabs;
    }
}
