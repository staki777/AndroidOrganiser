package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.DosesManagement;
import com.example.user.drugsorganiser.Shared.UniversalMethods;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

//import java.util.Date;

/**
 * Created by DV7 on 2017-04-26.
 */

public class DoseAdapter extends RecyclerView.Adapter<DrugViewHolder> {

    private Context ctx;
    private List<Pair<Drug, DateTime>> doses;
    private SimpleDateFormat dateFormat;

    public DoseAdapter(Context ctx) {
        Log.i("DoseAdapter", "Constructor");
        this.ctx = ctx;

        dateFormat = new SimpleDateFormat ("HH:mm (dd.MM)");
        doses = new LinkedList<Pair<Drug, DateTime>>();
        CalculateDoses();
    }

    private void CalculateDoses() {
        DosesManagement dm = new DosesManagement((DrugsActivity) ctx);
        List<CustomDose> customDoses = dm.findCustomDosesForNext24h(((DrugsActivity) ctx).getUser());
        for (CustomDose cd : customDoses){
            doses.add(new Pair<Drug, DateTime>(cd.drug, cd.doseDate));
        }

    }

    @Override
    public DrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drug_view_item, null);
        return new DrugViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final DrugViewHolder holder, final int position) {
        final Pair<Drug,DateTime> dose = doses.get(position);
        holder.itemNameView.setText(UniversalMethods.DateTimeToString(dose.second)+" - "+dose.first.name);
        holder.itemDoseView.setText(dose.first.doseQuantity+" "+dose.first.doseDescription);
        holder.itemImportantView.setText((dose.first.important)?"Important": "Not important");
        holder.itemCommentView.setText(dose.first.comment);

    }

    @Override
    public int getItemCount() {
        return (doses != null) ? doses.size() : 0;
    }
}