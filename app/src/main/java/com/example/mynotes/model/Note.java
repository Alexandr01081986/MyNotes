package com.example.mynotes.model;

import java.util.Date;

public class Note {

    private Integer mId = 0;
    private String mTheme;
    private String mDescription;
    private final Date mDateCreate;
    private Date mDateChange;
    private Date mDateAlarm;

    public Note(Integer uniqIdNote, String theme, String description) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateCreate = new Date();
        this.mDateChange = new Date();
        this.mId = uniqIdNote;
    }

    public Integer getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTheme() {
        return mTheme;
    }

    public void setTheme(String theme) {
        this.mTheme = theme;
        this.mDateChange = new Date();
    }

    public void setDescription(String description) {
        this.mDescription = description;
        this.mDateChange = new Date();
    }

    public Date getDateCreate() {
        return mDateCreate;
    }

    public Date getDateChange() {
        return mDateChange;
    }

    public void editNote (String theme, String description) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateChange = new Date();
    }

    public Date getDateAlarm() {
        return mDateAlarm;
    }

    public void setDateAlarm(Date mDateAlarm) {
        this.mDateAlarm = mDateAlarm;
    }

}
