package com.example.mohamedmabrouk.patienttacker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;
import java.util.UUID;

/**
 * Created by Mohamed Mabrouk on 12/04/2016.
 */
public class PatientPagerAtivity extends AppCompatActivity implements PatientFragment.Callbacks {
  private ViewPager viewPager;
    private List<Patient>  mPatients;
    private  static final String EXTRA_PATIENT_ID="com.example.mohamedmabrouk.patienttacker.patient_id";

    public static Intent newIntent(Context context,UUID  uuid){
        Intent intent=new Intent(context,PatientPagerAtivity.class);
        intent.putExtra(EXTRA_PATIENT_ID, uuid);
       return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_pager);
        UUID  patientId=(UUID)getIntent().getSerializableExtra(EXTRA_PATIENT_ID);
        viewPager=(ViewPager)findViewById(R.id.activity_patient_view_pager);
        mPatients=PatientLab.getLAB(this).getPatients();
        final FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
             Patient patient= mPatients.get(position);
                return PatientFragment.newInstance(patient.getId());
            }

            @Override
            public int getCount() {
                return mPatients.size();
            }
        });

        //************ for show selected patient in View Pager  *********//
        for(int i=0 ; i<mPatients.size();i++){
            if(mPatients.get(i).getId().equals(patientId)){
                viewPager.setCurrentItem(i);
                break;
            }

        }
    }


    @Override
    public void onPatientUpdated(Patient patient) {

    }
}
