package com.rpjuvvadi.calendardialogfragmentexample.models;

import java.util.Date;

/**
 * Created by rpj on 8/2/16.
 */
public class CalendarGridModel {
    Date date;
    String status;

    public CalendarGridModel(Date date, String status) {
        this.date = date;
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
