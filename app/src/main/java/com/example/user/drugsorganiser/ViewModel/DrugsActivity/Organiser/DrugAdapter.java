package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.drugsorganiser.Model.ConstantIntervalDose;
import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.DosesManagement;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug.AddEditDrugFragment;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 2017-04-14.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugViewHolder>  implements Serializable{
    private User user;
    private List<Drug> drugs;
    private Context ctx;

    public DrugAdapter(User user, Context ctx) {
        Log.i("DrugAdapter", "Constructor");

        this.drugs = new ArrayList<>();
        if(user != null){
            drugs.addAll(user.drugs);
        }
        this.user=user;
        this.ctx = ctx;

    }

    @Override
    public DrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drug_item, null);
        return new DrugViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final DrugViewHolder holder, final int position) {
        final Drug drug = drugs.get(position);
        holder.itemNameView.setText(drug.name);
        holder.itemDoseView.setText(drug.doseQuantity+" "+drug.doseDescription);
        holder.itemCommentView.setText(drug.comment);
        holder.itemImportantView.setText((drug.important)?"Important": "Not important");
        holder.itemOptionsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(ctx, holder.itemOptionsView);
                popup.inflate(R.menu.menu_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_menu_item:
                                AddEditDrugFragment adf=new AddEditDrugFragment();
                                Bundle b = new Bundle();
                                b.putSerializable("editDrug", drug);
                                adf.setArguments(b);
                                ((DrugsActivity)ctx).removeIfExists(AddEditDrugFragment.class.getSimpleName());
                                ((DrugsActivity)ctx).replaceWithNew(R.id.toReplace, adf, true);

                                break;
                            case R.id.delete_menu_item:
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);

                                dialogBuilder.setTitle(ctx.getString(R.string.delete_item_dialog_title));
                                dialogBuilder.setPositiveButton(ctx.getString(R.string.yes_button), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        deleteItem(drug);
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
        return (drugs != null) ? drugs.size() : 0;
    }

    public void deleteItem(Drug drug){
        int position = drugs.indexOf(drug);
        //cancelling all alarms connected with deleted drug
        DosesManagement dm = new DosesManagement((DrugsActivity)ctx);
        dm.cancelAllAlarmsForDrug(((DrugsActivity)ctx).getUser(), drug);
        try{
            final Dao<Drug, Integer> drugDao = ((DrugsActivity)ctx).getHelper().getDrugDao();
            drugDao.delete(drug);
        }catch (SQLException e){
            e.printStackTrace();
        }
        drugs.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(ctx, drug.name+"\n"+ ctx.getString(R.string.delete_confirmation), Toast.LENGTH_SHORT).show();
    }

    public void addItem(Drug drug){
        try{
            final Dao<Drug, Integer> drugDao = ((DrugsActivity)ctx).getHelper().getDrugDao();
            drugDao.createIfNotExists(drug);
        }catch (SQLException e){
            e.printStackTrace();
            Log.i("DrugAdapter", "sql EXCEPTION: "+e.getMessage());
        }
        Log.i("DrugAdapter", "sql createIfNotExistsExecutedProperly");
        createOrUpdateDrug(drug);
        drugs.add(drug);
        notifyItemInserted(drugs.indexOf(drug));
        Toast.makeText(ctx, drug.name+"\n"+ctx.getString(R.string.add_confirmation), Toast.LENGTH_SHORT).show();
    }

    public void editItem(Drug drug){
        createOrUpdateDrug(drug);
        notifyItemChanged(drugs.indexOf(drug));
        Toast.makeText(ctx, drug.name+"\n"+ctx.getString(R.string.edit_confirmation), Toast.LENGTH_SHORT).show();
    }

    private void createOrUpdateDrug(Drug drug){
        Log.i("DrugAdapter", "In createOrUpdateDrug; dosesSeriesType="+drug.dosesSeriesType);
        try{
            final Dao<Drug, Integer> drugDao = ((DrugsActivity)ctx).getHelper().getDrugDao();

            if(drug.dosesSeriesType == 0){ //regular doses
                for(RegularDose r : drug.regularDoses){
                    ((DrugsActivity)ctx).getHelper().getRegularDoseDao().createOrUpdate(r);
                }
                ((DrugsActivity)ctx).getHelper().getCustomDoseDao().delete(drug.customDoses);
                drug.customDoses.clear();
                ((DrugsActivity)ctx).getHelper().getConstantIntervalDoseDao().delete(drug.constantIntervalDose);
                drug.constantIntervalDose = new ConstantIntervalDose();
            }
            else if(drug.dosesSeriesType == 1){ //constant interval
                ((DrugsActivity)ctx).getHelper().getCustomDoseDao().delete(drug.customDoses);
                drug.customDoses.clear();
                ((DrugsActivity)ctx).getHelper().getRegularDoseDao().delete(drug.regularDoses);
                drug.regularDoses.clear();
                drug.constantIntervalDose.drug = drug;
                ((DrugsActivity)ctx).getHelper().getConstantIntervalDoseDao().createOrUpdate(drug.constantIntervalDose);
            }
            else if(drug.dosesSeriesType == 2){ //custom doses

                for(CustomDose c : drug.customDoses){
                    ((DrugsActivity)ctx).getHelper().getCustomDoseDao().createOrUpdate(c);
                }

                ((DrugsActivity)ctx).getHelper().getRegularDoseDao().delete(drug.regularDoses);
                drug.regularDoses.clear();
                ((DrugsActivity)ctx).getHelper().getConstantIntervalDoseDao().delete(drug.constantIntervalDose);
                drug.constantIntervalDose = new ConstantIntervalDose();
            }
            drugDao.update(drug);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private  boolean existsEntityWithSameID(Collection<CustomDose> collection, int ID){
        for (CustomDose cd : collection){
            if(cd.doseId == ID){
                return true;
            }
        }
        return  false;
    }

}