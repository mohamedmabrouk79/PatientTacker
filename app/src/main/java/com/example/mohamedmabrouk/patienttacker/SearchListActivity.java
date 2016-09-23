package com.example.mohamedmabrouk.patienttacker;

import android.support.v4.app.Fragment;

/**
 * Created by Mohamed Mabrouk on 13/05/2016.
 */
public class SearchListActivity extends  SingleFragmentAcivity {
    @Override
    protected Fragment createFragment() {
        return new SearchListView();
    }
}
