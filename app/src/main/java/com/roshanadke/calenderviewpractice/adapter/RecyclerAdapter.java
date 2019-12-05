package com.roshanadke.calenderviewpractice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roshanadke.calenderviewpractice.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProgramViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String>> arrayList;

    public RecyclerAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        String area, checkin, checkout, diff, date;
        area = arrayList.get(position).get("area");
       /* checkin ="Check In Time: " + arrayList.get(position).get("checkin");
        checkout ="Check Out Time: " + arrayList.get(position).get("checkout");*/
        checkin = arrayList.get(position).get("checkin");
        checkout = arrayList.get(position).get("checkout");
        diff ="Time Spent: " + arrayList.get(position).get("diff");
        date ="Date " + arrayList.get(position).get("date");

        holder.txt_checkin.setText(checkin);
        holder.txt_checkout.setText(checkout);
        holder.txt_diff.setText(diff);
        holder.txt_area_nm.setText(area);
        holder.txt_date.setText(date);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {

        TextView txt_area_nm , txt_checkin, txt_date, txt_checkout, txt_diff;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);


            txt_area_nm = itemView.findViewById(R.id.txt_area_nm);
            txt_checkin = itemView.findViewById(R.id.txt_checkin);
            txt_checkout = itemView.findViewById(R.id.txt_checkout);
            txt_diff = itemView.findViewById(R.id.txt_diff);
            txt_date = itemView.findViewById(R.id.txt_date);

        }
    }

}
