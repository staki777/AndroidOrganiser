package com.example.user.drugsorganiser.ViewModel;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-04-14.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugViewHolder> {
    private User user;
    private List<Drug> drugs;
    private Context ctx;
    private DialogHelper dh;

    public DrugAdapter(User user, Context ctx) {

        this.drugs = new ArrayList<>();
        drugs.addAll(user.drugs);


        this.user=user;
        this.ctx = ctx;
        dh = new DialogHelper(user, ctx, DrugAdapter.this);
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
        holder.itemDoseView.setText(drug.dose);
        holder.itemIntervalView.setText((Integer.toString((int)drug.interval)));
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
                                dh.showEditDialog(view, drug);
                                break;
                            case R.id.delete_menu_item:
                                dh.showDeleteDialog(drug);
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
        }
        drugs.add(drug);
        notifyItemInserted(drugs.indexOf(drug));
        Toast.makeText(ctx, drug.name+"\n"+ctx.getString(R.string.add_confirmation), Toast.LENGTH_SHORT).show();
    }

    public void editItem(Drug drug){
        try{
            final Dao<Drug, Integer> drugDao = ((DrugsActivity)ctx).getHelper().getDrugDao();
            drugDao.update(drug);
        }catch (SQLException e){
            e.printStackTrace();
        }
        notifyItemChanged(drugs.indexOf(drug));
        Toast.makeText(ctx, drug.name+"\n"+ctx.getString(R.string.edit_confirmation), Toast.LENGTH_SHORT).show();
    }
}