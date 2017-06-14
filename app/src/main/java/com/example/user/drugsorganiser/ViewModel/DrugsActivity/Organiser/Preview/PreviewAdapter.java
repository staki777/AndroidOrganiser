package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Preview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.UniversalMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gosia on 31.05.2017.
 */

public class PreviewAdapter extends RecyclerView.Adapter<PreviewViewHolder> {

    private Drug drug;
    private int type;
    private Context ctx;
    private List<RegularDose> regularDoses;
    private List<CustomDose> customDoses;
    private String[] daysOfWeek;

    public PreviewAdapter(Drug drug, Context ctx) {

        Log.i("PreviewAdapter", "Constructor");
        this.drug = drug;
        this.ctx = ctx;
        this.customDoses = new ArrayList<>();
        this.regularDoses = new ArrayList<>();
        this.type = drug.dosesSeriesType;
        if(drug.dosesSeriesType==0)
            regularDoses.addAll(drug.regularDoses);
        else if(drug.dosesSeriesType==2)
            customDoses.addAll(drug.customDoses);
        daysOfWeek = ctx.getResources().getStringArray(R.array.days_of_week);;
    }

    @Override
    public PreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_item, null);
        return new PreviewViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(PreviewViewHolder holder, int position) {
        if(drug.dosesSeriesType==0) {
            Log.i("PreviewAdapter", Integer.toString(position));
            final RegularDose term = regularDoses.get(position);
            String minute;
            if(term.minute<10)
                minute = "0" + Integer.toString(term.minute);
            else minute = Integer.toString(term.minute);
            String monthD;
            if (term.monthDay < 10)
                monthD = "0" + Integer.toString(term.monthDay);
            else monthD = Integer.toString(term.monthDay);
            String mon;
            if (term.month < 10)
                mon = "0" + Integer.toString(term.month);
            else mon = Integer.toString(term.month);
            if(term.interval==0)
                holder.itemDateView.setText(Integer.toString(term.hour) +":"+ minute);
            else if(term.weekDay == -1)
                if(term.month == -1)
                    if(term.monthDay == -1)
                        holder.itemDateView.setText(Integer.toString(term.hour) +":"+ minute);
                    else
                        holder.itemDateView.setText(monthD+"  " +Integer.toString(term.hour) +":"+ minute);
                else  holder.itemDateView.setText( monthD + "-"  + mon +"  " +Integer.toString(term.hour) +":"+ minute );
            else
                holder.itemDateView.setText(daysOfWeek[term.weekDay-1] +"  " + Integer.toString(term.hour) +":"+ minute );
        }
        else {
            final CustomDose term = customDoses.get(position);
            holder.itemDateView.setText(UniversalMethods.DateTimeToString(term.doseDate));
        }
    }

    @Override
    public int getItemCount() {
        if(drug.dosesSeriesType==0)
            return drug.regularDoses.size();
        else return drug.customDoses.size();
    }
}
