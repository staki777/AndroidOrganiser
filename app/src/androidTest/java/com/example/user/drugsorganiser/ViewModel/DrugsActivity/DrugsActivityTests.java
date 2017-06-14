package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.test.ActivityInstrumentationTestCase2;

import com.example.user.drugsorganiser.Model.User;
import com.j256.ormlite.dao.Dao;

/**
 * Created by user on 2017-06-14.
 */

public class DrugsActivityTests extends
        ActivityInstrumentationTestCase2<DrugsActivity> {

    private DrugsActivity mTestActivity;
    private Dao<User, Integer> userDao;


    public DrugsActivityTests() {
        super(DrugsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mTestActivity = getActivity();
        userDao = getActivity().getHelper().getUserDao();
    }


    public void testPreconditions() {

        assertNotNull("mTestActivity is null", mTestActivity);
        assertNotNull("getDoseTypes returns null", mTestActivity.getDoseTypes());
        assertNotNull("userdao is null", userDao);
    }

    public void testUserDao(){
        try{
            int usersCountBefore = userDao.queryForAll().size();
            User u = new User("testLogin", "testPassword", "testMail");
            userDao.create(u);
            int usersCountAfterCreate = userDao.queryForAll().size();
            assertTrue("user does not exist in db", usersCountAfterCreate == usersCountBefore + 1);
            userDao.delete(u);
            int usersCountAfterDelete = userDao.queryForAll().size();
            assertTrue("user does not exist in db", usersCountAfterDelete == usersCountBefore);
        }catch (Exception e) {}
    }


}