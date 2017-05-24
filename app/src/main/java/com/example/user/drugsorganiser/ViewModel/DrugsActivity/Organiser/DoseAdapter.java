package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by DV7 on 2017-04-26.
 */

public class DoseAdapter extends RecyclerView.Adapter<DrugViewHolder> {

    private User user;
    private List<Drug> drugs;
    private Context ctx;
    private List<Pair<Drug, Date>> doses;
    private SimpleDateFormat dateFormat;

    public DoseAdapter(User user, Context ctx) {
        Log.i("DoseAdapter", "Constructor");
        this.drugs = new ArrayList<>();
        if(user != null){
            drugs.addAll(user.drugs);
        }

        this.user=user;
        this.ctx = ctx;

        dateFormat = new SimpleDateFormat ("HH:mm (dd.MM)");
        doses = new LinkedList<Pair<Drug, Date>>();
        CalculateDoses();
    }

    private void CalculateDoses() {
        //TODO: Calculate which drug' doses for next 24 hours.
       //mock
        Calendar cal = Calendar.getInstance();
        cal.set(2017,4,27,8,0);
        if (drugs.size() == 0) return;
        doses.add(new Pair<Drug, Date>(drugs.get(0), new Date()));
        doses.add(new Pair<Drug, Date>(drugs.get(0), new Date(cal.getTimeInMillis())));
    }

    @Override
    public DrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drug_view_item, null);
        return new DrugViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final DrugViewHolder holder, final int position) {
        final Pair<Drug,Date> dose = doses.get(position);
        holder.itemNameView.setText(dose.first.name);
        holder.itemDoseView.setText(dose.first.doseQuantity+" "+dose.first.doseDescription);
        holder.itemImportantView.setText((dose.first.important)?"Important": "Not important");
        holder.itemCommentView.setText(dose.first.comment);

    }

    @Override
    public int getItemCount() {
        return (doses != null) ? doses.size() : 0;
    }
}