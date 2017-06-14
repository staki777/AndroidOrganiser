package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by DV7 on 2017-05-19.
 */

public class RegularDose implements Serializable {

    private static final long serialVersionUID = -222864131214757024L;
    public static final String ID_FIELD = "dose_id";

    public static final String DRUG_COLUMN = "drug";
    public static final String INTERVAL_COLUMN = "interval_type";
    public static final String MINUTE_COLUMN = "dose_minute";
    public static final String HOUR_COLUMN = "dose_hour";
    public static final String WEEK_DAY_COLUMN = "dose_week_day";
    public static final String MONTH_DAY_COLUMN = "dose_month_day";
    public static final String MONTH_COLUMN = "dose_month";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int doseId;

    @DatabaseField(columnName = DRUG_COLUMN, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public Drug drug;

    @DatabaseField(columnName = INTERVAL_COLUMN)
    public int interval;

    @DatabaseField(columnName = MINUTE_COLUMN)
    public int minute; // minute of hour

    @DatabaseField(columnName = HOUR_COLUMN)
    public int hour; // hour of day

    @DatabaseField(columnName = WEEK_DAY_COLUMN)
    public int weekDay;

    @DatabaseField(columnName = MONTH_DAY_COLUMN)
    public int monthDay;

    @DatabaseField(columnName = MONTH_COLUMN)
    public int month;

    public RegularDose() {}

    public RegularDose(Drug drug, int interval, int minute, int hour, int weekDay, int monthDay, int month) {
        this.drug = drug;
        this.interval = interval;
        this.minute = minute;
        this.hour = hour;
        this.weekDay = weekDay;
        this.monthDay = monthDay;
        this.month = month;
    }
}