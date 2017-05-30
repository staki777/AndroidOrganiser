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

import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.UniversalMethods;
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
        regularDoses.addAll(findRegularDosesByDrug(drug));
        if(regularDoses.size()>=1)
            type = regularDoses.get(0).interval;
        daysOfWeek = new String[] { "Monday" , "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};
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
        holder.itemDateView.setText("");
        final RegularDose term = regularDoses.get(position);
        String minute;
        if(term.minute<10)
            minute = "0" + Integer.toString(term.minute);
        else minute = Integer.toString(term.minute);
        if(term.interval=="day")
            holder.itemDateView.setText(Integer.toString(term.hour) +":"+ minute);
        else if(term.weekDay == -1)
            if(term.month == -1)
                if(term.monthDay == -1)
                    holder.itemDateView.setText(Integer.toString(term.hour) +":"+ minute);
                else
                holder.itemDateView.setText(Integer.toString(term.monthDay)+"  " +Integer.toString(term.hour) +":"+ minute);
            else  holder.itemDateView.setText( Integer.toString(term.monthDay) + "-"  + Integer.toString(term.month)+"  " +Integer.toString(term.hour) +":"+ minute );
        else
            holder.itemDateView.setText(daysOfWeek[term.weekDay-1] +"  " + Integer.toString(term.hour) +":"+ minute );
        Log.i("TermAdapterRegular", String.valueOf(term.interval.length()));
        Log.i("TermAdapterRegular", String.valueOf(term.interval.charAt(0)));
        Log.i("TermAdapterRegular", String.valueOf(term.interval.charAt(1)));
        Log.i("TermAdapterRegular", String.valueOf(term.interval.charAt(2)));
        Log.i("TermAdapterRegular", String.valueOf(type==type));
        Log.i("TermAdapterRegular", String.valueOf(type==term.interval));
        Log.i("TermAdapterRegular", String.valueOf(getTyp()));
        Log.i("TermAdapterRegular", holder.itemDateView.getText().toString());
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
        Toast.makeText(ctx, "Selected term\n"+ ctx.getString(R.string.delete_confirmation), Toast.LENGTH_SHORT).show();
    }
    public  void deleteAllItems(){
        ((DrugsActivity)ctx).getEditedDrug().regularDoses.clear();
        notifyDataSetChanged();
    }

    public void addItem(RegularDose term){
        Log.i("TermAdapter", "AddItem begin");
        Drug editedDrug = ((DrugsActivity)ctx).getEditedDrug();
        editedDrug.regularDoses.add(term);
        regularDoses.add(term);
        notifyItemInserted(regularDoses.indexOf(term));
        Log.i("TermAdapter", Arrays.toString(regularDoses.toArray()));
        Toast.makeText(ctx, "New term successfully added!", Toast.LENGTH_SHORT).show();
    }

    private Collection<RegularDose> findRegularDosesByDrug(Drug drug) {
        return ((DrugsActivity)ctx).getEditedDrug().regularDoses;
    }
    public String getTyp() {
        if(type == null)
            return null;
        else return type;
    }

}

