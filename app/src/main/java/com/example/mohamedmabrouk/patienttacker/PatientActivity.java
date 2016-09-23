package com.example.mohamedmabrouk.patienttacker;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import java.util.UUID;

public class PatientActivity extends SingleFragmentAcivity {
    public   static final String EXTRA_PATIENT_ID="com.example.mohamedmabrouk.patienttacker.patient_id";

    public static Intent newIntent(Context context, UUID uuid){
        Intent intent=new Intent(context,PatientActivity.class);
        intent.putExtra(EXTRA_PATIENT_ID,uuid);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID PatientId=(UUID)getIntent().getSerializableExtra(EXTRA_PATIENT_ID);
        return  PatientFragment.newInstance(PatientId);
    }
}
