package com.example.user.drugsorganiser.Model;

import org.joda.time.DateTime;

public class NonStandardDose extends SpecificDose {

    public NonStandardDose() {}

    public NonStandardDose(Drug drug, DateTime doseDate) {
        super(drug, doseDate);
    }
}
