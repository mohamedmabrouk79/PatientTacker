package com.example.mohamedmabrouk.patienttacker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mohamed Mabrouk on 16/04/2016.
 */
public class Search_Fragment extends Fragment {
    public static final String h="com.example.mohamedmabrouk.patienttacker.h";
    public static final String data="com.example.mohamedmabrouk.patienttacker.datas";
    public static final String type="com.example.mohamedmabrouk.patienttacker.type";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_DATE1 = 1;
    private EditText name,disease,medical;
    private Button timeArrivalFrom,timeArrivalTo,search,clear;
    private Patient patient=new Patient();
    private Date dateFrome,dateTo;
    private boolean dataFlage,timeFlage;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search_fragment,container,false);
        name=(EditText)view.findViewById(R.id.name_search);
        timeArrivalFrom=(Button)view.findViewById(R.id.arrival_time_from);
        timeArrivalTo=(Button)view.findViewById(R.id.arrival_time_to);
        search=(Button)view.findViewById(R.id.search);
        clear=(Button)view.findViewById(R.id.clear);
        disease=(EditText)view.findViewById(R.id.disease_search);
        medical=(EditText)view.findViewById(R.id.medical_search);

        //*************** action for search button *******************//
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=new ArrayList<String>();
                    Intent intent=new Intent(getActivity(),SearchListActivity.class);
                if (!name.getText().toString().equals("")){
                    list.add(name.getText().toString()+"/");
                }else if (!disease.getText().toString().equals("")){
                    list.add(disease.getText().toString()+"-");
                    Toast.makeText(getActivity(),list.get(0)+"", Toast.LENGTH_SHORT).show();
                }else if (!medical.getText().toString().equals("")){
                    list.add(medical.getText().toString()+"+");
                    Toast.makeText(getActivity(),list.get(0)+"", Toast.LENGTH_SHORT).show();
                }else if (!timeArrivalFrom.getText().toString().equals("") && !timeArrivalTo.getText().toString().equals("")){
                    list.add(timeArrivalFrom.getText().toString()+"*");
                    list.add(timeArrivalTo.getText().toString());
                    dataFlage=true;
                }else{
                    return;
                }
                    intent.putStringArrayListExtra(h, (ArrayList<String>) list);
                    startActivity(intent);



            }
        });
        //**************** action for clear button ************//
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             name.setText(null);
                disease.setText(null);
                medical.setText(null);
            }
        });


        //************ action for data arrival from **************//
        timeArrivalFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                DatePickerFragment datePickerFragment=DatePickerFragment.NewInstence(new Date());
                datePickerFragment.setTargetFragment(Search_Fragment.this,REQUEST_DATE);
                datePickerFragment.show(fragmentManager,DIALOG_DATE);
            }
        });

        //************** action for dat arrival to    ****//
        timeArrivalTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager1=getFragmentManager();
                DatePickerFragment datePickerFragment1=DatePickerFragment.NewInstence(new Date());
                datePickerFragment1.setTargetFragment(Search_Fragment.this,REQUEST_DATE1);
                datePickerFragment1.show(fragmentManager1,DIALOG_DATE);
            }
        });
        return view;
    }

 public void show (View view){
     for (String s:list){
         Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
     }
 }
    ///************** for get date form Date Picker DateFragment *************//
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=Activity.RESULT_OK){
            return;
        }
        if (requestCode==REQUEST_DATE){
            Date date=(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            dateFrome=date;

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            timeArrivalFrom.setText(df.format("yyyy-MM-dd", date));


        }else if(requestCode==REQUEST_DATE1){
            Date date=(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            dateTo = date;
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            timeArrivalTo.setText(df.format("yyyy-MM-dd", date));

        }
    }
}
