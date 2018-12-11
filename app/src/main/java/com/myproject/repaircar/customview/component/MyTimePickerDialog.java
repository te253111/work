package com.myproject.repaircar.customview.component;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import com.myproject.repaircar.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

public class MyTimePickerDialog {
    private Context context;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private Listener listener;
    private String dateFormat;

    public interface Listener{
        void onTimeSet(Date date, String dateText);
    }

    public static MyTimePickerDialog newInstance(Context context, Listener listener) {
        return new MyTimePickerDialog(context,listener);
    }

    public MyTimePickerDialog(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
        init();
    }

    private void init() {
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(context,onTimePickerDialogListener,hour,minute,true);
    }

    public void setTimeFormat(String format){
        this.dateFormat = format;
    }

    private final TimePickerDialog.OnTimeSetListener onTimePickerDialogListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            String timeText = DateTimeUtils.getTimeText(hour, minute, dateFormat);

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            if(listener != null) listener.onTimeSet(calendar.getTime(),timeText);
        }
    };

    public void show(){
        timePickerDialog.show();
    }

}
