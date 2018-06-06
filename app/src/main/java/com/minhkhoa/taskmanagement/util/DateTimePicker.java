package com.minhkhoa.taskmanagement.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by thaim on 22/11/2017.
 */

public class DateTimePicker {
    public static void pickDate(Activity context, final Calendar calendar, final TextView txtDeadline, final SimpleDateFormat sdf) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DATE, dayOfMonth);

                txtDeadline.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
}
