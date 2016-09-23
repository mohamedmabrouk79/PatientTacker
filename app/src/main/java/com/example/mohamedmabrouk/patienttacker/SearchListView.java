package com.example.mohamedmabrouk.patienttacker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mohamed Mabrouk on 12/05/2016.
 */
public class SearchListView extends Fragment{
    public static final String h="com.example.mohamedmabrouk.patienttacker.h";
    public static final String type="com.example.mohamedmabrouk.patienttacker.type";
    public static final String data="com.example.mohamedmabrouk.patienttacker.datas";
    private RecyclerView recyclerView;
    private Patient patient;
    List<Patient> patients;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_patient_list, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.patient_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle=getActivity().getIntent().getExtras();

        List<String> list=bundle.getStringArrayList(h);

            patients=getPatients(list);



        SearchAdepter searchAdepter=new SearchAdepter(patients);
        recyclerView.setAdapter(searchAdepter);
        searchAdepter.notifyDataSetChanged();
        return view;
    }


    {


    }
    private List<Patient> getPatients(List<String> list){
        List<Patient> patients=new ArrayList<>();
        String s;
        int i=0;
        for (i= 0 ; i<list.size() ; i++){
               s=list.get(i);

            switch (s.charAt(s.length() - 1)){
                case '/':
                    patients=PatientLab.getLAB(getActivity()).getsearchs(s.substring(0,s.indexOf("/")),null,0);
                    return patients;

                case '-':
                    patients=PatientLab.getLAB(getActivity()).getsearchs(s.substring(0, s.indexOf("-")),null,3);
                    return patients;
                case '+':
                    patients=PatientLab.getLAB(getActivity()).getsearchs(s.substring(0,s.indexOf("+")),null,4);
                    Toast.makeText(getActivity(), s.substring(0,s.indexOf("+")), Toast.LENGTH_SHORT).show();

                    return patients;
                case '*' :
                    patients=PatientLab.getLAB(getActivity()).getsearchs(s.substring(0,s.indexOf("*")),list.get(i+1),1);

                    Toast.makeText(getActivity(), s.substring(0, s.indexOf("*"))+ ":"+list.get(i+1), Toast.LENGTH_SHORT).show();
                    return patients;
            }
        }


        return null;

    }

    private class searchHolder extends RecyclerView.ViewHolder
    {
        private TextView name,diease,date;
        public searchHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Patient  patient=new Patient();
                    Intent intent=PatientPagerAtivity.newIntent(getActivity(),patient.getId());
                    startActivity(intent);
                }
            });
            name=(TextView)itemView.findViewById(R.id.list_item_patient_name_text_view);
            diease=(TextView)itemView.findViewById(R.id.list_item_patient_disease_text_view);
            date=(TextView)itemView.findViewById(R.id.list_item_patient_date_text_view);
        }

        public void putdata(Patient patient){
            name.setText(patient.getName());
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            date.setText((df.format("EEE , dd MMMM ,yyyy", patient.getDate()))+ "     "+df.format("hh:mm", patient.getTime()));
            diease.setText(patient.getDisease());
        }
    }

    private class SearchAdepter extends RecyclerView.Adapter<searchHolder>{
        private List<Patient> patients;

        public SearchAdepter(List<Patient> mPatients){
            this.patients=mPatients;
        }
        @Override
        public searchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view=LayoutInflater.from(getActivity()).inflate(R.layout.list_view_patient,null);

            return  new searchHolder(view);
        }

        @Override
        public void onBindViewHolder(searchHolder holder, int position) {
         Patient patient=patients.get(position);
            holder.putdata(patient);
        }

        @Override
        public int getItemCount() {
            try {
                return patients.size();
            }catch (Exception count){
                return 0;
            }
        }
    }
}
