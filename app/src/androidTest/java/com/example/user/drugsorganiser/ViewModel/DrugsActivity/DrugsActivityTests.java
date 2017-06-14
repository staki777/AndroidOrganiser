package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by user on 2017-06-14.
 */

public class DrugsActivityTests extends
        ActivityInstrumentationTestCase2<DrugsActivity> {

    private DrugsActivity mTestActivity;
   // private TextView mTestEmptyText;
   // private FloatingActionButton mFab;

    public DrugsActivityTests() {
        super(DrugsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mTestActivity = getActivity();
    }


    public void testPreconditions() {

        assertNotNull("mTestActivity is null", mTestActivity);
        assertNotNull("getDoseTypes returns null", mTestActivity.getDoseTypes());
        assertNotNull("getHelper returns null", mTestActivity.getHelper());
    }

}