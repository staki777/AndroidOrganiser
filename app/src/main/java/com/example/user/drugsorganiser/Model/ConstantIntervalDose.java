package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by DV7 on 2017-05-19.
 */

public class ConstantIntervalDose implements Serializable {

    private static final long serialVersionUID = -222864131214757024L;
    public static final String ID_FIELD = "dose_id";

    public static final String DRUG_COLUMN = "drug";
    public static final String INTERVAL_COLUMN = "dose_interval";
    public static final String FIRST_DOSE_COLUMN = "first_dose";
    public static final String LAST_DOSE_COLUMN = "last_accepted_dose";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int doseId;

    @DatabaseField(columnName = DRUG_COLUMN, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public Drug drug;

    @DatabaseField(columnName = INTERVAL_COLUMN)
    public int interval; // minute interval

    @DatabaseField(columnName = FIRST_DOSE_COLUMN)
    public DateTime firstDose;

    @DatabaseField(columnName = LAST_DOSE_COLUMN)
    public DateTime lastAcceptedDose;

    public ConstantIntervalDose() {}

    public ConstantIntervalDose(Drug drug, int interval, DateTime firstDose, DateTime lastAcceptedDose) {
        this.drug = drug;
        this.interval = interval;
        this.firstDose = firstDose;
        this.lastAcceptedDose = lastAcceptedDose;
    }
}