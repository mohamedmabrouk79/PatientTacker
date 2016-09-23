package com.example.mohamedmabrouk.patienttacker;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mohamed Mabrouk on 09/04/2016.
 */
public class Patient {
    private String  mName;
    private UUID  mId;
    private String mTel;
    private String mE_mail;
    private Date mDate;
    private Date mTime;
    private String mDisease;
    private String mMedication;
    private String mCoast;

    public Patient(){
        this(UUID.randomUUID());

    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
   public Patient(UUID id){
     mId=id;
      mDate=new Date();
       mTime=new Date();
   }
    public Date getTime() {
        return mTime;
    }

    public void setTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getName() {
        return mName;
    }

    public UUID getId() {
        return mId;
    }

    public String getTel() {
        return mTel;
    }

    public String getE_mail() {
        return mE_mail;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDisease() {
        return mDisease;
    }

    public String getMedication() {
        return mMedication;
    }

    public String getCoast() {
        return mCoast;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setTel(String mTel) {
        this.mTel = mTel;
    }

    public void setE_mail(String mE_mail) {
        this.mE_mail = mE_mail;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setDisease(String mDisease) {
        this.mDisease = mDisease;
    }

    public void setMedication(String mMedication) {
        this.mMedication = mMedication;
    }

    public void setCoast(String mCoast) {
        this.mCoast = mCoast;
    }
}
