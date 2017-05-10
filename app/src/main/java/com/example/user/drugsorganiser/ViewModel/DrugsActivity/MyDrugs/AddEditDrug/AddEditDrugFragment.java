package com.example.user.drugsorganiser.ViewModel.DrugsActivity.MyDrugs.AddEditDrug;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.drugsorganiser.Model.DoseTypes;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.MyDrugs.MyDrugsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditDrugFragment extends Fragment  implements View.OnClickListener {

    private DoseTypes doseTypes;

    private TextView tvOtherDoseType;
    private Button btnPositive, btnNegative;
    private EditText etName, etOtherDoseType, etComment;
    private CheckBox chbxImportant;
    private NumberPicker dosePicker;
    private Spinner spDoseType;

    private boolean editMode;
    private  Drug drugToEdit;

    public AddEditDrugFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if(bundle != null){
            drugToEdit=(Drug)bundle.getSerializable("editDrug");
            if(drugToEdit != null){
                editMode = true;
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_drug, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();

        doseTypes = new DoseTypes(getView());
        btnPositive = (Button) getView().findViewById(R.id.positive_button);
        btnNegative = (Button) getView().findViewById(R.id.negative_button);
        btnPositive.setOnClickListener(this);
        btnNegative.setOnClickListener(this);

        tvOtherDoseType = (TextView) getView().findViewById(R.id.tw_other_dose_type);
        etName = (EditText) getView().findViewById(R.id.edit_name);
        etOtherDoseType = (EditText) getView().findViewById(R.id.edit_other_dose_type);
        etComment = (EditText) getView().findViewById(R.id.editComment);
        chbxImportant = (CheckBox) getView().findViewById(R.id.edit_important);

        dosePicker = (NumberPicker) getView().findViewById(R.id.dosePicker);
        dosePicker.setMinValue(1);
        dosePicker.setMaxValue(250);
        dosePicker.setValue(1);

        spDoseType = (Spinner) getView().findViewById(R.id.dose_type_spinner);
        ArrayAdapter<String> doseTypeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, doseTypes.getArr());
        spDoseType.setAdapter(doseTypeAdapter);
        spDoseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected=spDoseType.getSelectedItem().toString();
                Log.i("AddEditDrugFragment", "|"+selected+"|");
                if(selected.equals(doseTypes.getOtherString())){
                    tvOtherDoseType.setVisibility(View.VISIBLE);
                    etOtherDoseType.setVisibility(View.VISIBLE);
                }
                else {
                    etOtherDoseType.setVisibility(View.INVISIBLE);
                    tvOtherDoseType.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //nothing
            }

        });

        //fill
        if(editMode){
            etName.setText(drugToEdit.name);
            dosePicker.setValue(drugToEdit.doseQuantity);
            spDoseType.setSelection(doseTypes.getPosition(drugToEdit.doseDescription));
            if(spDoseType.getSelectedItemPosition() == doseTypes.getPositionOfOther())
                etOtherDoseType.setText(drugToEdit.doseDescription);
            etComment.setText(drugToEdit.comment);
            if(drugToEdit.important){
                chbxImportant.toggle();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnPositive)
        {
            if(etName.getText().toString().isEmpty() || (etOtherDoseType.getVisibility()==View.VISIBLE && etOtherDoseType.getText().toString().isEmpty()))
            {
                Snackbar.make(getView(), getString(R.string.fields_must_be_filled), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            MyDrugsFragment mdf= new MyDrugsFragment();
            Bundle b = new Bundle();
            if(!editMode){ //dodajemy nowy lek
                Drug drug=new Drug( ((DrugsActivity)getActivity()).getUser(), etName.getText().toString(), dosePicker.getValue(), (etOtherDoseType.getVisibility()==View.VISIBLE) ? etOtherDoseType.getText().toString() : spDoseType.getSelectedItem().toString(), chbxImportant.isChecked(), etComment.getText().toString());
                b.putSerializable("newDrug", drug);

            }
            else{ //edytujemy lek
                drugToEdit.name=etName.getText().toString();
                drugToEdit.doseQuantity = dosePicker.getValue();
                drugToEdit.doseDescription=(etOtherDoseType.getVisibility()==View.VISIBLE) ? etOtherDoseType.getText().toString() : spDoseType.getSelectedItem().toString();
                drugToEdit.important=chbxImportant.isChecked();
                drugToEdit.comment=etComment.getText().toString();
                b.putSerializable("editDrug", drugToEdit);
            }
            mdf.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.toReplace, mdf).disallowAddToBackStack().commit();


        }
        else if(v == btnNegative){
            getFragmentManager().beginTransaction().replace(R.id.toReplace, new MyDrugsFragment()).disallowAddToBackStack().commit();
        }
    }
}
