package com.roshanadke.calenderviewpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.roshanadke.calenderviewpractice.adapter.RecyclerAdapter;
import com.roshanadke.calenderviewpractice.api.RetrofitClient;
import com.roshanadke.calenderviewpractice.model.Event1;
import com.roshanadke.calenderviewpractice.model.RecordsList;
import com.roshanadke.calenderviewpractice.model.Summary;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayListActivity extends AppCompatActivity {

    private static final String TAG = "DisplayListActivity";
    private String user_id = "2356", calender_date;
    private DatabaseHelper databaseHelper;
    ArrayList<HashMap<String, String>> arrayList;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecordsList recordsList;
    private TextView txt_date, txt_event;
    private ProgressBar progressBar;
    private String department = "IT Cell";
    private View view;
    int flag1, flag2;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calender_date = getIntent().getStringExtra("date");
        user_id = getIntent().getStringExtra("user_id");

        cardView = findViewById(R.id.card_event);
        view = findViewById(R.id.view_line2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        txt_date = findViewById(R.id.txt_date1);
        txt_event = findViewById(R.id.txt_event);
        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(this, arrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
        setTitleDate();
        if(!isNetworkAvailable()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Poor Internet Connection!", Toast.LENGTH_SHORT).show();
        }else{

            showRecordList();
        }

    }

    private void setTitleDate() {
        String d = calender_date;
        String m = d.substring(5, 7);
        String d1 = d.substring(8, 10);
        String y = d.substring(0, 4);
        String m1 = getMonth(Integer.parseInt(m));

        String myDate = d1 + "-" + m1 + "-" + y;
        txt_date.setText(myDate);
    }

    private void showRecordList() {
        Call<RecordsList> call = RetrofitClient
                .getInstance()
                .getApi()
                .sendDate(user_id, calender_date, department);
        call.enqueue(new Callback<RecordsList>() {
            @Override
            public void onResponse(Call<RecordsList> call, Response<RecordsList> response) {
                if (response.body() != null) {
                    recordsList = new RecordsList();
                    recordsList.setSummary(response.body().getSummary());
                    recordsList.setNumSummary(response.body().getNumSummary());
                    recordsList.setEvent(response.body().getEvent());

                    setUpEvent();
                    setUpList();

                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<RecordsList> call, Throwable t) {
                Toast.makeText(DisplayListActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void setUpEvent() {
        flag1 = 0;
        List<Event1> object = recordsList.getEvent();
       for(Event1 obj : object){
           if(obj!=null) {
               String str_event = obj.getHolidayName();
               txt_event.setText(str_event);
               txt_event.setVisibility(View.VISIBLE);
               flag1 = 1;
           }
       }
       if(flag1 == 1){
           cardView.setVisibility(View.VISIBLE);
       }

    }

    private void setUpList() {
        flag2= 0;
        List<Summary> lists = recordsList.getSummary();
        for (Summary summary : lists) {

            if (summary != null) {
                HashMap<String, String> map = new HashMap<String, String>();

                String area = summary.getAreaName();
                String checkin = summary.getCheckinTime();
                String checkout = summary.getCheckoutTime();
                String diff = summary.getDiff();
                String date = summary.getDate();

                map.put("area", area);
                map.put("checkin", checkin);
                map.put("checkout", checkout);
                map.put("diff", diff);
                map.put("date", date);

                arrayList.add(map);
                flag2 = 1;
            }
        }
        if (recyclerAdapter != null) {
            recyclerAdapter.notifyDataSetChanged();
        }
        if (flag1 == 1 && flag2 == 1){
            view.setVisibility(View.VISIBLE);
        }

    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    /*private void showList() {
        arrayList.clear();
        Cursor c = databaseHelper.getData(calender_date);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do{
                HashMap<String, String> map = new HashMap<String, String>();

                String id = c.getString(c.getColumnIndex("ID"));
                String date = c.getString(c.getColumnIndex("DATE"));

                map.put("id", id);
                map.put("date", date);

                arrayList.add(map);
            }while (c.moveToNext());
        }
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
