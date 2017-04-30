package com.example.user.drugsorganiser.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

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
    private Integer interval;

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

    public DialogHelper(User user, Context ctx, RegistryAdapter drugAdapter) {
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
        final TextView tvSeek1= (TextView) dialogView.findViewById(R.id.tv_seek1);
        final TextView tvSeek2= (TextView) dialogView.findViewById(R.id.tv_seek2);
        final CheckBox chbxImportant= (CheckBox) dialogView.findViewById(R.id.edit_important);
        final SeekBar seek1= (SeekBar)dialogView.findViewById(R.id.seekBar);
        final SeekBar seek2= (SeekBar)dialogView.findViewById(R.id.seekBar2);

        dialogBuilder.setTitle(ctx.getString(R.string.new_item_dialog_title));
        Integer[] iNumbers = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
        Integer[] iDays = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
        Integer[] iMinutes = new Integer[60];
        for(int i=0;i<60;i++)
            iMinutes[i]=i+1;
        String[] des = {"hours","days","minutes"};

        final Spinner spNum = (Spinner)dialogView.findViewById (R.id.number_spinner);
        final Spinner spDes = (Spinner) dialogView.findViewById (R.id.des_spinner);

        final ArrayAdapter<Integer> numAdapter1 =  new ArrayAdapter<Integer>(ctx, android.R.layout.simple_list_item_1,iNumbers);
        final ArrayAdapter<Integer> numAdapter2 =  new ArrayAdapter<Integer>(ctx, android.R.layout.simple_list_item_1,iDays);
        final ArrayAdapter<Integer> numAdapter3 =  new ArrayAdapter<Integer>(ctx, android.R.layout.simple_list_item_1,iMinutes);
        ArrayAdapter<String> desAdapter =  new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1,des);

        spNum.setAdapter(numAdapter1);
        spDes.setAdapter(desAdapter);

        spNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                interval= Integer.parseInt(spNum.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        spDes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                if(position==0)
                    spNum.setAdapter(numAdapter1);
                if(position==1)
                    spNum.setAdapter(numAdapter2);
                if(position==2)
                    spNum.setAdapter(numAdapter3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        Resources res = ctx.getResources();
        final String[] doses = res.getStringArray(R.array.dose_array);

        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                tvSeek1.setText(doses[progress]);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,
                //        Toast.LENGTH_SHORT).show();
            }
        });
        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                if(progress==0)
                    tvSeek2.setText("1 day");
                else
                    tvSeek2.setText(progress+1 +" days");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,
                //        Toast.LENGTH_SHORT).show();
            }
        });



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
