package com.example.mohamedmabrouk.patienttacker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Mohamed Mabrouk on 11/04/2016.
 */
public class FisrtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.Fragment_Container);
        if(fragment==null){
            fragment= new Enter_Fragment();

            fragmentManager.beginTransaction().add(R.id.Fragment_Container,fragment).commit();
        }
    }

    public void enter(View view){
        startActivity(new Intent(this,PatientListActivity.class));
    }
}

