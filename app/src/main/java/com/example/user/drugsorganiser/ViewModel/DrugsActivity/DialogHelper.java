package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.app.AlertDialog;
import android.content.Context;

import com.example.user.drugsorganiser.Model.User;

/**
 * Created by user on 2017-04-14.
 */
//DO NOT USE!
public class DialogHelper {
    private Context ctx;
    private DrugAdapter drugAdapter;
    private User user;
    private Integer interval;

    private AlertDialog.Builder dialogBuilder;
//
//    //controls
//    private EditText etName, etDose, etInterval;
//    private TextView tvSeek;
//    private CheckBox chbxImportant;
//    private SeekBar seek;
//    private Spinner spNum;
//    private Spinner spDes;
//    private Spinner spDoseType;
//
//    //arrays
//    private String[] timeUnitsArr;
//    private Integer[] iHours = new Integer[23];// 1-23
//    private Integer[] iDays = new Integer[31];//1-31
//    private Integer[] iMinutes = new Integer[59];//1-59
//
//    //adapters
//    private ArrayAdapter<Integer> hoursAdapter;
//    private ArrayAdapter<Integer> daysAdapter;
//    private ArrayAdapter<Integer> minutesAdapter;
//    private ArrayAdapter<String> timeUnitsAdapter;
//    private ArrayAdapter<String> doseTypeAdapter;
//
//
//    public DialogHelper(User user, Context ctx, DrugAdapter drugAdapter) {
//        this.ctx = ctx;
//        this.drugAdapter = drugAdapter;
//        this.user = user;
//
//        dialogBuilder = new AlertDialog.Builder(ctx);
//        dialogBuilder.create();
//        View dialogView = LayoutInflater.from(ctx).inflate(R.layout.add_item_dialog, null);
//        dialogBuilder.setView(dialogView);
//
//        etName = (EditText) dialogView.findViewById(R.id.edit_name);
//        etDose = (EditText) dialogView.findViewById(R.id.edit_dose);
//        etInterval = (EditText) dialogView.findViewById(R.id.edit_interval);
//        tvSeek = (TextView) dialogView.findViewById(R.id.tv_seek2);
//        chbxImportant = (CheckBox) dialogView.findViewById(R.id.edit_important);
//        seek = (SeekBar) dialogView.findViewById(R.id.seekBar2);
//
//        spNum = (Spinner) dialogView.findViewById(R.id.number_spinner);
//        spDes = (Spinner) dialogView.findViewById(R.id.des_spinner);
//        spDoseType = (Spinner) dialogView.findViewById(R.id.dose_type_spinner);
//
//        Resources res = ctx.getResources();
//        //ARRAYS
//        timeUnitsArr = res.getStringArray(R.array.time_units_array);
//
//        iHours = new Integer[23];// 1-23
//        iDays = new Integer[31];//1-31
//        iMinutes = new Integer[59];//1-59
//        for (int i = 0; i < iHours.length; i++)
//            iHours[i] = i + 1;
//        for (int i = 0; i < iDays.length; i++)
//            iDays[i] = i + 1;
//        for (int i = 0; i < iMinutes.length; i++)
//            iMinutes[i] = i + 1;
//
//        //ADAPTERS
//        hoursAdapter = new ArrayAdapter<Integer>(ctx, android.R.layout.simple_list_item_1, iHours);
//        daysAdapter = new ArrayAdapter<Integer>(ctx, android.R.layout.simple_list_item_1, iDays);
//        minutesAdapter = new ArrayAdapter<Integer>(ctx, android.R.layout.simple_list_item_1, iMinutes);
//        timeUnitsAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, timeUnitsArr);
//        doseTypeAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_dropdown_item_1line, );
//
//        spNum.setAdapter(hoursAdapter);
//        spDes.setAdapter(timeUnitsAdapter);
//        spDoseType.setAdapter(doseTypeAdapter);
//
//    }

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

//    public void showAddDialog(final View view){
//        dialogBuilder.setTitle(ctx.getString(R.string.new_item_dialog_title));
//
//        spNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
//                interval= Integer.parseInt(spNum.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//
//        });
//
//        spDes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
//                if(position==0)
//                    spNum.setAdapter(hoursAdapter);
//                if(position==1)
//                    spNum.setAdapter(daysAdapter);
//                if(position==2)
//                    spNum.setAdapter(minutesAdapter);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//
//        });
//
//
//
//
//        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            int progressChangedValue = 0;
//
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                progressChangedValue = progress;
//                if(progress==0)
//                    tvSeek.setText("1 day");
//                else
//                    tvSeek.setText(progress+1 +" days");
//            }
//
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        dialogBuilder.setPositiveButton(ctx.getString(R.string.positive_button), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                if(etName.getText().toString().isEmpty() || etDose.getText().toString().isEmpty() ||
//                        etInterval.getText().toString().isEmpty())
//                {
//                    Snackbar.make(view, ctx.getString(R.string.fields_must_be_filled), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    return;
//                }
//                Drug drug=new Drug(user, etName.getText().toString(), etDose.getText().toString(), Long.parseLong(etInterval.getText().toString()), chbxImportant.isChecked());
//                drugAdapter.addItem(drug);
//            }
//        });
//        dialogBuilder.setNegativeButton(ctx.getString(R.string.negative_button), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //pass
//            }
//        });
//        dialogBuilder.show();
//    }

//    public void showEditDialog(final View view, final Drug drug){
//        etName.setText(drug.name);
//        etDose.setText(drug.dose);
//        etInterval.setText(Long.toString(drug.interval));
//        if(drug.important){
//            chbxImportant.toggle();
//        }
//
//        dialogBuilder.setTitle(ctx.getString(R.string.edit_item_dialog_title));
//        dialogBuilder.setPositiveButton(ctx.getString(R.string.positive_button), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                if(etName.getText().toString().isEmpty() || etDose.getText().toString().isEmpty() ||
//                        etInterval.getText().toString().isEmpty())
//                {
//                    Snackbar.make(view, ctx.getString(R.string.fields_must_be_filled), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    return;
//                }
//                drug.name=etName.getText().toString();
//                drug.dose=etDose.getText().toString();
//                drug.interval=Long.parseLong(etInterval.getText().toString());
//                drug.important=chbxImportant.isChecked();
//                drugAdapter.editItem(drug);
//            }
//        });
//        dialogBuilder.setNegativeButton(ctx.getString(R.string.negative_button), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //pass
//            }
//        });
//        dialogBuilder.show();
//
//    }
//
//    public void showDeleteDialog(final Drug drug){
//       // SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(ctx);
//       // Boolean confirm = SP.getBoolean("deleteConfirmation", false);
//       // if(!confirm){
//       //     drugAdapter.deleteItem(drug);
//        //    return;
//       // }
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);
//
//        dialogBuilder.setTitle(ctx.getString(R.string.delete_item_dialog_title));
//        dialogBuilder.setPositiveButton(ctx.getString(R.string.yes_button), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                drugAdapter.deleteItem(drug);
//            }
//        });
//        dialogBuilder.setNegativeButton(ctx.getString(R.string.no_button), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //pass
//            }
//        });
//        dialogBuilder.show();
//    }
//

}
