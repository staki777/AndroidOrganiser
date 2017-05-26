package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by user on 2017-04-14.
 */
@DatabaseTable
public class Drug implements Serializable {

    private static final long serialVersionUID = -222864131214757024L;
    public static final String ID_FIELD = "drug_id";

    public static final String NAME_COLUMN = "drug_name";
    public static final String DOSE_QUANTITY_COLUMN ="drug_dose_quantity";
    public static final String DOSE_DESCRIPTION_COLUMN = "drug_dose_description";
    public static final String DOSES_SERIES_COLUMN = "doses_series_type";
    public static final String IMPORTANT_COLUMN = "drug_important";
    public static final String COMMENT_COLUMN ="drug_comment";
    public static final String USER_COLUMN = "drug_user";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int drugId;

    @DatabaseField(columnName = NAME_COLUMN)
    public String name;

    @DatabaseField(columnName = DOSE_QUANTITY_COLUMN)
    public int doseQuantity;

    @DatabaseField(columnName = DOSE_DESCRIPTION_COLUMN)
    public String doseDescription;

    @DatabaseField(columnName = DOSES_SERIES_COLUMN)
    public int dosesSeriesType;

    @DatabaseField(columnName = IMPORTANT_COLUMN)
    public boolean important;

    @DatabaseField(columnName = COMMENT_COLUMN)
    public String comment;

    // Foreign key defined to hold associations
    @DatabaseField(columnName = USER_COLUMN, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public User user;

    @ForeignCollectionField(eager = true)
    public Collection<RegularDose> regularDoses;

    @DatabaseField(foreign = true)
    public ConstantIntervalDose constantIntervalDose;

    @ForeignCollectionField(eager = true)
    public Collection<CustomDose> customDoses;

    @ForeignCollectionField(eager = true)
    public Collection<SpecificDose> nearestDoses;

    @ForeignCollectionField(eager = true)
    public Collection<RegistryDose> registry;

    public Drug() {
        dosesSeriesType = 0;
        nearestDoses = new ArrayList<SpecificDose>();
        registry = new ArrayList<RegistryDose>();

        regularDoses = new ArrayList<>();
        constantIntervalDose = new ConstantIntervalDose();
        customDoses = new ArrayList<>();
    } //required

    public Drug(User user, String name, int doseQuantity, String doseDescription, int dosesSeriesType, boolean important, String comment) {
        this.name = name;
        this.doseQuantity = doseQuantity;
        this.doseDescription = doseDescription;
        this.dosesSeriesType = dosesSeriesType;
        this.important = important;
        this.comment = comment;
        this.user = user;

        nearestDoses = new ArrayList<SpecificDose>();
        registry = new ArrayList<RegistryDose>();

        regularDoses = new ArrayList<>();
        constantIntervalDose = new ConstantIntervalDose();
        customDoses = new ArrayList<>();

    }


}
