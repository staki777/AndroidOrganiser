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
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.UniversalMethods;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Created by user on 2017-04-14.
 */

public class TermAdapter extends RecyclerView.Adapter<TermViewHolder>  implements Serializable {
    private List<CustomDose> customDoses;
    private Context ctx;
    private Drug drug;

    public TermAdapter(Drug drug, Context ctx) {
        Log.i("TermAdapter", "Constructor");
        this.drug = drug;
        this.ctx = ctx;

        this.customDoses = new ArrayList<>();
        customDoses.addAll(findCustomDosesByDrug());
        Log.i("TermAdapter", customDoses.size()+" DosesFound");
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, null);
        return new TermViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final TermViewHolder holder, final int position) {
        final CustomDose term = customDoses.get(position);
        holder.itemDateView.setText(UniversalMethods.DateTimeToString(term.doseDate));
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
        return (customDoses != null) ? customDoses.size() : 0;
    }

    public void deleteItem(CustomDose term){
        int position = customDoses.indexOf(term);
        Log.i("TermAdapter", "Removing custom dose..");
        ((DrugsActivity)ctx).getEditedDrug().customDoses.remove(term);

        customDoses.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(ctx, ctx.getString(R.string.term_deleted_confirmation), Toast.LENGTH_SHORT).show();
    }

    public void addItem(CustomDose term){
        Log.i("TermAdapter", "AddItem begin");
        Drug editedDrug = ((DrugsActivity)ctx).getEditedDrug();
        editedDrug.customDoses.add(term);
        customDoses.add(term);
        notifyItemInserted(customDoses.indexOf(term));
        Log.i("TermAdapter", Arrays.toString(customDoses.toArray()));
        Toast.makeText(ctx, ctx.getString(R.string.term_added_confirmation), Toast.LENGTH_SHORT).show();
    }

    private Collection<CustomDose> findCustomDosesByDrug() {
        if(((DrugsActivity)ctx).getEditedDrug()==null)
            return  new ArrayList<>();
            return ((DrugsActivity)ctx).getEditedDrug().customDoses;
    }

}
