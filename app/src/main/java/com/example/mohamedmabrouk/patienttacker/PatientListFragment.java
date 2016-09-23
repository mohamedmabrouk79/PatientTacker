package com.example.mohamedmabrouk.patienttacker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import javax.security.auth.callback.Callback;

/**
 * Created by Mohamed Mabrouk on 11/04/2016.
 */
public class PatientListFragment extends Fragment {
    private RecyclerView  mRecyclerView;
    private PatientAdepter mAdepter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    public static final String h="com.example.mohamedmabrouk.patienttacker.h";
    public static final int REQUEST_SEARCH=0;
    private Callbacks mCallbacks;
    public interface Callbacks {
        void onCrimeSelected(Patient mPatient);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks=(Callbacks) activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_patient_list,container,false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.patient_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      //**** savedInstanceState !=null meaning you rotate  your phone then savedInstanceState have values (true)  *****//
        if (savedInstanceState !=null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        UpdateUI();
        return  view;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    //********* for save values if you rotate your phone *******//

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
    //*******  create menu **********//
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_patient_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    //*******   for set action to menu item ********//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_patient:
                Patient patient=new Patient();
               PatientLab.getLAB(getActivity()).addPatient(patient);
                UpdateUI();
                mCallbacks.onCrimeSelected(patient);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            case R.id.menu_item_search_patient:
                Intent intent1=new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(intent1,REQUEST_SEARCH);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    //************* on Resume Fragment we will Update thie change in Fragment **********//
    @Override
    public void onResume() {
        super.onResume();
        UpdateUI();
    }

    //*****************  for update Recycle View ***************//
    public void UpdateUI(){
        PatientLab  patientLab=PatientLab.getLAB(getActivity());
        List<Patient>  patients=patientLab.getPatients();
        if (mAdepter==null){
            mAdepter=new PatientAdepter(patients);
            mRecyclerView.setAdapter(mAdepter);
        }else{
            mAdepter.setPatient(patients);
            mAdepter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    //******* create view holder class to fetch view *****************//
    private class PatientHolder extends RecyclerView.ViewHolder {
        private Patient mpatient;
        private TextView mNameView;
        private TextView mDateView;
        private TextView mDiseaseView;
        public PatientHolder(View itemView) {
            super(itemView);
            mNameView=(TextView)itemView.findViewById(R.id.list_item_patient_name_text_view);
            mDateView=(TextView)itemView.findViewById(R.id.list_item_patient_date_text_view);
            mDiseaseView=(TextView)itemView.findViewById(R.id.list_item_patient_disease_text_view);
            //********* action on item view ******//
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mCallbacks.onCrimeSelected(mpatient);
                    Toast.makeText(getActivity(), mpatient.getDate().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        //************ create bind to put values in views ***********//
        public void bindPatient(Patient patient){
            mpatient=patient;
            mNameView.setText(patient.getName());
            android.text.format.DateFormat df = new android.text.format.DateFormat();
          mDateView.setText( (df.format("yyyy-MM-dd", patient.getDate()))+ "     "+df.format("hh:mm", patient.getTime()));
           // mDateView.setText( patient.getDate().toString()+ "     "+df.format("hh:mm", patient.getTime()));
            mDiseaseView.setText(patient.getDisease());
        }
    }

    //****** create Class Adepter to get date and put it in View Holder ***********//
    private class PatientAdepter extends RecyclerView.Adapter<PatientHolder>{
        private List<Patient>  mPatients;
        public  PatientAdepter(List<Patient>  Patients){
            this.mPatients=Patients;
        }
        //************* for create View for Recycle View *******//
        @Override
        public PatientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           LayoutInflater  layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.list_view_patient,parent,false);
            return new PatientHolder(view);
        }

        //*********** get position of this view   **************//
        @Override
        public void onBindViewHolder(PatientHolder holder, int position) {
            Patient Patients=mPatients.get(position);
            holder.bindPatient(Patients);

        }

        @Override
        public int getItemCount() {
            return mPatients.size();
        }

        //**********  swap out the crimes it displays       *********//
        public void setPatient(List<Patient>  patients){
            mPatients=patients;
        }
    }

    //******** to update subtitle (for show number of current patient in your list)  *******//
    private void updateSubtitle(){
        PatientLab patientLab=PatientLab.getLAB(getActivity());
        int patientcount=patientLab.getPatients().size();
        String  subtitle=getString(R.string.subtitle_format, patientcount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (resultCode!= Activity.RESULT_OK){
          return;
      }
        if (requestCode==REQUEST_SEARCH){
            Patient patient;
            List<String> list=data.getStringArrayListExtra(h);
            String taype=list.get(0).substring(0,1);
            Toast.makeText(getActivity(),taype, Toast.LENGTH_SHORT).show();
            try {

            }catch (Exception e){
                Toast.makeText(getActivity(),"No matching data  "+list.size(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
