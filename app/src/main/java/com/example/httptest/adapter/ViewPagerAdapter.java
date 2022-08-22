package com.example.httptest.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.httptest.tabmodel.page1;
import com.example.httptest.tabmodel.page2;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private String query;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String query) {

        super(fragmentActivity);
        this.query = query;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return  new page1(this.query);
            default:
                return  new page2(this.query);
//            default:
//                return  new page3();
        }
    }
    @Override
    public int getItemCount() {return 2; }
}
