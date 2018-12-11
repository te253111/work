package com.myproject.repaircar.customview.component;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.myproject.repaircar.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

public class MyDatePickerDialog {
    private Context context;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Listener listener;
    private String dateFormat;

    public interface Listener{
        void onDateSet(Date date,String dateText);
    }

    public static MyDatePickerDialog newInstance(Context context,Listener listener) {
        return new MyDatePickerDialog(context,listener);
    }

    public MyDatePickerDialog(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
        init();
    }

    private void init() {
        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(context,onDatePickerListener,year,month,day);
    }

    public void setDateFormat(String format){
        this.dateFormat = format;
    }

    private final DatePickerDialog.OnDateSetListener onDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String dateText = DateTimeUtils.toFormat(calendar,dateFormat);

            if(listener != null) listener.onDateSet(calendar.getTime(),dateText);
        }
    };

    public void show(){
        datePickerDialog.show();
    }

}
