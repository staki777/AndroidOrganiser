package com.example.user.drugsorganiser.Model;

import org.joda.time.DateTime;

public class CustomDose extends SpecificDose {

    public CustomDose() {}

    public CustomDose(Drug drug, DateTime doseDate) {
        super(drug, doseDate);
    }
}
