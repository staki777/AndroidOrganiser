package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;

public class RegistryDose{

    private static final long serialVersionUID = -222864131214757024L;
    public static final String ID_FIELD = "dose_id";

    public static final String DRUG_COLUMN = "drug";
    public static final String USER_COLUMN = "user";
    public static final String DOSE_QUANTITY_COLUMN = "dose_quantity";
    public static final String DOSE_TYPE_COLUMN = "dose_type";
    public static final String DOSE_CUSTOM_COLUMN = "dose_custom_name";

    public static final String ACCEPTED_COLUMN = "accepted";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int doseId;

    @DatabaseField(columnName = DRUG_COLUMN, canBeNull = false)
    public String drug;
    // drug's name and date

    @DatabaseField(columnName = USER_COLUMN, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public User user;
    // drug's name and date

    @DatabaseField(columnName = DOSE_QUANTITY_COLUMN, canBeNull = false)
    public int doseQuantity;

    @DatabaseField(columnName = DOSE_TYPE_COLUMN, canBeNull = false)
    public int doseType;

    @DatabaseField(columnName = DOSE_CUSTOM_COLUMN, canBeNull = false)
    public String doseCustomType;

    @DatabaseField(columnName = ACCEPTED_COLUMN)
    public Boolean accepted;

    public RegistryDose() {}

    public RegistryDose(String drug, User user, int doseQuantity, int doseType, String doseCustomType, Boolean accepted) {
        this.drug = drug;
        this.doseCustomType = doseCustomType;
        this.doseQuantity = doseQuantity;
        this.doseType = doseType;
        this.accepted = accepted;
        this.user = user;
    }
}

