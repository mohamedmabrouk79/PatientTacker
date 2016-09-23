package com.example.mohamedmabrouk.patienttacker;

import android.accounts.AccountManager;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.mohamedmabrouk.patienttacker.PatientDbSchema.PatientTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mohamed Mabrouk on 15/04/2016.
 */
public class PatientCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public PatientCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //**************** for get Patient and him details *********//
    public Patient getPatient(){
        String uuid=getString(getColumnIndex(PatientTable.Cols.UUID));
        String name=getString(getColumnIndex(PatientTable.Cols.PATIENTNAME));
        String tel=getString(getColumnIndex(PatientTable.Cols.TEL));
        String e_mail=getString(getColumnIndex(PatientTable.Cols.EMAIL));
        String medication=getString(getColumnIndex(PatientTable.Cols.MEDICATION));
        Long date=getLong(getColumnIndex(PatientTable.Cols.DATE));
        Long time=getLong(getColumnIndex(PatientTable.Cols.TIME));
        String disease=getString(getColumnIndex(PatientTable.Cols.DISEASE));
        String cost=getString(getColumnIndex(PatientTable.Cols.COST));

        Patient patient=new Patient(UUID.fromString(uuid));
        patient.setName(name);
        patient.setTel(tel);
        patient.setE_mail(e_mail);
        patient.setDate(new Date(date));
        patient.setTime(new Date(time));
        patient.setDisease(disease);
        patient.setMedication(medication);
        patient.setCoast(cost);
        return patient;
    }
}
