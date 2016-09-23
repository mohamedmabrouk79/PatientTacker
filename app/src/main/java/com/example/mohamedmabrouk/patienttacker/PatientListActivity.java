package com.example.mohamedmabrouk.patienttacker;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Mohamed Mabrouk on 11/04/2016.
 */
public class PatientListActivity extends SingleFragmentAcivity implements PatientListFragment.Callbacks,PatientFragment.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new PatientListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Patient mPatient) {
        if (findViewById(R.id.detail_Fragment_Container) == null) {
            Intent intent = PatientPagerAtivity.newIntent(this, mPatient.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = PatientFragment.newInstance(mPatient.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_Fragment_Container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onPatientUpdated(Patient patient) {
        PatientListFragment listFragment = (PatientListFragment)
                getSupportFragmentManager()
                            .findFragmentById(R.id.Fragment_Container);
        listFragment.UpdateUI();
    }
}
