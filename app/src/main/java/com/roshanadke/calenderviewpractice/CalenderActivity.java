package com.roshanadke.calenderviewpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalenderActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private DatabaseHelper databaseHelper;
    private String myDateString;
    private List<EventDay> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        calendarView = findViewById(R.id.calendarView);
        databaseHelper = new DatabaseHelper(this);
        events = new ArrayList<>();
        calendarView.setHeaderColor(R.color.white);
        calendarView.setHeaderLabelColor(R.color.black);
        calendarView.setForwardButtonImage(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        calendarView.setPreviousButtonImage(getResources().getDrawable(R.drawable.ic_chevron_right_black_24dp));

        showData();


        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                myDateString = dateFormat.format(clickedDayCalendar.getTime());
                Toast.makeText(CalenderActivity.this, myDateString, Toast.LENGTH_SHORT).show();

            }
        });

    }

        private void showData() {
            Cursor c = databaseHelper.showData();
            if (c.getCount() != 0) {
                c.moveToFirst();
                do {
                    String date1 = c.getString(c.getColumnIndex("DATE"));

                    setCalenderEvent(date1);

                } while (c.moveToNext());
            }
            calendarView.setEvents(events);
        }

    private void setCalenderEvent(String date1) {
        String strDay, strMonth, strYear;
        strDay = date1.substring(0, 2);
        strMonth = date1.substring(3, 5);
        strYear = date1.substring(6, 10);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, Integer.parseInt(strDay));
        events.add(new EventDay(calendar, R.drawable.ic_lens_green));
    }
}
