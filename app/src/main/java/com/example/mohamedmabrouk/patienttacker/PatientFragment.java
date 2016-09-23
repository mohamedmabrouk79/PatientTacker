package com.example.mohamedmabrouk.patienttacker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mohamed Mabrouk on 09/04/2016.
 */
public class PatientFragment extends Fragment {

  private Patient mPatient;
    private static final String ARG_PATIENT_ID = "crime_id";
    private EditText Name ,Tel,E_mail,Disease,Mediacl,Cost;
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_Time = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_PHOTO= 2;
    private static final String DIALOG_PHOTO= "DialogPhoto";
    private Button Date,Time;
    public static int Hour=0,Minute=0;
    private static int Args_Time=0;
    public static  int y=0;
    private Button mCall,save;
    private File mPhotoFile;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private Callbacks mCallbacks;
    public interface Callbacks {
        void onPatientUpdated(Patient patient);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID patientId=(UUID) getArguments().getSerializable(ARG_PATIENT_ID);
        mPatient=PatientLab.getLAB(getActivity()).getPatient(patientId);
        setHasOptionsMenu(true);
    }

    //******** create menu *****//
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_patient_delete_list, menu);
    }
    //******* to add action to menu items *********//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_delete_patient:
                UUID patientId=(UUID) getArguments().getSerializable(ARG_PATIENT_ID);
                Patient patient=new Patient();

                List<Patient> patients=PatientLab.getLAB(getActivity()).getPatients();
                for (int i=0 ; i<patients.size(); i++){
                    if (patients.get(i).getId().equals(patientId)){
                        PatientLab.getLAB(getActivity()).deletePatient(i,patientId);
                    }
                }
                y=1;
                Intent  intent=new Intent(getActivity(),PatientListActivity.class);
                startActivity(intent);
                getActivity().finish();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    //************** get time from Time Picker **************//
    public static void setTimes(int hour , int minute){
        Hour=hour;
        Minute=minute;
    }
    //********** create instance from Fragment and set Argument and return this Fragment  *******//
    public static Fragment newInstance(UUID uuid){
            Bundle bundle=new Bundle();
           bundle.putSerializable(ARG_PATIENT_ID, uuid);
        Fragment fragment=new PatientFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    public static int ifdelete(){
        if (y>0){
            return 1;
        }
        return 0;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.patient_fragment,container,false);
        //*********** declaration varible  *************************//
        Name=(EditText)view.findViewById(R.id.Name);
        Tel=(EditText)view.findViewById(R.id.Tel);
        E_mail=(EditText)view.findViewById(R.id.E_mail);
        Date=(Button)view.findViewById(R.id.Date);
        Time=(Button)view.findViewById(R.id.Time);
        updateDate();
        updateTime();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        Date.setText(df.format("yyyy-MM-dd", mPatient.getDate()));
        Disease=(EditText)view.findViewById(R.id.Disease);
        Mediacl=(EditText)view.findViewById(R.id.Medication);
        Cost=(EditText)view.findViewById(R.id.Cost);
        Time.setText(df.format("hh:mm", mPatient.getTime()));
        Name.setText(mPatient.getName());
        Tel.setText(mPatient.getTel());
        E_mail.setText(mPatient.getE_mail());
        Disease.setText(mPatient.getDisease());
        Mediacl.setText(mPatient.getMedication());
        Cost.setText(mPatient.getCoast());
        mCall=(Button)view.findViewById(R.id.call);
        mPhotoFile=PatientLab.getLAB(getActivity()).getPhotoFile(mPatient);
        mPhotoButton=(ImageButton)view.findViewById(R.id.image_Button);
        mPhotoView=(ImageView)view.findViewById(R.id.patient_photo);
        save=(Button)view.findViewById(R.id.save);
        ////****** action for edit View   *********//////
        Tel_action();
        Name_action();
        Disease_action();
        Email_action();
        Medical_action();
        Cost_action();
        //************ for dected if camera app is run ********//
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(getActivity().getPackageManager()) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
        //********* action for show Date Picker inn Fragment *********//
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                DatePickerFragment fragment=DatePickerFragment.NewInstence(new Date());
                fragment.setTargetFragment(PatientFragment.this,REQUEST_DATE);
                fragment.show(fragmentManager, DIALOG_DATE);
            }
        });
        //********* action for show Date Picker inn Fragment *********//
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager=getFragmentManager();
                TimePickerFragment fragment=TimePickerFragment.NewInstence(mPatient.getTime());
                fragment.setTargetFragment(PatientFragment.this,REQUEST_TIME);
                fragment.show(fragmentManager, DIALOG_Time);
            }
        });

        //****************** action for call your patient ***************//
        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + Tel.getText()));

                PackageManager manager=getActivity().getPackageManager();
                if(manager.resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY)!=null){
                   startActivity(intent);
                }
            }
        });
        //************* action for show Photo from Photo view ***********//
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragmentManager=getFragmentManager();
                Bitmap bitmap=PhotoUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
                if (bitmap!=null ) {
                    ShowPhotoFragment showPhotoFragment = new ShowPhotoFragment();
                    showPhotoFragment.ShowPhotoFragment(bitmap);
                    showPhotoFragment.show(fragmentManager,DIALOG_PHOTO);
                }else{
                    Toast.makeText(getActivity(), " :) No photo for Show :)", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updatePhotoView();
        save();
        return view;
    }


    //**************** for save data into data base ***//
    private void save(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInsertData(mPatient)){
                    onPause();
                    PatientLab.getLAB(getActivity()).updatePatient(mPatient);
                    startActivity(new Intent(getActivity(),PatientListActivity.class));
                    getActivity().finish();
                }
            }
        });
    }

    //*** action for enter name *******//
    private void Name_action(){
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mPatient.setName(s.toString());
                updatePatient();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //*** action for enter Tel *******//
    private void Tel_action(){
        Tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPatient.setTel(s.toString());
                updatePatient();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //*** action for enter E_mail*******//
    private void Email_action(){
        E_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPatient.setE_mail(s.toString());
                updatePatient();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //*** action for enter Disease *******//
    private void Disease_action(){
        Disease.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPatient.setDisease(s.toString());
                updatePatient();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //*** action for enter Medication *******//
    private void Medical_action() {
        Mediacl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPatient.setMedication(s.toString());
                updatePatient();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //*** action for enter Cost *******//
    private void Cost_action(){
        Cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPatient.setCoast(s.toString());
                updatePatient();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    //********** for receve Date from Date Picker Fragment *********//
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            android.text.format.DateFormat df = new android.text.format.DateFormat();
       //    Date.setText(df.format("EEE , dd MMMM ,yyyy", mPatient.getDate()));
           mPatient.setDate(date);
            updatePatient();
               updateDate();
        }else if(requestCode==REQUEST_TIME){
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_Time);
            mPatient.setTime(date);
            updatePatient();
            updateTime();
        }else if(requestCode==REQUEST_PHOTO){
            updatePatient();
            updatePhotoView();
        }
    }

//**************** for check vailded   *************//
    private boolean checkInsertData(Patient  mPatient){
      if (Name.getText().toString().equals("")||Tel.getText().toString().equals("")||Cost.getText().toString().equals("")||Date.getText().toString().equals("")||
      Disease.getText().toString().equals("")||Mediacl.getText().toString().equals("")||    E_mail.getText().toString().equals("")){
          Toast.makeText(getActivity(), "Please Complete Insert Data", Toast.LENGTH_SHORT).show();
          return false;
      }
        return true;
    }

    //*********** for update Date *********//
    private void updateDate() {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
      //  Date.setText( mPatient.getDate());
        Date.setText(df.format("yyyy-MM-dd", mPatient.getDate()));
    }

    //**************** for update Time *******//
    private void updateTime(){
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        Time.setText(df.format("hh:mm", mPatient.getTime()));

    }

    //*** for update Patient Date Base  **********//
    @Override
    public void onPause() {
            super.onPause();
            PatientLab.getLAB(getActivity()).updatePatient(mPatient);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    //**************** for update Photo View **************//
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PhotoUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    //************* for update Patient *************//
    private void updatePatient() {
        PatientLab.getLAB(getActivity()).updatePatient(mPatient);
        mCallbacks.onPatientUpdated(mPatient);
    }
}
