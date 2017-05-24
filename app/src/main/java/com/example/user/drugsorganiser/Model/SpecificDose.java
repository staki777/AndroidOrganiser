package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by DV7 on 2017-05-19.
 */

public class SpecificDose implements Serializable {
    // used as both past and non-standard dose

    private static final long serialVersionUID = -222864131214757024L;
    public static final String ID_FIELD = "dose_id";

    public static final String DRUG_COLUMN = "drug";
    public static final String DATE_COLUMN = "dose_date";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int doseId;

    @DatabaseField(columnName = DRUG_COLUMN, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public Drug drug;

    @DatabaseField(columnName = DATE_COLUMN)
    public DateTime doseDate;

    public SpecificDose() {}

    public SpecificDose(Drug drug, DateTime doseDate) {
        this.drug = drug;
        this.doseDate = doseDate;
    }

    public int getDrugID(){
        return  this.drug.drugId;
    }
}

