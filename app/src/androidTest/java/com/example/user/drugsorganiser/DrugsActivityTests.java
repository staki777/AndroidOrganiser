package com.example.user.drugsorganiser;

import android.app.Activity;

import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Config(constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class DrugsActivityTests {
    @Test
    public void titleIsCorrect() throws Exception {
        Activity activity = Robolectric.setupActivity(DrugsActivity.class);
        assertTrue(activity.getTitle().toString().equals("Deckard"));
    }
}
