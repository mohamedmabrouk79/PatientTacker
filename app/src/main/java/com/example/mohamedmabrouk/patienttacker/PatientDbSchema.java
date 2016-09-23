package com.example.mohamedmabrouk.patienttacker;

/**
 * Created by Mohamed Mabrouk on 15/04/2016.
 */
public class PatientDbSchema {
public static final class PatientTable{
    public static final String NAME="patients";
    public static final class Cols{
        public static final String UUID="id";
        public static final String PATIENTNAME="name";
        public static final String TEL="tel";
        public static final String EMAIL="email";
        public static final String DISEASE="disease";
        public static final String MEDICATION="medication";
        public static final String COST="cost";
        public static final String DATE="date";
        public static final String TIME="time";
    }
    }
}
