package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegistryDose;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.DosesManagement;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

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

    private Context ctx;
    private List<RegistryDose> doses;

    public RegistryAdapter(Context ctx) {
        Log.i("RegistryAdapter", "Constructor");

        this.ctx = ctx;
        doses = new LinkedList<>();
        prepareRegistryDoses();
    }

    private void prepareRegistryDoses() {
        DosesManagement dm = new DosesManagement((DrugsActivity) ctx);
        doses = dm.findAllRegistryDosesForUser(((DrugsActivity) ctx).getUser());
    }

    @Override
    public DrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drug_view_item, null);
        return new DrugViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final DrugViewHolder holder, final int position) {
        final RegistryDose dose = doses.get(doses.size() - position - 1);
        holder.itemNameView.setText(dose.drug);
        holder.itemDoseView.setText(dose.dose);
        holder.itemImportantView.setText((dose.accepted) ? "Accepted" : "Unaccepted");
    }

    @Override
    public int getItemCount() {
        return (doses != null) ? doses.size() : 0;
    }
}