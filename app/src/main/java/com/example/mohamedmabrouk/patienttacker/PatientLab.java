package com.example.mohamedmabrouk.patienttacker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.example.mohamedmabrouk.patienttacker.PatientDbSchema.PatientTable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Mohamed Mabrouk on 11/04/2016.
 */
public class PatientLab {

    public static  PatientLab mLAB;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Patient> patients;
    public static PatientLab getLAB(Context context){
        if (mLAB==null){
            mLAB=new PatientLab(context);
        }
        return mLAB;
    }

    //************** for get  photo file *******//
    public File getPhotoFile(Patient patient){
        File file=mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file==null){
            return null;
        }
        return new File(file,patient.getPhotoFilename());
    }
    private PatientLab(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new  PatientBaseHelper(mContext).getWritableDatabase();
    }

    //*****:)******* to delete current patient ******:)****//
    public void deletePatient(int i , UUID uuid){
        patients=getPatients();
        mDatabase.delete(PatientTable.NAME, PatientTable.Cols.UUID+" = ?", new String[]{uuid.toString()});
        patients.remove(i);
    }
    //****:)**** to add new Patient   *****:)**//
    public void addPatient(Patient patient){
        ContentValues contentValues=getContentValues(patient);
        mDatabase.insert(PatientTable.NAME, null, contentValues);
    }

    //****** to return all Patients ********//
    public List<Patient> getPatients(){
        patients=new ArrayList<>();
        PatientCursorWrapper wrapper=queryCrimes(null, null);
        try {
           wrapper.moveToFirst();
            while (!wrapper.isAfterLast()){
                patients.add(wrapper.getPatient());
                wrapper.moveToNext();
            }
        }finally {
     wrapper.close();
        }
        return patients;
    }

    //**************** get search Patient ******************//
   public List<Patient> getsearchs(String name,String name2,int index){
        patients=new ArrayList<>();
       PatientCursorWrapper wrapper=null;
       switch (index){
           case 0:
               wrapper=queryCrimes(PatientTable.Cols.PATIENTNAME+ " = ?", new String[]{name});
               break;
           case 1:
               Cursor  cursor=mDatabase.rawQuery("SELECT * FROM  "+PatientTable.NAME+" WHERE  "+PatientTable.Cols.DATE+" BETWEEN '"+name+"' and '"+name2+"'",null);
            //  wrapper=queryCrimes(PatientTable.Cols.DATE+ " between  ? and "+PatientTable.Cols.DATE+"  ? ",new String[]{name,name2} );
             wrapper=new PatientCursorWrapper(cursor);
               break;
           case 3:
               wrapper=queryCrimes(PatientTable.Cols.DISEASE+ " = ?", new String[]{name});
               break;
           case 4:
               wrapper=queryCrimes(PatientTable.Cols.MEDICATION+ " = ?", new String[]{name});
               break;
       }
        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()){
                patients.add(wrapper.getPatient());
                wrapper.moveToNext();
            }
        }finally {
            wrapper.close();
        }
        return patients;
    }

    //****** to return one Patient by UUid ********//
    public Patient getPatient(UUID Id){
        PatientCursorWrapper wrapper=queryCrimes(PatientTable.Cols.UUID + " = ?", new String[]{Id.toString()});
        try {
            if (wrapper.getCount()==0){
                return null;
            }
            wrapper.moveToFirst();
            return wrapper.getPatient();
        }finally {
           wrapper.close();
        }
    }

    public void updatePatient(Patient patient){
        String uuid=patient.getId().toString();
        ContentValues Values=getContentValues(patient);

        mDatabase.update(PatientTable.NAME,Values,
                PatientTable.Cols.UUID+" = ?",new String[]{uuid}
                );
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());

        return dateFormat.format(date);
    }

    //*********** for insert values into context ***********//
    private static ContentValues getContentValues(Patient patient){
        ContentValues values=new ContentValues();
        values.put(PatientTable.Cols.UUID,patient.getId().toString());
        values.put(PatientTable.Cols.PATIENTNAME,patient.getName());
        values.put(PatientTable.Cols.TEL,patient.getTel());
        values.put(PatientTable.Cols.EMAIL,patient.getE_mail());
        values.put(PatientTable.Cols.DISEASE,patient.getDisease());
        values.put(PatientTable.Cols.MEDICATION,patient.getMedication());
        values.put(PatientTable.Cols.COST,patient.getCoast());
        System.out.println("patient = [" + getDateTime(patient.getDate()) + "]");
        values.put(PatientTable.Cols.DATE,patient.getDate().getTime());
        values.put(PatientTable.Cols.TIME,patient.getTime().getTime());
        return values;
    }


    //************** for write Query   ************//
    private PatientCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PatientTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new PatientCursorWrapper(cursor);
    }
}
