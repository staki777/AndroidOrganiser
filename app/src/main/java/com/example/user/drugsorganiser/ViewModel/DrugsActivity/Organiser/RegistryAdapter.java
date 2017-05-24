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

public class RegistryAdapter extends RecyclerView.Adapter<DrugViewHolder> {

    private User user;
    private Context ctx;
    private List<Pair<Drug, Date>> doses;
    private SimpleDateFormat dateFormat;

    public RegistryAdapter(User user, Context ctx) {
        Log.i("RegistryAdapter", "Constructor");

        this.user=user;
        this.ctx = ctx;

        dateFormat = new SimpleDateFormat ("HH:mm (dd.MM)");
        doses = new LinkedList<>();

        //mock
        List<Drug> drugs = new ArrayList<>();
        if(user != null){
            drugs.addAll(user.drugs);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(2017,4,25,8,0);
        if (drugs.size() == 0) return;
        doses.add(new Pair<Drug, Date>(drugs.get(0), new Date(cal.getTimeInMillis())));
        cal.set(2017,4,25,20,0);
        doses.add(new Pair<Drug, Date>(drugs.get(0), new Date(cal.getTimeInMillis())));
        cal.set(2017,4,26,8,0);
        doses.add(new Pair<Drug, Date>(drugs.get(0), new Date(cal.getTimeInMillis())));
    }

    public void moveToRegistry(Pair<Drug,Date> dose)
    {
        //TODO: Dose's moving from DoseAdapter to RegistryAdapter.
        doses.add(dose);
    }

    public void removeOlderThanWeek(){
        //TODO:
    }

    @Override
    public DrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drug_view_item, null);
        return new DrugViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final DrugViewHolder holder, final int position) {
        final Pair<Drug, Date> dose = doses.get(doses.size() - position - 1);
        holder.itemNameView.setText(dose.first.name);
        holder.itemDoseView.setText(dose.first.doseQuantity + " " + dose.first.doseDescription);
        holder.itemCommentView.setText(dose.first.comment);
        holder.itemImportantView.setText((dose.first.important) ? "Important" : "Not important");
    }

    @Override
    public int getItemCount() {
        return (doses != null) ? doses.size() : 0;
    }
}