package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by user on 2017-04-14.
 */
@DatabaseTable
public class Drug implements Serializable {

    private static final long serialVersionUID = -222864131214757024L;
    public static final String ID_FIELD = "drug_id";

    public static final String NAME_COLUMN = "drug_name";
    public static final String DOSE_COLUMN = "drug_dose";
    public static final String INTERVAL_COLUMN = "drug_interval";
    public static final String IMPORTANT_COLUMN = "drug_important";
    public static final String USER_COLUMN = "drug_user";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int drugId;

    @DatabaseField(columnName = NAME_COLUMN)
    public String name;

    @DatabaseField(columnName = DOSE_COLUMN)
    public String dose;

    //TODO: implement other types of dosing
    @DatabaseField(columnName = INTERVAL_COLUMN)
    public long interval;

    @DatabaseField(columnName = IMPORTANT_COLUMN)
    public boolean important;

    // Foreign key defined to hold associations
    @DatabaseField(columnName = USER_COLUMN, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public User user;

    public Drug() {} //required

    public Drug(User user, String name, String dose, long interval, boolean important) {
        this.name = name;
        this.dose = dose;
        this.interval = interval;
        this.important = important;
        this.user = user;
    }


}
