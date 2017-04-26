package com.example.user.drugsorganiser.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;

/**
 * Created by user on 2017-04-14.
 */

public class DialogHelper {
    private Context ctx;
    private DrugAdapter drugAdapter;
    private User user;

    public DialogHelper(User user, Context ctx, DrugAdapter drugAdapter) {
        this.ctx = ctx;
        this.drugAdapter =drugAdapter;
        this.user=user;
    }

    public DialogHelper(User user, Context ctx, DoseAdapter drugAdapter) {
        this.ctx = ctx;
        //TODO: One DialogHelper for all adapters.
        //this.drugAdapter =drugAdapter;
        this.user=user;
    }

    public void showAddDialog(final View view){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);

        final View dialogView = LayoutInflater.from(ctx).inflate(R.layout.add_item_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText etName= (EditText) dialogView.findViewById(R.id.edit_name);
        final EditText etDose= (EditText) dialogView.findViewById(R.id.edit_dose);
        final EditText etInterval= (EditText) dialogView.findViewById(R.id.edit_interval);

        final CheckBox chbxImportant= (CheckBox) dialogView.findViewById(R.id.edit_important);

        dialogBuilder.setTitle(ctx.getString(R.string.new_item_dialog_title));

        dialogBuilder.setPositiveButton(ctx.getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(etName.getText().toString().isEmpty() || etDose.getText().toString().isEmpty() ||
                        etInterval.getText().toString().isEmpty())
                {
                    Snackbar.make(view, ctx.getString(R.string.fields_must_be_filled), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                Drug drug=new Drug(user, etName.getText().toString(), etDose.getText().toString(), Long.parseLong(etInterval.getText().toString()), chbxImportant.isChecked());
                drugAdapter.addItem(drug);
            }
        });
        dialogBuilder.setNegativeButton(ctx.getString(R.string.negative_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.create().show();
    }

    public void showEditDialog(final View view, final Drug drug){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);

        final View dialogView = LayoutInflater.from(ctx).inflate(R.layout.add_item_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText etName= (EditText) dialogView.findViewById(R.id.edit_name);
        final EditText etDose= (EditText) dialogView.findViewById(R.id.edit_dose);
        final EditText etInterval= (EditText) dialogView.findViewById(R.id.edit_interval);

        final CheckBox chbxImportant= (CheckBox) dialogView.findViewById(R.id.edit_important);

        etName.setText(drug.name);
        etDose.setText(drug.dose);
        etInterval.setText(Long.toString(drug.interval));
        if(drug.important){
            chbxImportant.toggle();
        }

        dialogBuilder.setTitle(ctx.getString(R.string.edit_item_dialog_title));
        dialogBuilder.setPositiveButton(ctx.getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(etName.getText().toString().isEmpty() || etDose.getText().toString().isEmpty() ||
                        etInterval.getText().toString().isEmpty())
                {
                    Snackbar.make(view, ctx.getString(R.string.fields_must_be_filled), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                drug.name=etName.getText().toString();
                drug.dose=etDose.getText().toString();
                drug.interval=Long.parseLong(etInterval.getText().toString());
                drug.important=chbxImportant.isChecked();
                drugAdapter.editItem(drug);
            }
        });
        dialogBuilder.setNegativeButton(ctx.getString(R.string.negative_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.create().show();

    }

    public void showDeleteDialog(final Drug drug){
       // SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(ctx);
       // Boolean confirm = SP.getBoolean("deleteConfirmation", false);
       // if(!confirm){
       //     drugAdapter.deleteItem(drug);
        //    return;
       // }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);

        dialogBuilder.setTitle(ctx.getString(R.string.delete_item_dialog_title));
        dialogBuilder.setPositiveButton(ctx.getString(R.string.yes_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                drugAdapter.deleteItem(drug);
            }
        });
        dialogBuilder.setNegativeButton(ctx.getString(R.string.no_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.create().show();
    }


}
