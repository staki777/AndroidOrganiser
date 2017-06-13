package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Gosia on 29.05.2017.
 */

public class TermAdapterRegular extends RecyclerView.Adapter<TermViewHolder>  implements Serializable {
    private List<RegularDose> regularDoses;
    private String type;
    private Context ctx;
    private Drug drug;
    private String[] daysOfWeek;

    public TermAdapterRegular(Drug drug, Context ctx) {
        Log.i("TermAdapterRegular", "Constructor");
        this.drug = drug;
        this.ctx = ctx;

        this.regularDoses = new ArrayList<>();
        regularDoses.addAll(findRegularDosesByDrug());
        if(regularDoses.size()>=1)
            type = regularDoses.get(0).interval;
        daysOfWeek = ctx.getResources().getStringArray(R.array.days_of_week);
        Log.i("TermAdapterRegular", regularDoses.size()+" DosesFound");
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, null);
        return new TermViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final TermViewHolder holder, final int position) {
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
        if(term.interval==daysOfWeek[0])
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
        holder.itemOptionsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(ctx, holder.itemOptionsView);
                popup.inflate(R.menu.menu_only_remove_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_menu_item:
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);

                                dialogBuilder.setTitle(ctx.getString(R.string.delete_item_dialog_title));
                                dialogBuilder.setPositiveButton(ctx.getString(R.string.yes_button), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        deleteItem(term);
                                    }
                                });
                                dialogBuilder.setNegativeButton(ctx.getString(R.string.no_button), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //pass
                                    }
                                });
                                dialogBuilder.show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return (regularDoses != null) ? regularDoses.size() : 0;
    }

    public void deleteItem(RegularDose term){
        int position = regularDoses.indexOf(term);

        ((DrugsActivity)ctx).getEditedDrug().regularDoses.remove(term);
        regularDoses.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(ctx, ctx.getString(R.string.term_deleted_confirmation), Toast.LENGTH_SHORT).show();
    }
    public  void deleteAllItems(){
        ((DrugsActivity)ctx).getEditedDrug().regularDoses.clear();
        notifyDataSetChanged();
        regularDoses.clear();
        notifyDataSetChanged();
    }

    public void addItem(RegularDose term){
        Log.i("TermAdapter", "AddItem begin");
        Drug editedDrug = ((DrugsActivity)ctx).getEditedDrug();
        editedDrug.regularDoses.add(term);
        regularDoses.add(term);
        notifyItemInserted(regularDoses.indexOf(term));
        Log.i("TermAdapter", Arrays.toString(regularDoses.toArray()));
        Toast.makeText(ctx, R.string.term_added_confirmation, Toast.LENGTH_SHORT).show();
    }

    private Collection<RegularDose> findRegularDosesByDrug() {
        if(((DrugsActivity)ctx).getEditedDrug()==null)
            return  new ArrayList<>();
        return ((DrugsActivity)ctx).getEditedDrug().regularDoses;
    }
    public String getType() {
        if(type == null)
            return null;
        else return type;
    }

}

