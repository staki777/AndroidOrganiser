package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import com.example.user.drugsorganiser.DataBase.DatabaseHelper;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.NonStandardDose;
import com.j256.ormlite.stmt.PreparedQuery;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-05-24.
 */

public class DrugInProgress {
    private Drug drug;
    private List<NonStandardDose> customDoses;
    private DatabaseHelper databaseHelper;
    //TODO: Here should be also fields associated with other typed of dosage


    public DrugInProgress(DatabaseHelper databaseHelper){
        drug = new Drug();
        customDoses = new ArrayList<>();
        this.databaseHelper = databaseHelper;
    }
    public DrugInProgress(Drug drug, DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
        this.drug = drug;
        customDoses = findCustomDosesByDrug(drug);
    }

    public void AddCustomDose(DateTime term){
        NonStandardDose d = new NonStandardDose(drug, term);
        try {
            databaseHelper.getNonStandardDoseDao().createIfNotExists(d);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

//    private Drug findDrugByID(int drugID) {
//        try {
//            PreparedQuery<Drug> q = databaseHelper.getDrugDao().queryBuilder().where().eq(Drug.ID_FIELD, drugID).prepare();
//            return databaseHelper.getDrugDao().queryForFirst(q);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            e.toString();
//        }
//        return  null;
//    }

    public List<NonStandardDose> findCustomDosesByDrug(Drug drug) {
        try {
            PreparedQuery<NonStandardDose> q = databaseHelper.getNonStandardDoseDao().queryBuilder().where().eq(NonStandardDose.DRUG_COLUMN, drug).prepare();
            return databaseHelper.getNonStandardDoseDao().queryForEq(NonStandardDose.DRUG_COLUMN, drug);
        } catch (SQLException e) {
            e.printStackTrace();
            e.toString();
        }
        return  new ArrayList<>();
    }


}
