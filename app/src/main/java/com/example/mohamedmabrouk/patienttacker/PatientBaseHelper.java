package com.example.mohamedmabrouk.patienttacker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.mohamedmabrouk.patienttacker.PatientDbSchema.*;

/**
 * Created by Mohamed Mabrouk on 15/04/2016.
 */
public class PatientBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "patientBase.db";

    public PatientBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PatientTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        PatientTable.Cols.UUID + ", " +
                        PatientTable.Cols.PATIENTNAME+", "+
                        PatientTable.Cols.TEL+", "+
                        PatientTable.Cols.EMAIL+", "+
                        PatientTable.Cols.DISEASE+", "+
                        PatientTable.Cols.MEDICATION+", "+
                        PatientTable.Cols.COST+", "+
                        PatientTable.Cols.DATE+", "+
                        PatientTable.Cols.TIME+
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
