package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;

import org.joda.time.DateTime;

public class CustomDose {

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

    public CustomDose() {}

    public CustomDose(Drug drug, DateTime doseDate) {
        this.drug = drug;
        this.doseDate = doseDate;
    }
}
