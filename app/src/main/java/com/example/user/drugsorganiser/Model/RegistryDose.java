package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;

import org.joda.time.DateTime;

public class RegistryDose{

    private static final long serialVersionUID = -222864131214757024L;
    public static final String ID_FIELD = "dose_id";

    public static final String DRUG_COLUMN = "drug";
    public static final String USER_COLUMN = "user";
    public static final String DOSE_COLUMN = "dose_details";
    public static final String ACCEPTED_COLUMN = "accepted";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int doseId;

    @DatabaseField(columnName = DRUG_COLUMN, canBeNull = false)
    public String drug;
    // drug's name and date

    @DatabaseField(columnName = USER_COLUMN, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public User user;
    // drug's name and date

    @DatabaseField(columnName = DOSE_COLUMN, canBeNull = false)
    public String dose;
    // drug's description

    @DatabaseField(columnName = ACCEPTED_COLUMN)
    public Boolean accepted;

    public RegistryDose() {}

    public RegistryDose(String drug, User user, String dose, Boolean accepted) {
        this.drug = drug;
        this.dose = dose;
        this.accepted = accepted;
        this.user = user;
    }
}

