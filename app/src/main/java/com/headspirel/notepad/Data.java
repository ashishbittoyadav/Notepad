package com.headspirel.notepad;

/**
 *Data class is the model class for the data*/
public class Data {
    private String note;
    private String date;

    public Data(String note, String date) {
        this.note = note;
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
