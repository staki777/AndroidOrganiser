package com.example.user.drugsorganiser.Model;

import org.joda.time.DateTime;

public class RegistryDose extends SpecificDose {

    public RegistryDose() {}

    public RegistryDose(Drug drug, DateTime doseDate) {
        super(drug, doseDate);
    }
}

