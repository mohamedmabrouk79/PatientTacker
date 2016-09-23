package com.example.mohamedmabrouk.patienttacker;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mohamed Mabrouk on 11/04/2016.
 */
public abstract class SingleFragmentAcivity extends AppCompatActivity {
    protected  abstract  Fragment createFragment();
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.patient_activity;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.Fragment_Container);
        if(fragment==null){
            fragment= createFragment();
            fragmentManager.beginTransaction().add(R.id.Fragment_Container,fragment).commit();
        }
    }
}
