package com.example.mohamedmabrouk.patienttacker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchActivity extends SingleFragmentAcivity {

    @Override
    protected Fragment createFragment() {
        return new Search_Fragment();
    }
}
