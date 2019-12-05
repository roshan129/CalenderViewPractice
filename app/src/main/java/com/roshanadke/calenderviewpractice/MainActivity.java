package com.roshanadke.calenderviewpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.roshanadke.calenderviewpractice.api.RetrofitClient;
import com.roshanadke.calenderviewpractice.model.Absent;
import com.roshanadke.calenderviewpractice.model.CalenderResponse;
import com.roshanadke.calenderviewpractice.model.Holiday;
import com.roshanadke.calenderviewpractice.model.NotCheckout;
import com.roshanadke.calenderviewpractice.model.Pandding;
import com.roshanadke.calenderviewpractice.model.Present;
import com.roshanadke.calenderviewpractice.model.RecordsList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private String myDateString;
    private List<EventDay> events;
    private List<NotCheckout> notcheckList;
    private LinearLayout linearLayout;
    private List<NotCheckout> l2;
    private List<Pandding> l1;

    Button btnSelect, btnShow;
    EditText edt_id;
    TextView txt_pend_no, txt_present_no, txt_not_checked_out;
    String date, d;
    private DatabaseHelper databaseHelper;
    int mYear, mMonth, mDay;
    private ProgressBar progressBar;
    CalenderResponse calenderResponse;
    private String user_id = "2356", calender_date = "2019-11-01", last_date = "2019-11-30", department= "IT Cell";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDay = 30;
        mMonth = 10;
        mYear = 2019;

        linearLayout = findViewById(R.id.legend);

        edt_id = findViewById(R.id.edt_user_id);
        btnSelect = findViewById(R.id.btnSelect);
        txt_pend_no = findViewById(R.id.txt_pending_id);
        txt_not_checked_out = findViewById(R.id.txt_not_checked_out);
        txt_present_no = findViewById(R.id.txt_present_id);
        btnShow = findViewById(R.id.btnShow);
        databaseHelper = new DatabaseHelper(this);
        progressBar = findViewById(R.id.progressBar);
        notcheckList = new ArrayList<>();

        calendarView = findViewById(R.id.calendarView);
        events = new ArrayList<>();
        calendarView.setHeaderColor(R.color.grey);
        calendarView.setHeaderLabelColor(R.color.black);
        calendarView.setForwardButtonImage(getResources().getDrawable(R.drawable.ic_chevron_right_black_24dp));
        calendarView.setPreviousButtonImage(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));


      /*  Calendar calendar123 = Calendar.getInstance();
        calendar123.set(2019, 11, 13);
        events.add(new EventDay(calendar123, null, Color.parseColor("#F44336")));
        Calendar calendar124 = Calendar.getInstance();
        calendar124.set(2019, 11, 11);
        events.add(new EventDay(calendar124, R.drawable.ic_lens_red));
        Calendar calendar125 = Calendar.getInstance();
        calendar125.set(2019, 11, 11);
        events.add(new EventDay(calendar125, null, Color.parseColor("#F44336")));
        calendarView.setEvents(events);*/

        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                events.clear();
                calendarView.setEvents(events);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String s = simpleDateFormat.format(calendarView.getCurrentPageDate().getTime());
                calender_date = s;
                getLastDate(s);
                sendData();
            }
        });

        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                events.clear();
                calendarView.setEvents(events);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String s = simpleDateFormat.format(calendarView.getCurrentPageDate().getTime());
                calender_date = s;
                getLastDate(s);
                sendData();
            }
        });

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                myDateString = dateFormat1.format(clickedDayCalendar.getTime());

                Log.d("tag", "onDayClick: " + myDateString);
                Toast.makeText(MainActivity.this, myDateString, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, DisplayListActivity.class);
                intent.putExtra("date", myDateString);
                intent.putExtra("user_id", user_id);
                startActivity(intent);

            }
        });


        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTempData();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date = dayOfMonth + "-" + month + "-" + year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Date date1 = null;
                        try {
                            date1 = sdf.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date1);
                        d = sdf.format(cal.getTime());
                        Toast.makeText(MainActivity.this, d, Toast.LENGTH_SHORT).show();
                        insertData();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (btnShow.getText().equals("Show Calender")) {
                    btnShow.setText("Cancel");
                    events.clear();
                    calendarView.setEvents(events);
                    showCalender();
                } else if (btnShow.getText().equals("Cancel")) {
                    btnShow.setText("Show Calender");
                    calendarView.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        /*showCalender();*/

    }

    private void sendTempData() {
       /* Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .sendUserId(user_id, calender_date, last_date);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;
                if (response.body() != null) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void sendData() {
        progressBar.setVisibility(View.VISIBLE);
      Call<CalenderResponse> call = RetrofitClient
              .getInstance()
              .getApi()
              .sendUserId(user_id, calender_date, last_date, department);

      call.enqueue(new Callback<CalenderResponse>() {
          @Override
          public void onResponse(Call<CalenderResponse> call, Response<CalenderResponse> response) {
              if (response.body() != null) {
                  List<Present> list = response.body().getPresent();
                  List<Pandding> list1 = response.body().getPandding();
                  List<NotCheckout> list2 = response.body().getNotCheckout();
                  List<Absent> list3 = response.body().getApsent();
                  List<Holiday> list4 = response.body().getHoliday();

                  Log.d("tag", "onResponse: "+ list4);
                  l1 = list1;
                  l2 = list2;
                  setUpPending();

                  setUpDateList(list);
                  setUpNotCheckOutDateList(notcheckList);
                  setUpPendingDateList(list1);
                /*  setUpAbsentDateList(list3);*/
                  setUpHolidayDateList(list4);

                  progressBar.setVisibility(View.GONE);

              }

          }

          @Override
          public void onFailure(Call<CalenderResponse> call, Throwable t) {
              Toast.makeText(MainActivity.this, "No Intenet Connection!", Toast.LENGTH_SHORT).show();
              progressBar.setVisibility(View.GONE);
          }
      });


    }

    private void setUpPending() {

        notcheckList.clear();
        for (NotCheckout notChceck : l2) {
            int flag = 0;
            for (Pandding pend : l1) {
                if (pend != null && notChceck != null) {
                    if (notChceck.getDate().equals(pend.getDate())) {
                        flag = 1;
                    }
                }

            }

            if (flag == 0) {
                notcheckList.add(notChceck);
            }
        }

    }

    private void setUpDateList(List<Present> list) {
        for (Present temp : list) {
            if (temp != null) {
                String d = temp.getDate();
                String strDay, strMonth, strYear;
                strYear = d.substring(0, 4);
                strMonth = d.substring(5, 7);
                strDay = d.substring(8, 10);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, Integer.parseInt(strDay));
                events.add(new EventDay(calendar, R.drawable.ic_lens_green));
            }
        }
        calendarView.setEvents(events);
    }

    private void setUpPendingDateList(List<Pandding> list) {

        for (Pandding temp : list) {
            if (temp != null) {
                String d = temp.getDate();
                String strDay, strMonth, strYear;
                strYear = d.substring(0, 4);
                strMonth = d.substring(5, 7);
                strDay = d.substring(8, 10);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, Integer.parseInt(strDay));

                events.add(new EventDay(calendar, R.drawable.ic_lens_yellow));
            }
        }
        calendarView.setEvents(events);

    }

    private void setUpNotCheckOutDateList(List<NotCheckout> list) {

        for (NotCheckout temp : list) {
            if (temp != null) {
                String d = temp.getDate();
                String strDay, strMonth, strYear;
                strYear = d.substring(0, 4);
                strMonth = d.substring(5, 7);
                strDay = d.substring(8, 10);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, Integer.parseInt(strDay));
                events.add(new EventDay(calendar, R.drawable.ic_lens_purple));
            }
        }
        calendarView.setEvents(events);
    }

    private void setUpAbsentDateList(List<Absent> list) {
        for (Absent temp : list) {
            if (temp != null) {
                String d = temp.getDate();
                String strDay, strMonth, strYear;
                strYear = d.substring(0, 4);
                strMonth = d.substring(5, 7);
                strDay = d.substring(8, 10);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, Integer.parseInt(strDay));

                events.add(new EventDay(calendar, R.drawable.ic_lens_red));
            }
        }
        calendarView.setEvents(events);

    }

    private void setUpHolidayDateList(List<Holiday> list) {
        for (Holiday temp : list) {
            if (temp != null) {
                String d = temp.getHolidayDate();
                String strDay, strMonth, strYear;
                strYear = d.substring(0, 4);
                strMonth = d.substring(5, 7);
                strDay = d.substring(8, 10);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, Integer.parseInt(strDay));

                events.add(new EventDay(calendar, null, Color.parseColor("#F44336")));
            }
        }
        calendarView.setEvents(events);

    }


    private void insertData() {
        boolean result = databaseHelper.insertData(d);
        if (result) {
            Toast.makeText(this, "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
            showCalender();
            btnShow.setText("Cancel");
        }
    }

    private void showCalender() {
        /* showData();*/
        user_id = edt_id.getText().toString();
       /* if (user_id.isEmpty()) {
            user_id = "2356";
        }*/

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpleDateFormat.format(calendarView.getCurrentPageDate().getTime());
        calender_date = s;
        getLastDate(s);
        sendData();
        calendarView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }

    private void getLastDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = null;
        try {
            convertedDate = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(convertedDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        last_date = simpleDateFormat.format(c.getTime());
        Log.d("tag", "showCalender: " + last_date);
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
        calendarView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
